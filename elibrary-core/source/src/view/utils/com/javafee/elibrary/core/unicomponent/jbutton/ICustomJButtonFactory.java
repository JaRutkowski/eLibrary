package com.javafee.elibrary.core.unicomponent.jbutton;

import javax.swing.Icon;

public interface ICustomJButtonFactory<B extends CustomJButton> {
	B create(Icon icon, String text);
}
