package ru.project.csvloader.events;

import org.springframework.context.ApplicationEventPublisher;

/**
 * Интерфейс публикации событий в рамках сервиса.
 * 
 * @author Alimurad A. Ramazanov
 * @since 11.04.2017
 * @version 1.0.0
 *
 */
public interface EventPublisher {

	/**
	 * Публикует событие о необходимости загрузки файла.
	 * <p>
	 * 
	 * @see ApplicationEventPublisher
	 * @param needLoad
	 *            - признак, что надо загрузить файл в БД.
	 */
	void publish(boolean needLoad);
}
