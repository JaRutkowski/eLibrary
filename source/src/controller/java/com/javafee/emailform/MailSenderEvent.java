package com.javafee.emailform;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.javafee.common.Constans;
import com.javafee.hibernate.dto.common.UserData;
import com.javafee.mail.MailSender;

public class MailSenderEvent {

	private MailSender mailSender = new MailSender();

	private Message message;

	public boolean control(List<SimpleEntry<Message.RecipientType, UserData>> recipients, String subject, String text) {
		setMessage(recipients, subject, text);
		return mailSender.send(message);
	}

	private void setMessage(List<SimpleEntry<Message.RecipientType, UserData>> recipients, String subject, String text) {
		try {
			message = new MimeMessage(mailSender.getSession());
			message.setFrom(new InternetAddress(Constans.APPLICATION_EMAIL));
			recipients.forEach(recipient -> {
				try {
					if (Message.RecipientType.TO.equals(recipient.getKey()))
						message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient.getValue().getEMail()));
					if (Message.RecipientType.CC.equals(recipient.getKey()))
						message.addRecipient(Message.RecipientType.CC, InternetAddress.parse(recipient.getValue().getEMail())[0]);
					if (Message.RecipientType.BCC.equals(recipient.getKey()))
						message.addRecipient(Message.RecipientType.BCC, InternetAddress.parse(recipient.getValue().getEMail())[0]);

				} catch (MessagingException e) {
					e.printStackTrace();
				}
			});
			message.setSubject(subject);
			message.setText(text);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
