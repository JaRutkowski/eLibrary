package com.javafee.unicomponent.jbutton;

import java.awt.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.javafee.common.Constants.Button_Type;
import com.javafee.common.SystemProperties;

public final class CustomJButtonFactory {
	public static final CustomBaseJButton createCustomJButton(Button_Type button_type) {
		String resourceImageName = null, resourceStringName = null;
		switch (button_type) {
			case ACCEPT:
				resourceImageName = "/images/btnAccept-ico.png";
				resourceStringName = "cockpitConfirmationPanel.btnAccept";
				break;
			case DENY:
				resourceImageName = "/images/btnDeny-ico.png";
				resourceStringName = "cockpitConfirmationPanel.btnDeny";
		}

		Icon icon = new ImageIcon(new ImageIcon(CustomJButtonFactory.class.getResource(resourceImageName))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH));
		String text = SystemProperties.getInstance().getResourceBundle().getString(resourceStringName);

		CustomBaseJButtonFactory<CustomBaseJButton> customBaseJButtonFactory = CustomBaseJButton::new;
		return customBaseJButtonFactory.create(icon, text);
	}
}
