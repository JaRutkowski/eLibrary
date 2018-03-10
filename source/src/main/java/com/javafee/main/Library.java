package com.javafee.main;

import javax.swing.SwingUtilities;

import com.javafee.startform.Actions;

public class Library {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Actions actions = new Actions();
					actions.control();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
