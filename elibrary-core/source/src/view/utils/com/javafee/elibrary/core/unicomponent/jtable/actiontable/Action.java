package com.javafee.elibrary.core.unicomponent.jtable.actiontable;

import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Action {
	private String name;
	private ActionWrapper action;

	public Action() {
	}

	public Action(String name, Consumer action) {
		this.name = name;
		this.action = new ActionWrapper(action);
	}
}
