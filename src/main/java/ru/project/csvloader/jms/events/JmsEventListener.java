package ru.project.csvloader.jms.events;

import org.junit.runner.notification.RunListener.ThreadSafe;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.project.csvloader.context.events.EventListenerAbstract;

@Component
@ThreadSafe
@Scope("singleton")
public class JmsEventListener extends EventListenerAbstract {

	@Override
	public void handle(boolean needLoad) {
		super.handle(needLoad);
	}
}
