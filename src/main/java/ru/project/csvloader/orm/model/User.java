package ru.project.csvloader.orm.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.google.common.collect.Sets;

import ru.project.csvloader.orm.model.item.Role;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "user_name", length = 32, unique = true, nullable = false)
	private String username;

	@Column(name = "encrypted_password", length = 255, nullable = false)
	private String password;

	@Version
	@Column(name = "last_modified", nullable = false)
	private Timestamp lastModified;

	@Column(name = "role", nullable = true)
	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "user", targetEntity = FileItem.class)
	private Set<FileItem> files;

	public User(String username) {
		this.username = username;
	}

	public User() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getLastModified() {
		return lastModified.toLocalDateTime();
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = Timestamp.valueOf(lastModified);
	}

	public Set<FileItem> getFiles() {
		if (this.files == null)
			this.files = Sets.newHashSet();
		return files;
	}

	public void setFiles(Set<FileItem> files) {
		this.files = files;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
