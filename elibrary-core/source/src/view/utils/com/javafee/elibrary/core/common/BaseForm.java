package com.javafee.elibrary.core.common;

import java.awt.*;

import javax.swing.JFrame;

import com.javafee.elibrary.core.startform.StartForm;

import lombok.Getter;

public class BaseForm implements IBaseForm {
	@Getter
	public JFrame frame;

	public BaseForm() {
		initialize();
	}

	public void initialize() {
		Utils.setLookAndFeel();
		frame = new JFrame();
		frame.getContentPane().setBackground(Utils.getApplicationUserDefinedColor());
		frame.setTitle(Constants.APPLICATION_NAME);
		frame.setIconImage(
				Toolkit.getDefaultToolkit().getImage(StartForm.class.getResource("/images/splashScreen.jpg")));
	}
}
