package ru.project.csvloader.web.ui;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Интерфейс представляющий текущего авторизованного пользователя.
 * 
 * @author Alimurad A. Ramazanov
 * @since 13.04.2017
 * @version 1.0.0
 *
 */
public interface CurrentUser {

	/**
	 * Возвращает данные о авторизованном пользователе.
	 * <p>
	 * 
	 * @see UserDetails
	 * @return не может быть {@code null}.
	 */
	UserDetails getCurrentUser();
}
