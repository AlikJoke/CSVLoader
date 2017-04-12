package ru.project.csvloader.jms.events;

import javax.annotation.PostConstruct;

import org.junit.runner.notification.RunListener.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import ru.project.csvloader.events.EventPublisher;

@Component
@ThreadSafe
@Scope("singleton")
public class JmsEventPublisher implements EventPublisher {

	@Autowired
	private Environment env;

	@Autowired
	private JmsTemplate jmsTemplate;

	@PostConstruct
	public void init() {
		if (jmsTemplate == null)
			throw new RuntimeException("Jms template isn't injected!");
	}

	@Override
	public void publish(boolean needLoad) {
		jmsTemplate.convertAndSend(env.getProperty("spring.jms.template.default-destination"), needLoad);
	}
}
