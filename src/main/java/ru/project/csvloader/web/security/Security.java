package ru.project.csvloader.web.security;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Интерфейс входа в систему.
 * 
 * @author Alimurad A. Ramazanov
 * @since 16.04.2017
 * @version 1.0.0
 *
 */
public interface Security {

	/**
	 * Авторизация в системе.
	 * <p>
	 * 
	 * @see SecurityContextHolder
	 * @see AuthenticationManager
	 * 
	 * @param username
	 *            - имя пользователя, не может быть {@code null}.
	 * @param password
	 *            - пароль пользователя, не может быть {@code null}.
	 * @throws UsernameNotFoundException
	 */
	void login(@NotNull @NotEmpty String username, @NotNull @NotEmpty String password);

	/**
	 * Выход в системе.
	 * <p>
	 * 
	 * @see SecurityContextHolder
	 * @see AuthenticationManager
	 */
	void logout();
}
