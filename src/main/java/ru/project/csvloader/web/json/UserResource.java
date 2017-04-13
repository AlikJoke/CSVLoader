package ru.project.csvloader.web.json;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import ru.project.csvloader.orm.model.User;
import ru.project.csvloader.orm.model.item.Role;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResource implements Resource<UserResource> {

	@JsonProperty(value = "username", required = true)
	public String username;

	@JsonIgnore
	private String password;

	@JsonProperty(value = "files")
	public List<FileResource> files;

	@JsonIgnore
	private Role role;

	@JsonCreator
	public UserResource(@JsonProperty("username") String username, @JsonProperty("password") String password,
			@JsonProperty("role") Role role) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.files = Lists.newArrayList();
	}

	public UserResource(User user) {
		this.username = user.getUsername();
		this.files = user.getFiles().stream().map(file -> new FileResource(file)).collect(Collectors.toList());
	}

	public UserResource() {
		super();
	}
	
	@JsonIgnore
	public static Function<UserResource, User> resourceToUser = user -> {
		User domain = new User(user.username);
		domain.setPassword(user.password);
		domain.setRole(user.role);
		return domain;
	};
}
