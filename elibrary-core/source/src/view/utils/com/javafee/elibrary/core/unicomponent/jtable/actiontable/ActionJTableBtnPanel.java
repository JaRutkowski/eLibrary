package com.javafee.elibrary.core.unicomponent.jtable.actiontable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import com.javafee.elibrary.core.common.BasePanel;

class ActionJTableBtnPanel extends BasePanel {
	List<JButton> buttons = new ArrayList<>();

	public ActionJTableBtnPanel() {
		super(new FlowLayout(FlowLayout.LEADING));
		setOpaque(true);
	}

	protected void updateButtons(Object value) {
		if (value instanceof List) {
			removeAll();
			for (var e : ((List<Action>) value)) {
				JButton b = new JButton(e.getName());
				b.setFocusable(false);
				b.setRolloverEnabled(false);
				add(b);
				buttons.add(b);
				buttons.get(buttons.size() - 1).addActionListener(e.getAction());
			}
		}
	}
}
