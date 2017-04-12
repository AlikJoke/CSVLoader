package ru.project.csvloader.orm.dao;

import org.springframework.data.repository.CrudRepository;

import ru.project.csvloader.orm.model.User;

/**
 * Интерфейс-репозиторий для операций с сущностью {@link User}.
 * 
 * @author Alimurad A. Ramazanov
 * @version 1.0.0
 * @since 13.04.2017
 *
 */
public interface UserRepository extends CrudRepository<User, String> {

}
