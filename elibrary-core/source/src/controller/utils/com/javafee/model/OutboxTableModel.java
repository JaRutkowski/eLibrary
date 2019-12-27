package com.javafee.model;

import java.util.concurrent.atomic.AtomicInteger;

import com.javafee.common.Constants;
import com.javafee.common.Constants.OutboxTableColumn;
import com.javafee.common.SystemProperties;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.common.message.Message;

public class OutboxTableModel extends DraftTableModel {

	private static final long serialVersionUID = -1318024792294636748L;

	public OutboxTableModel() {
		super();
		this.prepareHibernateDao();
		this.columns = new String[]{
				SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.recipientSimpleDataCol"),
				SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.recipientEmailCol"),
				SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.topicCol"),
				SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.contentCol"),
				SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.senderSimpleDataCol"),
				SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.dateCol")};
	}

	@SuppressWarnings("unchecked")
	protected void prepareHibernateDao() {
		this.messages = HibernateUtil.getSession()
				.createQuery("from Message mes where mes.isDraft is null or mes.isDraft is false").list();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Message message = messages.get(row);
		switch (OutboxTableColumn.getByNumber(col)) {
			case COL_RECIPIENT_SIMPLE_DATA:
				return message.getRecipient();
			case COL_RECIPIENT_EMAIL:
				AtomicInteger counter = new AtomicInteger(1);
				StringBuilder recipientsEmails = new StringBuilder("[");
				message.getRecipient().forEach(recipient -> {
					if (recipient.getUserData() != null)
						recipientsEmails.append(recipient.getUserData().getEMail());
					else
						recipientsEmails.append(SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.deletedUserAlias"));
					if (counter.get() != message.getRecipient().size())
						recipientsEmails.append(",");
				});
				recipientsEmails.append("]");
				return recipientsEmails.toString();
			case COL_TOPIC:
				return message.getTitle();
			case COL_CONTENT:
				return message.getContent();
			case COL_SENDER_SIMPLE_DATA:
				return message.getSender() != null ? message.getSender().getSurname() + " " + message.getSender().getName()
						: "System";
			case COL_DATE:
				return message.getSendDate() != null ? Constants.APPLICATION_DATE_FORMAT.format(message.getSendDate())
						: null;
			default:
				return null;

		}
	}

}
