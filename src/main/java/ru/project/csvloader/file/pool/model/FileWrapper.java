package ru.project.csvloader.file.pool.model;

import java.io.File;

public class FileWrapper {

	private final File file;
	private Status status;

	public FileWrapper(File file) {
		this.file = file;
		this.status = Status.AVAILABLE;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return this.status;
	}

	public File getFile() {
		return this.file;
	}

	public FileWrapper markDeleted() {
		if (this.status == Status.AVAILABLE)
			throw new IllegalStateException("File processing status can't be available for this operation!");
		this.status = Status.DELETED;
		return this;
	}

	public FileWrapper markProcessing() {
		if (this.status == Status.DELETED)
			throw new IllegalStateException("File processing status can't be deleted for this operation!");
		this.status = Status.PROCESSING;
		return this;
	}

	public boolean isDeleted() {
		return this.status == Status.DELETED;
	}
}
