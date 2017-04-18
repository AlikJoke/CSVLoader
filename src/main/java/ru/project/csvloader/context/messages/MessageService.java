package ru.project.csvloader.context.messages;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

/**
 * Интерфейс сервиса сообщений в приложении.
 * 
 * @author Alimurad A. Ramazanov
 * @since 19.04.2017
 * @version 1.0.0
 *
 */
public interface MessageService {

	/**
	 * Получение сообщения по указанному коду. Если сообщения с таким кодом нет,
	 * то будет выдано сообщение "по умолчанию".
	 * <p>
	 * 
	 * @see MessageSource
	 * @param code
	 *            - код сообщения, не может быть {@code null}.
	 * @param args
	 *            - аргументы для сообщения.
	 * @return сообщение из файла.
	 */
	@NotNull
	String getMessage(@NotNull @NotEmpty String code, Object... args);

	/**
	 * Получение сообщения по указанному коду. Если сообщения с таким кодом нет,
	 * то будет выброшена ошибка.
	 * <p>
	 * 
	 * @see MessageSource
	 * @param code
	 *            - код сообщения, не может быть {@code null}.
	 * @param throwExceptionIfNotExists
	 *            - признак, что необходимо бросить ошибку, если сообщение с
	 *            данным кодом не найдено.
	 * @param args
	 *            - аргументы для сообщения.
	 * @throws NoSuchMessageException
	 * @return сообщение из файла.
	 */
	@NotNull
	String getMessage(@NotNull @NotEmpty String code, boolean throwExceptionIfNotExists, Object... args);
}
