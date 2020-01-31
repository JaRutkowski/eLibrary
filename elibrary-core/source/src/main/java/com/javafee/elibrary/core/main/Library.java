package com.javafee.elibrary.core.main;

import javax.swing.SwingUtilities;

import com.javafee.elibrary.core.startform.Actions;

public class Library {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				Actions actions = new Actions();
				actions.control();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
