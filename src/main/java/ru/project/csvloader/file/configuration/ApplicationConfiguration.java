package ru.project.csvloader.file.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class ApplicationConfiguration {

	@Autowired
	private Environment env;
	
	@Bean(name = "placeHolder")
	public String getFilePlaceHolder() {
		return env.getProperty("file.placeholder");
	}

}
