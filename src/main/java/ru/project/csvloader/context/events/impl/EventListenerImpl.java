package ru.project.csvloader.context.events.impl;

import org.junit.runner.notification.RunListener.ThreadSafe;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import ru.project.csvloader.context.events.EventListenerAbstract;

@Component
@ThreadSafe
@Scope("singleton")
public class EventListenerImpl extends EventListenerAbstract {

	@EventListener
	@Override
	public void handle(boolean needLoad) {
		super.handle(needLoad);
	}

}
