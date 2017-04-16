package ru.project.csvloader.orm.dao.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import ru.project.csvloader.orm.dao.UserRepository;
import ru.project.csvloader.orm.dao.UserService;
import ru.project.csvloader.orm.model.FileItem;
import ru.project.csvloader.orm.model.User;

@Service
@Repository
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	@Lazy
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public List<User> getUsers() {
		return Lists.newArrayList(userRepository.findAll());
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<FileItem> getFileItemsByUser(String username) {
		return userRepository.findOne(username).getFiles().stream().sorted((file1, file2) -> {
			return file1.getLoadDT().compareTo(file2.getLoadDT());
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<FileItem> getAllFiles() {
		List<FileItem> files = Lists.newArrayList();
		userRepository.findAll().forEach(user -> files.addAll(user.getFiles()));
		return files.stream().sorted((file1, file2) -> {
			return file1.getLoadDT().compareTo(file2.getLoadDT());
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public User getUserByUsername(String username) {
		return userRepository.findOne(username);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void loadUser(User user) {
		if (user.getPassword() == null)
			throw new RuntimeException("Password must be provided!");

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setLastModified(LocalDateTime.now());
		userRepository.save(user);
	}

}
