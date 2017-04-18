package ru.project.csvloader.context.messages;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageSource messageSource;

	@Value("messages.default.message")
	private String defaultMessage;

	@PostConstruct
	public void init() {
		if (messageSource == null)
			throw new RuntimeException("Message source isn't injected yet!");
	}

	@Override
	public String getMessage(String code, Object... args) {
		return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
	}

	@Override
	public String getMessage(String code, boolean throwExceptionIfNotExists, Object... args) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}
}
