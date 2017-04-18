package ru.project.csvloader.web.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ru.project.csvloader.orm.dao.UserService;
import ru.project.csvloader.web.json.UserResource;

@Component
public class UserValidator implements Validator {

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserResource.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserResource resource = (UserResource) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "empty.username");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "empty.password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "role", "empty.role");

		if (userService.getUserByUsername(resource.username) != null)
			errors.rejectValue("username", "username.already.exists");

		if (resource.username.length() > 32)
			errors.rejectValue("username", "incorrect.username.length");
	}

}
