package com.javafee.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import com.javafee.common.Constans.OutboxTableColumn;
import com.javafee.common.SystemProperties;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.common.message.Message;

public class OutboxTableModel extends AbstractTableModel {

	protected List<Message> messages;
	private String[] columns;

	public OutboxTableModel() {
		super();
		this.prepareHibernateDao();
		this.columns = new String[] {
				SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.recipientSimpleDataCol"),
				SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.recipientEmailCol"),
				SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.topicCol"),
				SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.contentCol"),
				SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.senderSimpleDataCol"),
				SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.dateCol") };
	}

	public Message getMessage(int index) {
		return messages.get(index);
	}

	public void setMessage(int index, Message message) {
		messages.set(index, message);
	}

	public void add(Message message) {
		messages.add(message);
		this.fireTableDataChanged();
	}

	public void remove(Message message) {
		messages.remove(message);
		this.fireTableDataChanged();
	}

	@SuppressWarnings("unchecked")
	protected void prepareHibernateDao() {
		// as mes join fetch mes.recipient
		this.messages = HibernateUtil.getSession().createQuery("from Message").list();
	}

	private void executeUpdate(String entityName, Object object) {
		HibernateUtil.beginTransaction();
		HibernateUtil.getSession().update(entityName, object);
		HibernateUtil.commitTransaction();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return messages.size();
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
				recipientsEmails.append(recipient.getUserData().getEMail());
				if(counter.get() != message.getRecipient().size()) recipientsEmails.append(",");
				});
			recipientsEmails.append("]");
			return recipientsEmails.toString();
		case COL_TOPIC:
			return message.getTitle();
		case COL_CONTENT:
			return message.getContent();
		case COL_SENDER_SIMPLE_DATA:
			return message.getSender() != null ? message.getSender().getSurname() + " " + message.getSender().getName() : "System";
		case COL_DATE:
			return message.getSendDate();
		default:
			return null;

		}
	}

	@Override
	public String getColumnName(int col) {
		return columns[col];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void reloadData() {
		prepareHibernateDao();
		fireTableDataChanged();
	}

	@Override
	public void fireTableChanged(TableModelEvent e) {
		super.fireTableChanged(e);
	}

}