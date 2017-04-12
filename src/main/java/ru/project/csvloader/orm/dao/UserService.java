package ru.project.csvloader.orm.dao;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.NotEmpty;

import ru.project.csvloader.orm.model.FileItem;
import ru.project.csvloader.orm.model.User;
import ru.project.csvloader.orm.model.item.Role;

/**
 * Интерфейс взаимодействия с данными.
 * 
 * @author Alimurad A. Ramazanov
 * @since 13.04.2017
 * @version 1.0.0
 *
 */
public interface UserService {

	/**
	 * Получение списка пользователей.
	 * <p>
	 * 
	 * @see User
	 * @return может быть {@code null}.
	 */
	@Null
	List<User> getUsers();

	/**
	 * Получение списка файлов, загруженных пользователем.
	 * <p>
	 * 
	 * @see User
	 * @see FileItem
	 * 
	 * @param username
	 *            - имя пользователя, не может быть {@code null}.
	 * 
	 * @return может быть {@code null}.
	 */
	@Null
	List<FileItem> getFileItemsByUser(@NotNull String username);

	/**
	 * Получение всего списка загруженных файлов пользователями.
	 * <p>
	 * 
	 * @see User
	 * @see FileItem
	 * @return может быть {@code null}.
	 */
	@Null
	List<FileItem> getAllFiles();

	/**
	 * Получение пользователя по его имени.
	 * <p>
	 * 
	 * @see User
	 * @param username
	 *            - имя пользователя, не может быть {@code null}.
	 * @return может быть {@code null}.
	 */
	@Null
	User getUserByUsername(@NotNull @NotEmpty String username);

	/**
	 * Создание или обновление пользователя с заданными именем, паролем и ролью.
	 * <p>
	 * 
	 * @see User
	 * @see Role
	 * 
	 * @param username
	 *            - имя пользователя, не может быть {@code null}.
	 * @param password
	 *            - пароль пользователя, не может быть {@code null}.
	 * @param role
	 *            - роль пользователя, не может быть {@code null}.
	 * 
	 * @return может быть {@code null}.
	 */
	void loadUser(@NotNull String username, @NotNull String password, @NotNull Role role);
}
