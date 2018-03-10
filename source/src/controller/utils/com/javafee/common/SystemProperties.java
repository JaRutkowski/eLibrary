package com.javafee.common;

import java.util.Locale;
import java.util.ResourceBundle;

import com.javafee.exception.RefusedSystemPropertiesLoadingException;
import com.javafee.hibernate.dao.HibernateUtil;

public class SystemProperties {
	private static SystemProperties systemProperties = null;
	private ResourceBundle resourceBundleLanguage = ResourceBundle.getBundle(Constans.LANGUAGE_RESOURCE_BUNDLE,
			new Locale(Constans.APPLICATION_LANGUAGE));

	private SystemProperties() {
	}

	public static SystemProperties getInstance() {
		if (systemProperties == null)
			systemProperties = new SystemProperties();
		else
			new RefusedSystemPropertiesLoadingException("Cannot system properties loading or was already created");
		return systemProperties;
	}

	public void initializeSystem() {
		initializeHibenrateUtil();
	}

	private void initializeHibenrateUtil() {
		@SuppressWarnings("unused")
		HibernateUtil hibernateUtil = new HibernateUtil();
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundleLanguage;
	}

	public void setResourceBundleLanguage(ResourceBundle resourceBundleLanguage) {
		this.resourceBundleLanguage = resourceBundleLanguage;
	}

	public void setResourceBundleLanguage(String language) {
		this.resourceBundleLanguage = ResourceBundle.getBundle("messages", new Locale(language));
	}

	public void setResourceBundleLanguage(Locale locale) {
		this.resourceBundleLanguage = ResourceBundle.getBundle("messages", locale);
	}
}
