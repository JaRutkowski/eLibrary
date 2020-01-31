package com.javafee.elibrary.core.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import org.apache.log4j.Logger;

import com.javafee.elibrary.core.common.Constants;

import lombok.Getter;

public class MailSender {
	private final String username = Constants.APPLICATION_EMAIL;
	private final String password = Constants.APPLICATION_EMAIL_PASSWORD;
	@Getter
	private Properties properties;
	@Getter
	private Session session;

	public MailSender() {
		this.defaultConfig();
		this.createSession();
	}

	public boolean send(Message message) {
		boolean result = false;
		try {
			Transport.send(message);
			result = true;
			Logger.getLogger("app").info("Wysłano e-mail");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public void config(String sslTrust, Boolean auth, Boolean starttlsEnable, String host, String port) {
		this.properties = new Properties();
		this.properties.put("mail.smtp.ssl.trust", sslTrust);
		this.properties.put("mail.smtp.auth", auth);
		this.properties.put("mail.smtp.starttls.enable", starttlsEnable);
		this.properties.put("mail.smtp.host", host);
		this.properties.put("mail.smtp.port", port);
	}

	private void defaultConfig() {
		this.properties = new Properties();
		this.properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		this.properties.put("mail.smtp.auth", true);
		this.properties.put("mail.smtp.starttls.enable", true);
		this.properties.put("mail.smtp.host", "smtp.gmail.com");
		this.properties.put("mail.smtp.port", "587");
	}

	private void createSession() {
		this.session = Session.getInstance(properties, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}
}
