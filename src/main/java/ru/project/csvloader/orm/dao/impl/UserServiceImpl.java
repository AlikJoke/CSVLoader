package ru.project.csvloader.orm.dao.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import ru.project.csvloader.orm.dao.UserRepository;
import ru.project.csvloader.orm.dao.UserService;
import ru.project.csvloader.orm.model.FileItem;
import ru.project.csvloader.orm.model.User;
import ru.project.csvloader.orm.model.item.Role;

@Service
@Repository
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getUsers() {
		return Lists.newArrayList(userRepository.findAll());
	}

	@Override
	public List<FileItem> getFileItemsByUser(String username) {
		return userRepository.findOne(username).getFiles().stream().sorted((file1, file2) -> {
			return file1.getLoadDT().compareTo(file2.getLoadDT());
		}).collect(Collectors.toList());
	}

	@Override
	public List<FileItem> getAllFiles() {
		List<FileItem> files = Lists.newArrayList();
		userRepository.findAll().forEach(user -> files.addAll(user.getFiles()));
		return files.stream().sorted((file1, file2) -> {
			return file1.getLoadDT().compareTo(file2.getLoadDT());
		}).collect(Collectors.toList());
	}

	@Override
	public User getUserByUsername(String username) {
		return userRepository.findOne(username);
	}

	@Override
	public void loadUser(String username, String password, Role role) {
		User user = null;
		if (userRepository.exists(username))
			user = userRepository.findOne(username);
		else
			user = new User(username);

		user.setRole(role);
		user.setPassword(password);
		user.setLastModified(LocalDateTime.now());
		userRepository.save(user);
	}

}
