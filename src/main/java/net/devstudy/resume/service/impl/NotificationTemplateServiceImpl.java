package net.devstudy.resume.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Service;

import net.devstudy.resume.component.NotificationContentResolver;
import net.devstudy.resume.exception.CantCompleteClientRequestException;
import net.devstudy.resume.model.NotificationMessage;
import net.devstudy.resume.service.NotificationTemplateService;

@Service
public class NotificationTemplateServiceImpl implements NotificationTemplateService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationTemplateServiceImpl.class);

	private Map<String, NotificationMessage> notificationTemplates;

	@Value("${notification.config.path}")
	private String notificationConfigPath;

	@Value("${notification.email.templates.path}")
	private String notificationEmailTemplatesPath;

	@Autowired
	private NotificationContentResolver notificationContentResolver;

	@PostConstruct
	private void postContruct()
	{
		notificationTemplates = Collections.unmodifiableMap(getNotificationTemplates());
		LOGGER.info("Loaded {} notification templates", notificationTemplates.size());
	}

	private Map<String, NotificationMessage> getNotificationTemplates()
	{
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.setValidating(false);
		reader.loadBeanDefinitions(new PathResource(notificationConfigPath));
		return getConfiguredTemplates(beanFactory.getBeansOfType(NotificationMessage.class));
	}
	
	private Map<String, NotificationMessage> getConfiguredTemplates(Map<String, NotificationMessage> notificationTemplates)
	{
		Map<String, NotificationMessage> configuredTemplates = new HashMap<>();
		for (Map.Entry<String, NotificationMessage> entry : notificationTemplates.entrySet())
		{
			NotificationMessage messageTemplate = entry.getValue();
			String htmlContent = readHtmlTemplate(notificationEmailTemplatesPath + messageTemplate.getContent());
			if (StringUtils.isNotBlank(htmlContent))
			{
				messageTemplate.setContent(htmlContent);
				configuredTemplates.put(entry.getKey(), messageTemplate);
			}
		}	
		return configuredTemplates;
	}

	private String readHtmlTemplate(String filePath)
	{
		StringBuilder htmlTemplate = new StringBuilder();
		Scanner scanner = null;
		try
		{
			scanner = new Scanner(new File(filePath));
			while (scanner.hasNext())
				htmlTemplate.append(scanner.nextLine().replace("\t", ""));
			 return htmlTemplate.toString();
		} catch (FileNotFoundException e)
		{
			LOGGER.error("Can't find email template file " + filePath);
			return null;
		} finally
		{
			if (scanner != null)
				scanner.close();
		}
	}

	@Override
	public NotificationMessage createNotificationMessage(String templateName, Object model)
	{
		NotificationMessage message = notificationTemplates.get(templateName);
		if (message == null)
			throw new CantCompleteClientRequestException("Notification template '" + templateName + "' not found");
		String resolvedSubject = notificationContentResolver.resolve(message.getSubject(), model);
		String resolvedContent = notificationContentResolver.resolve(message.getContent(), model);
		return new NotificationMessage(resolvedSubject, resolvedContent);
	}
}
