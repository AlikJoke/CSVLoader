package ru.project.csvloader.context.events.impl;

import org.junit.runner.notification.RunListener.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.project.csvloader.events.EventPublisher;

@Component
@ThreadSafe
@Scope("singleton")
public class EventPublisherImpl implements EventPublisher {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Override
	public void publish(boolean needLoad) {
		publisher.publishEvent(needLoad);
	}

}
