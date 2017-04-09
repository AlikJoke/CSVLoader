package ru.project.csvloader.schedules.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ru.project.csvloader.db.LoaderService;
import ru.project.csvloader.schedules.ScheduledTask;

@Component
public class ScheduledLoader implements ScheduledTask {

	@Autowired
	private LoaderService loaderService;

	@Override
	@Scheduled(fixedRate = 5000)
	public void scheduledLoad() {
		loaderService.loadToDB();
	}
}
