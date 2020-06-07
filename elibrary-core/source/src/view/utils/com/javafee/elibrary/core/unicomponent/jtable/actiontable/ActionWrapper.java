package com.javafee.elibrary.core.unicomponent.jtable.actiontable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ActionWrapper implements ActionListener, ItemListener {
	private Consumer consumer;

	@Override
	public void actionPerformed(ActionEvent e) {
		consumer.accept(e);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		consumer.accept(e);
	}
}
