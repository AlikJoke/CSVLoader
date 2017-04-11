package ru.project.csvloader.aop.advisors;

import java.io.File;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EventAdvisor {

	@Resource(name = "placeHolder")
	private String placeHolder;

	@Pointcut(value = "execution(* ru.project.csvloader.context.events.EventListenerAbstract.handle(boolean)) && args(needLoad)")
	private void afterHandlePointCut(boolean needLoad) {

	}

	@After("afterHandlePointCut(needLoad)")
	public void afterHandle(JoinPoint jp, boolean needLoad) {
		File csv = new File(this.placeHolder);
		if (csv.exists())
			csv.delete();
	}
}
