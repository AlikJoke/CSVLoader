package ru.project.csvloader.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;

import groovy.transform.Immutable;

@Immutable
public class Data implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4393556451584525022L;

	public Data(String name, LocalDateTime date, Double value) {
		super();
		this.name = name;
		this.date = date;
		this.value = value;
	}

	public Data() {
	}

	@NotNull
	@Max(255)
	private String name;

	@NotNull
	@Past
	private LocalDateTime date;

	@NotNull
	private Double value;

	@Null
	private String smth;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getSmth() {
		return smth;
	}

	public void setSmth(String smth) {
		this.smth = smth;
	}

	@Override
	public String toString() {
		return "Data: [name: " + name + ", date: " + date + ", value: " + value + ", smth: " + smth + "]";
	}
}
