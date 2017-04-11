package ru.project.csvloader.jms.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.StringUtils;

@EnableJms
@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class JmsConfiguration {

	@Value("${spring.activemq.broker-url}")
	private String brokerUrl;

	@Value("${spring.jms.template.default-destination}")
	private String destinationName;

	@Value("${spring.jms.template.receive-timeout}")
	private int receiveTimeout;

	@Bean
	@Lazy
	public ConnectionFactory connectionFactory() {
		if (!StringUtils.hasLength(this.brokerUrl))
			throw new RuntimeException("Broker url can't be empty or null! Check it!");

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.brokerUrl);
		connectionFactory.setObjectMessageSerializationDefered(true);
		connectionFactory.setCopyMessageOnSend(false);
		connectionFactory.setStatsEnabled(true);

		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
		return cachingConnectionFactory;
	}

	@Bean
	@Lazy
	public JmsTemplate jmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
		jmsTemplate.setDefaultDestination(new ActiveMQQueue(this.destinationName));
		jmsTemplate.setSessionTransacted(true);
		jmsTemplate.setReceiveTimeout(this.receiveTimeout);
		return jmsTemplate;
	}
}
