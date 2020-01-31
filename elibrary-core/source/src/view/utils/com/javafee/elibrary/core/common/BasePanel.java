package com.javafee.elibrary.core.common;

import javax.swing.JPanel;

public class BasePanel extends JPanel implements IBasePanel {
	public BasePanel() {
		initialize();
	}

	@Override
	public void initialize() {
		setBackground(Utils.getApplicationUserDefinedColor());
	}
}
