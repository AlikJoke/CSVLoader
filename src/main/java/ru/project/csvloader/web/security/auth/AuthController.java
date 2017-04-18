package ru.project.csvloader.web.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.project.csvloader.web.ControllerWithExceptionHandler;
import ru.project.csvloader.web.security.Security;

@RestController
public class AuthController extends ControllerWithExceptionHandler {

	@Autowired
	private Security security;

	@PostMapping(value = SecurityImpl.PATH_LOGIN)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void doLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
		security.login(username, password);
	}

	@GetMapping(value = SecurityImpl.PATH_LOGOUT)
	@ResponseStatus(HttpStatus.OK)
	public void doLogout() {
		security.logout();
	}
}
