package ru.project.csvloader.schedules.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ru.project.csvloader.event.dispatcher.EventDispatcher;
import ru.project.csvloader.schedules.ScheduledTask;

@Component
public class ScheduledLoader implements ScheduledTask {

	@Autowired
	private EventDispatcher eventDispatcher;

	@Override
	@Scheduled(fixedRate = 10000)
	public void scheduledLoad() {
		eventDispatcher.dispatch();
	}
}
