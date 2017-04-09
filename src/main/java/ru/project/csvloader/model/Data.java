package ru.project.csvloader.model;

public class Data {

	public Data(String name, String date, Double value) {
		super();
		this.name = name;
		this.date = date;
		this.value = value;
	}

	public Data() {
	}

	private String name;
	private String date;
	private Double value;
	private String smth;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
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
