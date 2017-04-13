package ru.project.csvloader.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Общий интерфейс для OPTIONS-запроса.
 * 
 * @author Alimurad A. Ramazanov
 * @since 13.04.2017
 * @version 1.0.0
 *
 */
public interface OptionsReference {

	/**
	 * Выполняет OPTIONS-запрос для получения допустимых методов.
	 * <p>
	 * 
	 * @see HttpServletResponse
	 * @see HttpServletRequest
	 * 
	 * @param request
	 *            - запрос к серверу.
	 * @param response
	 *            - ответ сервера.
	 */
	void doOptions(HttpServletRequest request, HttpServletResponse response);
}
