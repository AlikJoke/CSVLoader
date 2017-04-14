package ru.project.csvloader.file.pool;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.google.common.collect.ImmutableSet;

import ru.project.csvloader.file.pool.model.FileWrapper;
import ru.project.csvloader.file.pool.model.Status;

/**
 * Интерфейс, представляющий из себя пул оберток файлов для загрузки в БД.
 * 
 * @author Alimurad A. Ramazanov
 * @since 15.04.2017
 * @version 1.0.0
 *
 */
public interface Pool {

	/**
	 * Возвращает первый "свободный" файл для обработки в статус
	 * {@linkplain Status#AVAILABLE}. Переводит в статус
	 * {@linkplain Status#PROCESSING}.
	 * <p>
	 * 
	 * @see Status
	 * @see FileWrapper
	 * 
	 * @return может быть {@code null}, если в директории нет файлов нужного
	 *         формата.
	 */
	@Null
	FileWrapper getFirstAvailable();

	/**
	 * Получение списка всех "оберток" для файлов во всех статусах.
	 * <p>
	 * 
	 * @see FileWrapper
	 * @see ImmutableSet
	 * @return неизменяемый список "оберток", не может быть {@code null}.
	 */
	@NotNull
	Set<FileWrapper> getWrappers();

	/**
	 * Запуск асинхронной функции очистки списка "оберток" от файлов в статусе
	 * {@linkplain Status#DELETED}.
	 * <p>
	 * 
	 * @return {@code true}, если были найдены файлы для удаления, {@code false}
	 *         - иначе.
	 */
	boolean clearDeleted();
}
