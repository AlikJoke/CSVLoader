package ru.project.csvloader.event.dispatcher;

/**
 * Интерфейс диспетчеризации событий в сервисе.
 * 
 * @author Alimurad A. Ramazanov
 * @since 11.04.2017
 * @version 1.0.0
 *
 */
public interface EventDispatcher {

	/**
	 * Метод-диспетчер, определяющий, что использовать для сообщения о загрузке
	 * файла: события Spring или JMS.
	 */
	public void dispatch();
}
