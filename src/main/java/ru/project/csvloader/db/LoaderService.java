package ru.project.csvloader.db;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.validation.constraints.Null;

import com.opencsv.CSVReader;

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
	 * @param csv
	 *            - файл для загрузки, не может быть {@code null}.
	 * @throws IOException
	 * @return список объектов для загрузки.
	 */
	List<Data> loadToObjects() throws IOException;

	/**
	 * Загрузка файла в формате csv или xls в БД.
	 * <p>
	 * 
	 * @see File
	 * @param csv
	 *            - файл для загрузки, не может быть {@code null}.
	 */
	void loadToDB();

	/**
	 * Получение файла из некоторой директории для загрузки в БД.
	 * <p>
	 * 
	 * @see FileReader
	 * @throws IOException
	 * @return может быть {@code null}.
	 */
	@Null
	FileReader getFile() throws Exception;
}
