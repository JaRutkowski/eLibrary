package com.javafee.elibrary.micro.config;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SystemProperties {
	@Getter
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.LANGUAGE_RESOURCE_BUNDLE,
			new Locale(Constants.APPLICATION_LANGUAGE));
}
