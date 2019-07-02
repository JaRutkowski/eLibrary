package com.javafee.main;

import com.javafee.startform.Actions;

import javax.swing.*;

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
