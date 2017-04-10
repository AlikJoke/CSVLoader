package ru.project.csvloader.jdbc.utils;

public class JdbcUtils {

	public final static String INSERT_QUERY = "INSERT INTO stats VALUES(?, ?, ?, ?::timestamp)";
	public final static String UPDATE_QUERY = "UPDATE stats SET name = ?, value = ?, smth = ? WHERE date = ?";
	public final static String SELECT_BY_DATE_QUERY = "SELECT date, name, value, smth FROM stats WHERE date = ?";
	public final static String SELECT_ALL_QUERY = "SELECT date, name, value, smth FROM stats";
}
