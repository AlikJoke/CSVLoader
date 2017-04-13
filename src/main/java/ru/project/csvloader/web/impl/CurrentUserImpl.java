package ru.project.csvloader.web.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import ru.project.csvloader.web.ui.CurrentUser;

@Component
@Scope("prototype")
public class CurrentUserImpl implements CurrentUser {

	@Override
	public UserDetails getCurrentUser() {
		return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
