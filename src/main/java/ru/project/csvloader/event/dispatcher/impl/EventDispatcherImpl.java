package ru.project.csvloader.event.dispatcher.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.project.csvloader.context.events.impl.EventPublisherImpl;
import ru.project.csvloader.event.dispatcher.EventDispatcher;
import ru.project.csvloader.jms.events.JmsEventPublisher;

@Component
@PropertySource(value = { "classpath:application.properties" })
@Scope("singleton")
public class EventDispatcherImpl implements EventDispatcher {

	@Value("${event.dispatcher.type}")
	private int type;

	@Autowired
	private EventPublisherImpl eventPublisherImpl;

	@Autowired
	private JmsEventPublisher jmsEventPublisherImpl;

	@Override
	public void dispatch() {
		switch (type) {
		case 1:
			jmsEventPublisherImpl.publish(true);
			break;
		case 0:
		default:
			eventPublisherImpl.publish(true);
			break;
		}
	}

}
