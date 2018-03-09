package com.javafee.mail;

import java.lang.System.Logger.Level;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.pattern.LogEvent;

import com.javafee.common.Constans;

public class MailSender {
	private final String username = Constans.APPLICATION_EMAIL;
	private final String password = Constans.APPLICATION_EMAIL_PASSWORD;
	private Properties properties;
	private Session session;
	
	public MailSender() {
		this.defaultConfig();
		this.createSession();
	}
	
	public void send(Message message) {
		try {
			Transport.send(message);
			Logger.getLogger("app").info("Wys³ano e-mail");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Properties getProperties() {
		return properties;
	}
	
	public Session getSession() {
		return session;
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
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}
}
