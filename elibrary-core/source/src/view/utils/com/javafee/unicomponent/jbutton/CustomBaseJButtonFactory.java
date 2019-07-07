package com.javafee.unicomponent.jbutton;

import javax.swing.Icon;

public interface CustomBaseJButtonFactory<B extends CustomBaseJButton> {
	B create(Icon icon, String text);
}
