package ru.project.csvloader.aop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;

import ru.project.csvloader.aop.advisors.EventAdvisor;

@Configuration
@EnableAspectJAutoProxy
public class AopConfiguration {

	@Bean
	@Lazy
	public EventAdvisor eventAdvisor() {
		return new EventAdvisor();
	}
}
