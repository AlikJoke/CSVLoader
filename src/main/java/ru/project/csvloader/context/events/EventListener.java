package ru.project.csvloader.context.events;

/**
 * Интерфейс слушателя событий.
 * 
 * @author Alimurad A. Ramazanov
 * @since 11.04.2017
 * @version 1.0.0
 *
 */
public interface EventListener {

	/**
	 * Метод-слушатель.
	 * <p>
	 * 
	 * @param needLoad
	 *            - нужно произвести загрузку файла в БД.
	 */
	void handle(boolean needLoad);
}
