package ru.project.csvloader.context.events;

import org.springframework.beans.factory.annotation.Autowired;

import ru.project.csvloader.db.LoaderService;

public abstract class EventListenerAbstract implements ru.project.csvloader.events.EventListener {

	@Autowired
	private LoaderService loaderService;

	@Override
	public void handle(boolean needLoad) {
		if (needLoad)
			loaderService.loadToDB();
	}
}
