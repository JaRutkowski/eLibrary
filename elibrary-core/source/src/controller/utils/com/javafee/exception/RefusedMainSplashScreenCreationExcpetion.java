package com.javafee.exception;

public class RefusedMainSplashScreenCreationExcpetion extends Exception {
	private static final long serialVersionUID = 1L;

	public RefusedMainSplashScreenCreationExcpetion(String s) {
		super(s);
		printStackTrace();
	}
}
