package com.javafee.elibrary.core.common;

import java.awt.*;

import javax.swing.JPanel;

public class BasePanel extends JPanel implements IBasePanel {
	public BasePanel() {
		initialize();
	}

	public BasePanel(FlowLayout flowLayout) {
		super(flowLayout);
		initialize();
	}

	@Override
	public void initialize() {
		setBackground(Utils.getApplicationUserDefinedColor());
	}
}
