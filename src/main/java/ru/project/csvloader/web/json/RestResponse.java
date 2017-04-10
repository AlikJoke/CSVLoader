package ru.project.csvloader.web.json;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import ru.project.csvloader.model.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestResponse {

	@JsonProperty("name")
	public final String name;
	@JsonProperty("dateTime")
	public final LocalDateTime dateTime;
	@JsonProperty("value")
	public final Double value;
	@JsonProperty("smth")
	public final String smth;

	public RestResponse(String name, LocalDateTime dateTime, Double value, String smth) {
		super();
		this.name = name;
		this.dateTime = dateTime;
		this.value = value;
		this.smth = smth;
	}

	public RestResponse(Data data) {
		this.name = data.getName();
		this.dateTime = data.getDate();
		this.value = data.getValue();
		this.smth = data.getSmth();
	}
}
