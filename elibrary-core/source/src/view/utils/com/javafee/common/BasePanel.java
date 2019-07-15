package com.javafee.common;

import javax.swing.JPanel;

public class BasePanel extends JPanel implements IBasePanel {
	public BasePanel() {
		initialize();
	}

	@Override
	public void initialize() {
		setBackground(Utils.getApplicationUserDefineColor());
	}
}
