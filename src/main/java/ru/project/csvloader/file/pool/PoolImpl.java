package ru.project.csvloader.file.pool;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import ru.project.csvloader.file.pool.model.FileWrapper;
import ru.project.csvloader.file.pool.model.Status;

@Service
@Scope("singleton")
public class PoolImpl implements Pool {

	private List<FileWrapper> wrappers;

	@Resource(name = "placeHolder")
	private String placeHolder;

	private final static Logger log = LoggerFactory.getLogger(PoolImpl.class);

	@PostConstruct
	public void init() {
		if (placeHolder == null)
			throw new IllegalArgumentException("Files placeholder doesn't set!");

		wrappers = Lists.newArrayList();
	}

	@Override
	public synchronized FileWrapper getFirstAvailable() {
		Optional<FileWrapper> finded = wrappers.parallelStream()
				.filter(wrapper -> wrapper.getStatus() == Status.AVAILABLE).findFirst();
		if (finded.isPresent())
			return finded.get().markProcessing();

		return null;
	}

	@Override
	public Set<FileWrapper> getWrappers() {
		return ImmutableSet.<FileWrapper>copyOf(this.wrappers);
	}

	@Scheduled(fixedDelay = 1000)
	private void scanDirectory() {
		log.info("Start scanning of directory");
		if (wrappers.size() > 0) {
			log.info("Exists files in pool: finish");
			return;
		}

		File directory = new File(this.placeHolder);
		if (directory.isFile())
			throw new IllegalStateException("file.placeholder must be a directory!");

		File[] files = directory.listFiles();
		if (files == null)
			return;
		log.info("In target directory find {} files", files.length);
		wrappers.addAll(Optional.of(Stream.of(files).filter(file -> file.getName().endsWith(".csv") && file.canRead())
				.map(file -> new FileWrapper(file)).collect(Collectors.toList())).orElse(Lists.newArrayList()));
		
		log.info("Finish scanning of directory");
	}

	@Override
	@Async
	public boolean clearDeleted() {
		return this.wrappers.removeIf(wrapper -> wrapper.isDeleted());
	}

}
