package ru.project.csvloader.web.impl;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import ru.project.csvloader.web.ControllerWithExceptionHandler;
import ru.project.csvloader.web.Reference;
import ru.project.csvloader.web.json.RestResponse;

@RestController
public class ControllerREST extends ControllerWithExceptionHandler {

	@Autowired
	private Reference ref;

	@RequestMapping(value = ReferenceImpl.PATH, method = RequestMethod.OPTIONS)
	@ResponseStatus(HttpStatus.OK)
	public void doOptions(HttpServletRequest request, HttpServletResponse response) {
		ref.doOptions(request, response);
	}

	@RequestMapping(value = ReferenceImpl.PATH, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("isAuthenticated()")
	public List<RestResponse> doGet() {
		return ref.doGet();
	}

	@RequestMapping(value = ReferenceImpl.PATH + "/byDate", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("isAuthenticated()")
	public RestResponse doGetByDate(@RequestParam("date") String date) {
		return ref.doGetByDate(date);
	}

	@RequestMapping(value = ReferenceImpl.PATH, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@PostAuthorize("hasAuthority('ADMIN')")
	public void doPostByUrl(@RequestParam("url") String url, @RequestParam("files") MultipartFile[] files) {
		if (files.length > 0)
			ref.doPost(Arrays.asList(files));
		else if (StringUtils.hasLength(url))
			ref.doPostByUrl(url);
		else
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Specify params for POST request!");
	}
}
