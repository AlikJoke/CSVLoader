package ru.project.csvloader.jms.events;

import org.junit.runner.notification.RunListener.ThreadSafe;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.project.csvloader.events.EventPublisher;

@Component
@ThreadSafe
@Scope("singleton")
public class JmsEventPublisher implements EventPublisher {

	@Override
	public void publish(boolean needLoad) {
		// TODO
	}
}
