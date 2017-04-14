package ru.project.csvloader.file.pool.model;

/**
 * Перечисление возможных статусов для файла.
 * 
 * @author Alimurad A. Ramazanov
 * @since 14.04.2017
 * @version 1.0.0
 *
 */
public enum Status {

	/**
	 * Доступен для обработки.
	 */
	AVAILABLE,

	/**
	 * На стадии обработки.
	 */
	PROCESSING,

	/**
	 * Удален.
	 */
	DELETED;
}
