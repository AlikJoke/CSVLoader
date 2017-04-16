package ru.project.csvloader.web.security.auth;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;

import ru.project.csvloader.orm.dao.UserService;

@Service
@Scope("singleton")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@PostConstruct
	public void init() {
		if (userService == null)
			throw new RuntimeException("User service isn't injected!");
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ru.project.csvloader.orm.model.User user = userService.getUserByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("User not found!");

		return new User(user.getUsername(), user.getPassword(),
				Sets.<GrantedAuthority>newHashSet(new SimpleGrantedAuthority(user.getRole().name())));
	}
}
