package ru.project.csvloader.schedules.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ru.project.csvloader.event.dispatcher.EventDispatcher;
import ru.project.csvloader.schedules.ScheduledTask;

@Component
@Scope("singleton")
public class ScheduledTaskImpl implements ScheduledTask {

	@Autowired
	private EventDispatcher eventDispatcher;

	private boolean enableSheduling;

	@PostConstruct
	public void init() {
		if (this.eventDispatcher == null)
			throw new RuntimeException("Event dispatcher isn't injected yet!");
		this.enableSheduling = true;
	}

	@Override
	@Scheduled(fixedRate = 10000)
	public void scheduledLoad() {
		if (enableSheduling)
			eventDispatcher.dispatch();
	}

	@Override
	public void disableSheduling() {
		this.enableSheduling = false;
	}

	@Override
	public void enableSheduling() {
		this.enableSheduling = true;
	}
}
