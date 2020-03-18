package com.javafee.elibrary.core.unicomponent.jtable.actiontable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class ActionWrapper implements ActionListener {
	private Consumer consumer;

	@Override
	public void actionPerformed(ActionEvent e) {
		consumer.accept(e);
	}
}
