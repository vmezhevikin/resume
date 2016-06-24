package net.devstudy.resume.service.impl;

import java.util.concurrent.ExecutorService;

import javax.mail.internet.InternetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import net.devstudy.resume.entity.Profile;
import net.devstudy.resume.model.NotificationMessage;
import net.devstudy.resume.service.NotificationSenderService;

@Service
public class AsyncEmailNotificationSenderService implements NotificationSenderService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncEmailNotificationSenderService.class);

	@Autowired
	private ExecutorService executorService;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${application.production}")
	private boolean production;

	@Value("${email.fromEmail}")
	private String fromEmail;

	@Value("${email.fromName}")
	private String fromName;

	@Value("${email.sendTryCount}")
	private int tryCount;

	@Override
	public void sendNotification(NotificationMessage message) {
		executorService.submit(new EmailItem(message, tryCount));
	}

	@Override
	public String getDestinationAddress(Profile profile) {
		return profile.getEmail();
	}

	private class EmailItem implements Runnable {
		
		private final NotificationMessage notificationMessage;

		private int tryCount;

		private EmailItem(NotificationMessage notificationMessage, int tryCount) {
			super();
			this.notificationMessage = notificationMessage;
			this.tryCount = tryCount;
		}

		@Override
		public void run() {
			try {
				LOGGER.debug("Send new email to {}", notificationMessage.getDestinationAddress());
				MimeMessageHelper messageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), false);
				messageHelper.setSubject(notificationMessage.getSubject());
				messageHelper.setTo(new InternetAddress(notificationMessage.getDestinationAddress(), notificationMessage.getDestinationName()));
				messageHelper.setFrom(fromEmail, fromEmail);
				messageHelper.setText(notificationMessage.getContent(), true);
				MimeMailMessage message = new MimeMailMessage(messageHelper);
				if (production) {
					javaMailSender.send(message.getMimeMessage());
					LOGGER.info("Email to {} successfully sent", notificationMessage.getDestinationAddress());
				} else {
					LOGGER.debug("Debug: sending email to {}", notificationMessage.getDestinationAddress());
				}
			} catch (Exception e) {
				LOGGER.error("Can't send email to " + notificationMessage.getDestinationAddress() + ": " + e.getMessage(), e);
				tryCount--;
				if (tryCount > 0) {
					LOGGER.debug("Decrement tryCount and try again to send email: tryCount={}, destinationEmail={}", tryCount, notificationMessage.getDestinationAddress());
					executorService.submit(this);
				} else {
					LOGGER.error("Email wasn't sent to " + notificationMessage.getDestinationAddress());
				}
			}
		}
	}
}