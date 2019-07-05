package com.javafee.form;

import java.awt.*;

import javax.swing.JFrame;

import com.javafee.common.Constants;
import com.javafee.common.Utils;
import com.javafee.startform.StartForm;

import lombok.Getter;

public class BaseForm implements IBaseForm {
	@Getter
	public JFrame frame;

	// Eclipse WindowsBuilder needs no args constructor with
	// initialize method call for all child classes
	public BaseForm() {
		initialize();
	}

	public void initialize() {
		Utils.setLookAndFeel();
		frame = new JFrame();
		frame.getContentPane().setBackground(Utils.getApplicationColor());
		frame.setTitle(Constants.APPLICATION_NAME);
		frame.setIconImage(
				Toolkit.getDefaultToolkit().getImage(StartForm.class.getResource("/images/splashScreen.jpg")));
	}
}
