package ru.project.csvloader.web.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Objects;
import com.google.common.net.MediaType;

import ru.project.csvloader.db.LoaderService;
import ru.project.csvloader.event.dispatcher.EventDispatcher;
import ru.project.csvloader.orm.dao.UserService;
import ru.project.csvloader.orm.model.FileItem;
import ru.project.csvloader.orm.model.User;
import ru.project.csvloader.web.Reference;
import ru.project.csvloader.web.json.RestResponse;
import ru.project.csvloader.web.ui.CurrentUser;

@Service
public class ReferenceImpl implements Reference {

	static final String PATH = "/loader";

	@Autowired
	private LoaderService loaderService;

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private CurrentUser currentUser;

	@Autowired
	private EventDispatcher dispatcher;

	@Resource(name = "placeHolder")
	private String placeHolder;

	@PostConstruct
	public void init() {
		if (this.loaderService == null)
			throw new RuntimeException("Loader service ins't injected yet!");

		if (this.context == null)
			throw new RuntimeException("Application context isn't injected yet!");
	}

	@Override
	public RestResponse doGetByDate(String date) {
		LocalDateTime ldt = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		return new RestResponse(loaderService.getByDate(ldt));
	}

	@Override
	public List<RestResponse> doGet() {
		return loaderService.getAll().stream().filter(data -> data != null).map(data -> new RestResponse(data))
				.collect(Collectors.toList());
	}

	@Override
	public void doPost(List<MultipartFile> files) {
		files.stream()
				.filter(file -> (Objects.equal(MediaType.CSV_UTF_8, MediaType.parse(file.getContentType()))
						|| Objects.equal(MediaType.OCTET_STREAM, MediaType.parse(file.getContentType()))))
				.forEach(file -> {
					File tempFile = new File(placeHolder);
					try {
						file.transferTo(tempFile);
					} catch (IllegalStateException | IOException e) {
						throw new RuntimeException(e);
					} finally {
						User user = userService.getUserByUsername(currentUser.getCurrentUser().getUsername());
						user.getFiles().add(new FileItem(tempFile));
						userService.loadUser(user);
					}
					dispatcher.dispatch();
				});
	}

	@Override
	public void doPostByUrl(String url) {
		org.springframework.core.io.Resource resource = context.getResource("url://" + url);
		if (!resource.isOpen() || !resource.isReadable())
			throw new RuntimeException("Target resource isn't ready for processing");
		File tempFile = new File(placeHolder);
		OutputStream outStream = null;
		try {
			outStream = new FileOutputStream(tempFile);
			byte[] bytes = new byte[resource.getInputStream().available()];
			resource.getInputStream().read(bytes);
			outStream.write(bytes);
			outStream.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (tempFile.length() > 0) {
				User user = userService.getUserByUsername(currentUser.getCurrentUser().getUsername());
				user.getFiles().add(new FileItem(tempFile));
				userService.loadUser(user);
			}
		}
		dispatcher.dispatch();
	}

}
