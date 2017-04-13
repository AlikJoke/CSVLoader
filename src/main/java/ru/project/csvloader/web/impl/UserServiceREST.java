package ru.project.csvloader.web.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import ru.project.csvloader.web.ControllerWithExceptionHandler;
import ru.project.csvloader.web.json.UserResource;
import ru.project.csvloader.web.ui.UserReference;

@RestController
public class UserServiceREST extends ControllerWithExceptionHandler {

	@Autowired
	private UserReference ref;

	@RequestMapping(value = UserServiceImpl.PATH, method = RequestMethod.OPTIONS)
	@ResponseStatus(HttpStatus.OK)
	public void doOptions(HttpServletRequest request, HttpServletResponse response) {
		ref.doOptions(request, response);
	}

	@RequestMapping(value = UserServiceImpl.PATH, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("isAuthenticated()")
	public List<UserResource> doGet() {
		return ref.doGetUsers();
	}

	@RequestMapping(value = UserServiceImpl.PATH, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void doPost(@RequestBody UserResource user, BindingResult result) {
		if (result.hasErrors())
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		ref.doPostUser(user);
	}

	@RequestMapping(value = UserServiceImpl.PATH + "/scheduling/{enable}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostAuthorize("hasAuthority('ADMIN')")
	public void doPut(@PathVariable("enable") Boolean enable) {
		ref.doPutSwitcher(enable);
	}

	@RequestMapping(value = UserServiceImpl.PATH + "/{username}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("isAuthenticated()")
	public UserResource doGetUser(@PathVariable("username") String username) {
		return ref.doGetUser(username);
	}
}
