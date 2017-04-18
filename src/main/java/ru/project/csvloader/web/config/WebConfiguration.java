package ru.project.csvloader.web.config;

import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import ru.project.csvloader.web.impl.UserValidator;

@Configuration
@ComponentScan
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Bean
	@Lazy
	public UserValidator userValidator() {
		return new UserValidator();
	}

	private static final String pattern = "^[0-9]*$";

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		return localeChangeInterceptor;
	}

	@Bean(name = "localeResolver")
	public CookieLocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		Locale defaultLocale = new Locale("ru");
		localeResolver.setDefaultLocale(defaultLocale);
		return localeResolver;
	}

	@Bean
	public MessageSource messageSource() {
		this.checkParameters();
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource
				.setBasenames(StringUtils.tokenizeToStringArray(env.getProperty("spring.messages.basename"), ",;"));
		messageSource.setDefaultEncoding(Optional.of(env.getProperty("spring.messages.encoding")).orElse("UTF-8"));
		messageSource.setCacheSeconds(Integer.parseInt(env.getProperty("spring.messages.cache-seconds")));
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	private void checkParameters() {
		boolean isNumber = Pattern.compile(pattern).matcher(env.getProperty("spring.messages.cache-seconds")).matches();
		if (!isNumber)
			throw new RuntimeException("Parameter 'spring.messages.cache-seconds' is invalid!");

		if (!StringUtils.hasLength(env.getProperty("spring.messages.basename")))
			throw new RuntimeException("Parameter 'spring.messages.basename' isn't provided!");
	}
}
