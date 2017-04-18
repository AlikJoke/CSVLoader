package ru.project.csvloader.web.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@Resource(name = "userValidator")
	private UserValidator validator;

	@InitBinder("user")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

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

	@PostMapping(value = UserServiceImpl.PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void doPost(@Valid @RequestBody UserResource user, BindingResult result) {
		if (result.hasErrors())
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		ref.doPostUser(user);
	}

	@PutMapping(value = UserServiceImpl.PATH + "/scheduling/{enable}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostAuthorize("hasAuthority('ADMIN')")
	public void doPut(@PathVariable("enable") Boolean enable) {
		ref.doPutSwitcher(enable);
	}

	@GetMapping(value = UserServiceImpl.PATH + "/{username}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("isAuthenticated()")
	public UserResource doGetUser(@PathVariable("username") String username) {
		return ref.doGetUser(username);
	}
}
