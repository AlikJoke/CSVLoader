package ru.project.csvloader.web.json;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import ru.project.csvloader.orm.model.FileItem;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileResource implements Resource<FileResource> {

	@JsonProperty(value = "filename", required = true)
	public String filename;

	@JsonProperty(value = "size", required = true)
	public Long rows;

	@JsonProperty(value = "size", required = true)
	public Long size;

	@JsonProperty(value = "loadDateTime", required = true)
	public LocalDateTime loadDateTime;

	@JsonCreator
	public FileResource(String filename, Long rows, Long size, LocalDateTime loadDateTime) {
		this.filename = filename;
		this.rows = rows;
		this.size = size;
		this.loadDateTime = loadDateTime;
	}

	public FileResource(FileItem file) {
		this.filename = file.getFileName();
		this.rows = file.getRows();
		this.size = file.getSize();
		this.loadDateTime = file.getLoadDT();
	}
	
	public FileResource() {
		super();
	}
}
