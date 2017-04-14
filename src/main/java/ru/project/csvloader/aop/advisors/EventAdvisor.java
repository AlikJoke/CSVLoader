package ru.project.csvloader.aop.advisors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.project.csvloader.file.pool.Pool;

@Aspect
@Component
public class EventAdvisor {

	@Resource(name = "placeHolder")
	private String placeHolder;

	@Autowired
	private Pool pool;

	@PostConstruct
	public void init() {
		if (this.pool == null)
			throw new RuntimeException("Pool isn't injected yet!");
	}

	@Pointcut(value = "execution(* ru.project.csvloader.context.events.EventListenerAbstract.handle(boolean))")
	private void afterHandlePointCut() {

	}

	@After("afterHandlePointCut()")
	public void afterHandle(JoinPoint jp) {
		pool.getWrappers().parallelStream().filter(wrapper -> wrapper.isDeleted())
				.forEach(wrapper -> wrapper.getFile().delete());
		pool.clearDeleted();
	}
}
