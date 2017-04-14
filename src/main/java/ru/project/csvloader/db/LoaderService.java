package ru.project.csvloader.db;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.opencsv.CSVReader;

import ru.project.csvloader.file.pool.model.FileWrapper;
import ru.project.csvloader.model.Data;

/**
 * Интерфейс для операций загрузки файла в хранилище.
 * 
 * @author Alimurad A. Ramazanov
 * @since 09.04.2017
 * @version 1.0.0
 *
 */
public interface LoaderService {

	/**
	 * Выгрузка файла в формате csv или xls в список объектов.
	 * <p>
	 * 
	 * @see CSVReader
	 * @see Data
	 * @throws IOException
	 * @return список объектов для загрузки.
	 */
	@NotNull
	List<Data> loadToObjects() throws IOException;

	/**
	 * Загрузка файла в формате csv или xls в БД.
	 * <p>
	 * 
	 * @see File
	 */
	void loadToDB();

	/**
	 * Получение файла из некоторой директории для загрузки в БД.
	 * <p>
	 * 
	 * @see FileWrapper
	 * @throws IOException
	 * @return может быть {@code null}.
	 */
	@Null
	FileWrapper getFileWrapper() throws Exception;

	/**
	 * Получение всех записей из БД.
	 * <p>
	 * 
	 * @see Data
	 * @return не может быть {@code null}. Если записей нет, возвращается
	 *         {@linkplain Collections#EMPTY_LIST}.
	 */
	@NotNull
	List<Data> getAll();

	/**
	 * Получение записи по дате.
	 * <p>
	 * 
	 * @see Data
	 * @see LocalDateTime
	 * @return может быть {@code null}.
	 */
	@NotNull
	Data getByDate(@NotNull LocalDateTime dateTime);
}
