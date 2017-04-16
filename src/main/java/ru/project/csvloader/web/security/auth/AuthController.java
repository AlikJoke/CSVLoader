package ru.project.csvloader.web.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.project.csvloader.web.ControllerWithExceptionHandler;
import ru.project.csvloader.web.security.Security;

@RestController
public class AuthController extends ControllerWithExceptionHandler {

	@Autowired
	private Security security;

	@RequestMapping(value = SecurityImpl.PATH_LOGIN, method = RequestMethod.POST)
	public void doLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
		security.login(username, password);
	}

	@RequestMapping(value = SecurityImpl.PATH_LOGOUT, method = RequestMethod.GET)
	public void doLogout() {
		security.logout();
	}
}
