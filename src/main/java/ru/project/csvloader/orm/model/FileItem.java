package ru.project.csvloader.orm.model;

import java.io.File;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "files")
public class FileItem {

	@Id
	@Column(name = "id", unique = true, updatable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private String id;

	@Column(name = "file_name", nullable = false, unique = false)
	private String fileName;

	@Column(name = "size", nullable = false)
	private Long size;

	@Column(name = "rows", nullable = true)
	private Long rows;

	@Column(name = "load_DT", nullable = false)
	private LocalDateTime loadDT;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "user_name", nullable = false)
	private User user;

	public FileItem(@NotNull File file) {
		this.fileName = file.getName();
		this.rows = null;
		this.size = file.length();
		this.loadDT = LocalDateTime.now();
	}

	protected FileItem() {

	}

	public LocalDateTime getLoadDT() {
		return loadDT;
	}
	
	public String getId() {
		return id;
	}

	public String getFileName() {
		return fileName;
	}

	public Long getSize() {
		return size;
	}

	public Long getRows() {
		return rows;
	}

	public void setRows(Long rows) {
		this.rows = rows;
	}
}
