package ru.project.csvloader.schedules;

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
}
