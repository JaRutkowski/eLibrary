package com.javafee.unicomponent.border;

import java.awt.*;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.javafee.common.Utils;

public class CustomTitledBorder extends TitledBorder {
	public CustomTitledBorder(Border border,
	                          String title,
	                          int titleJustification,
	                          int titlePosition,
	                          Font titleFont,
	                          Color titleColor) {
		super(border, title, titleJustification, titlePosition, titleFont, titleColor);
		setTitleFont(Utils.getApplicationUserDefinedFont());
	}
}
