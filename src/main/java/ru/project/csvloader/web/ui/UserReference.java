package ru.project.csvloader.web.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.userdetails.User;

import ru.project.csvloader.web.OptionsReference;
import ru.project.csvloader.web.json.UserResource;

/**
 * Операции работы с пользователями REST-сервиса.
 * 
 * @author Alimurad A. Ramazanov
 * @since 13.04.2017
 * @version 1.0.0
 *
 */
public interface UserReference extends OptionsReference {

	/**
	 * Создание нового пользователя в системе.
	 * <p>
	 * 
	 * @see UserResource
	 * @param user
	 *            - ресурс пользователя для создания, не может быть
	 *            {@code null}.
	 */
	void doPostUser(@NotNull UserResource user);

	/**
	 * Операция включения/выключения фоновой процедуры загрузки файла.
	 * <p>
	 * 
	 * @see User
	 * @param enable
	 *            - {@code true} - включить, {@code false} - выключить.
	 */
	void doPutSwitcher(boolean enable);

	/**
	 * Операция получения списка всех пользователей и загруженных ими файлов.
	 * <p>
	 * 
	 * @see User
	 * @see UserResource
	 * 
	 * @return список пользователей с файлами.
	 */
	List<UserResource> doGetUsers();

	/**
	 * Операция получения файлов загруженных конкретным пользователем.
	 * <p>
	 * 
	 * @see UserResource
	 * 
	 * @return ресурс пользователя с файлами.
	 */
	UserResource doGetUser(@NotEmpty String username);

	@Override
	default void doOptions(HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Allow", "GET, POST, PUT, OPTIONS");
	}
}
