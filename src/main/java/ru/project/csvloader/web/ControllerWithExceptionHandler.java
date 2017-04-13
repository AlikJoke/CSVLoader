package ru.project.csvloader.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Базовый контроллер с обработкой ошибок.
 * 
 * @author Alimurad A. Ramazanov
 * @since 10.04.2017
 * @version 1.0.0
 *
 */
public abstract class ControllerWithExceptionHandler {
	
	@ExceptionHandler(value = RuntimeException.class)
	public void handle(RuntimeException e, HttpServletResponse response, HttpServletRequest request) {
		writeStatusAndMessageIntoResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}

	@ExceptionHandler(value = HttpClientErrorException.class)
	public void handle(HttpClientErrorException e, HttpServletResponse response, HttpServletRequest request) {
		writeStatusAndMessageIntoResponse(response, HttpStatus.BAD_REQUEST, e.getMessage());
	}

	public static void writeStatusAndMessageIntoResponse(HttpServletResponse response, HttpStatus status,
			String message) {
		response.setStatus(status.value());
		response.setContentType("text/plain; charset=UTF-8");
		try {
			byte[] message_ = message.getBytes("UTF-8");
			response.setContentLength(message_.length);
			response.getOutputStream().write(message_);
			response.getOutputStream().flush();
		} catch (Exception e) {
			throw new IllegalStateException("Unable to write text response", e);
		}
	}
}
