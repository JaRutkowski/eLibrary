package com.javafee.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import org.hibernate.query.Query;

import com.javafee.common.Constans.OutboxTableColumn;
import com.javafee.common.SystemProperties;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.common.message.Message;

public class DraftTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -6021161413421685192L;

	protected List<Message> messages;
	protected String[] columns;

	public DraftTableModel() {
		super();
		this.prepareHibernateDao();
		this.columns = new String[] {
				SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.recipientSimpleDataCol"),
				SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.recipientEmailCol"),
				SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.topicCol"),
				SystemProperties.getInstance().getResourceBundle().getString("outboxTableModel.contentCol") };
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
		this.messages = HibernateUtil.getSession().createQuery("from Message mes where mes.isDraft is true").list();
	}

	@SuppressWarnings("unchecked")
	protected void prepareHibernateDao(String query, List<Object> parameters) {
		Query<Message> resultQuery = HibernateUtil.getSession().createQuery(query);
		AtomicInteger position = new AtomicInteger(0);
		parameters.forEach(param -> {
			resultQuery.setParameter(position.getAndIncrement(), param);
		});
		this.messages = resultQuery.list();
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
				if (counter.get() != message.getRecipient().size())
					recipientsEmails.append(",");
			});
			recipientsEmails.append("]");
			return recipientsEmails.toString();
		case COL_TOPIC:
			return message.getTitle();
		case COL_CONTENT:
			return message.getContent();
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

	public void reloadData(String query, List<Object> parameters) {
		prepareHibernateDao(query, parameters);
		fireTableDataChanged();
	}

	@Override
	public void fireTableChanged(TableModelEvent e) {
		super.fireTableChanged(e);
	}
}
