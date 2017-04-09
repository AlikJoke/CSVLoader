package ru.project.csvloader.db.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.opencsv.CSVReader;

import ru.project.csvloader.db.LoaderService;
import ru.project.csvloader.jdbc.utils.JdbcUtils;
import ru.project.csvloader.model.Data;

@Repository
@Service
public class LoaderServiceImpl implements LoaderService {

	@Resource(name = "placeHolder")
	private String placeHolder;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void checkInit() {
		if (!StringUtils.hasLength(this.placeHolder))
			throw new RuntimeException("File placeholder is empty!");

		if (jdbcTemplate == null)
			throw new RuntimeException("JdbcTemplate isn't injected yet!");
	}

	@Override
	public List<Data> loadToObjects() throws IOException {
		if (this.getFile() == null)
			return Collections.emptyList();
		CSVReader reader = new CSVReader(this.getFile());
		List<Data> dataList = Lists.newArrayList();
		String[] line;
		try {
			while ((line = reader.readNext()) != null)
				dataList.add(new Data(line[0], line[1], Double.parseDouble(line[2])));
		} finally {
			reader.close();
		}
		return dataList;
	}

	@Override
	public FileReader getFile() throws IOException {
		File file = new File(this.placeHolder);
		if (!file.exists())
			return null;
		return new FileReader(this.placeHolder);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void loadToDB() {
		try {
			List<Data> dataList;
			if ((dataList = this.loadToObjects()).isEmpty())
				return;

			List<Data> dataListFinal = dataList.stream().filter(data -> this.isExists(data.getDate()))
					.collect(Collectors.toList());
			if (!dataListFinal.isEmpty())
				jdbcTemplate.batchUpdate(JdbcUtils.INSERT_QUERY, new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int pos) throws SQLException {
						ps.setString(1, dataListFinal.get(pos).getName());
						ps.setString(2, dataListFinal.get(pos).getDate());
						ps.setDouble(3, dataListFinal.get(pos).getValue());
						ps.setString(4, dataListFinal.get(pos).getSmth());
					}

					@Override
					public int getBatchSize() {
						return dataList.size();
					}
				});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Transactional(readOnly = true)
	public boolean isExists(final String date) {
		return jdbcTemplate.query(JdbcUtils.SELECT_QUERY, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, date);
			}

		}, new DataMapper()).isEmpty();
	}
}

class DataMapper implements RowMapper<Data> {

	@Override
	public Data mapRow(ResultSet rs, int rowNum) throws SQLException {
		Data data = new Data();
		data.setDate(rs.getString("date"));
		data.setName(rs.getString("name"));
		data.setValue(rs.getDouble("value"));
		data.setSmth(rs.getString("smth"));
		return data;
	}

}
