package ru.project.csvloader.web;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.springframework.web.multipart.MultipartFile;

import ru.project.csvloader.model.Data;
import ru.project.csvloader.web.json.RestResponse;

/**
 * Интерфейс, включающий в себя все возможные операции REST-сервиса.
 * 
 * @author Alimurad A. Ramazanov
 * @since 10.04.2017
 * @version 1.0.0
 *
 */
public interface Reference extends OptionsReference {

	/**
	 * Получение записи по дате-времени.
	 * <p>
	 * 
	 * @see RestResponse
	 * @see Data
	 * 
	 * @param date
	 *            - дата-время, не может быть {@code null}.
	 * @return может быть {@code null}, если записи по такому ключу не нашлось.
	 * @throws IllegalArgumentException
	 *             - если {@code date} не парсится в соответствии с
	 *             {@link LocalDateTime}.
	 */
	@Null
	RestResponse doGetByDate(@NotNull String date);

	/**
	 * Получение всего содержимого базы.
	 * <p>
	 * 
	 * @see RestResponse
	 * @return может быть {@code null}.
	 */
	@Null
	List<RestResponse> doGet();

	/**
	 * Загрузка переданных файлов в базу.
	 * <p>
	 * 
	 * @param files
	 *            - список файлов, переданных через мультипарт-запрос, не может
	 *            быть {@code null}.
	 * @throws IllegalArgumentException
	 *             - если {@code files == null}.
	 */
	void doPost(@NotNull List<MultipartFile> files);

	/**
	 * Загрузка файла в базу по переданному URL.
	 * <p>
	 * 
	 * @param url
	 *            - не может быть {@code null}.
	 * @throws IllegalArgumentException
	 *             - если {@code url == null}.
	 */
	void doPostByUrl(@NotNull String url);

	@Override
	default void doOptions(HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Allow", "GET, POST, OPTIONS");
	}
}
