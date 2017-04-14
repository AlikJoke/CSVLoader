package ru.project.csvloader.db.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.opencsv.CSVReader;

import ru.project.csvloader.db.LoaderService;
import ru.project.csvloader.file.pool.Pool;
import ru.project.csvloader.file.pool.model.FileWrapper;
import ru.project.csvloader.jdbc.utils.JdbcUtils;
import ru.project.csvloader.model.Data;

@Repository
@Service
public class LoaderServiceImpl implements LoaderService {

	@Autowired
	private Pool pool;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger log = LoggerFactory.getLogger(LoaderServiceImpl.class);

	@PostConstruct
	public void checkInit() {
		if (pool == null)
			throw new RuntimeException("Files pool isn't injected!");

		if (jdbcTemplate == null)
			throw new RuntimeException("JdbcTemplate isn't injected yet!");
	}

	@Override
	public List<Data> getAll() {
		return jdbcTemplate.query(JdbcUtils.SELECT_ALL_QUERY, DataMapper.rowMapper);
	}

	@Override
	public Data getByDate(LocalDateTime dateTime) {
		return jdbcTemplate.query(JdbcUtils.SELECT_BY_DATE_QUERY, new Object[] { Timestamp.valueOf(dateTime) },
				DataMapper.extractor);
	}

	@Override
	public List<Data> loadToObjects() throws IOException {
		final FileWrapper wrapper;
		if ((wrapper = this.getFileWrapper()) == null)
			return Collections.emptyList();
		File file = wrapper.getFile();
		if (!file.exists())
			throw new IllegalStateException("File is deleted!");
		FileReader fileReader = new FileReader(file);
		CSVReader reader = new CSVReader(
				new InputStreamReader(Files.asByteSource(file).openStream(), fileReader.getEncoding()));
		List<Data> dataList = Lists.newArrayList();
		String[] line;
		try {
			while ((line = reader.readNext()) != null)
				dataList.add(new Data(line[0], LocalDateTime.parse(line[1], DateTimeFormatter.ISO_LOCAL_DATE_TIME),
						Double.parseDouble(line[2])));
		} finally {
			wrapper.markDeleted();
			reader.close();
			fileReader.close();
		}
		return dataList;
	}

	@Override
	public FileWrapper getFileWrapper() throws IOException {
		return pool.getFirstAvailable();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public synchronized void loadToDB() {
		try {
			List<Data> dataList;
			if ((dataList = this.loadToObjects()).isEmpty())
				return;

			dataList.stream().forEach(data -> log.debug(data.toString()));
			List<Data> dataListFinal = dataList.stream().filter(data -> this.isExists(data.getDate()))
					.collect(Collectors.toList());

			dataListFinal.stream().forEach(data -> log.debug(data.toString()));

			if (!dataListFinal.isEmpty())
				jdbcTemplate.batchUpdate(JdbcUtils.INSERT_QUERY, new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int pos) throws SQLException {
						ps.setString(1, dataListFinal.get(pos).getName());
						ps.setTimestamp(4, Timestamp.valueOf(dataListFinal.get(pos).getDate()));
						ps.setDouble(2, dataListFinal.get(pos).getValue());
						ps.setString(3, dataListFinal.get(pos).getSmth());
					}

					@Override
					public int getBatchSize() {
						return dataListFinal.size();
					}
				});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Transactional(readOnly = true)
	private boolean isExists(final LocalDateTime date) {
		return this.getByDate(date) == null;
	}
}

class DataMapper {

	static RowMapper<Data> rowMapper = (ResultSet rs, int rownum) -> {
		return createSingleObject(rs);
	};

	static ResultSetExtractor<Data> extractor = (ResultSet rs) -> {
		return createSingleObject(rs);
	};

	private static Data createSingleObject(ResultSet rs) throws SQLException {
		Data data = null;
		if (rs.next()) {
			data = new Data();
			data.setDate(rs.getTimestamp("date").toLocalDateTime());
			data.setName(rs.getString("name"));
			data.setValue(rs.getDouble("value"));
			data.setSmth(rs.getString("smth"));
		}
		return data;
	}
}
