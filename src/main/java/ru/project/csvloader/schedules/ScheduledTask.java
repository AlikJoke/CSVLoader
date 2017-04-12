package ru.project.csvloader.schedules;

import ru.project.csvloader.orm.model.item.Role;

/**
 * Интерфейс для запуска задач с помощью планировщика.
 * 
 * @author Alimurad A. Ramazanov
 * @since 09.04.2017
 * @version 1.0.0
 *
 */
public interface ScheduledTask {

	/**
	 * Запуск задачи по загрузке данных из csv.
	 */
	void scheduledLoad();

	/**
	 * Отключение фоновой задачи загрузки данных. Доступно только пользователю с
	 * ролью {@linkplain Role#ADMIN}.
	 */
	void disableSheduling();

	/**
	 * Включение фоновой задачи загрузки данных. Доступно только пользователю с
	 * ролью {@linkplain Role#ADMIN}.
	 */
	void enableSheduling();
}
