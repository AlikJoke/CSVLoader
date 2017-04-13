package ru.project.csvloader.web.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import ru.project.csvloader.orm.dao.UserService;
import ru.project.csvloader.orm.model.User;
import ru.project.csvloader.schedules.ScheduledTask;
import ru.project.csvloader.web.json.UserResource;
import ru.project.csvloader.web.ui.UserReference;

@Service("webUserService")
public class UserServiceImpl implements UserReference {

	static final String PATH = "/users";

	@Autowired
	private UserService userService;

	@Autowired
	private ScheduledTask scheduledService;

	@PostConstruct
	public void init() {
		if (this.userService == null)
			throw new RuntimeException("User service isn't injected!");
	}

	@Override
	public void doPostUser(UserResource user) {
		userService.loadUser(UserResource.resourceToUser.apply(user));
	}

	@Override
	public void doPutSwitcher(boolean enable) {
		if (enable)
			scheduledService.enableSheduling();
		else
			scheduledService.disableSheduling();
	}

	@Override
	public List<UserResource> doGetUsers() {
		return userService.getUsers().stream().map(user -> new UserResource(user)).collect(Collectors.toList());
	}

	@Override
	public UserResource doGetUser(String username) {
		User user = userService.getUserByUsername(username);
		if (user == null)
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		return new UserResource(user);
	}

}
