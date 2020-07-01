package com.javafee.elibrary.core.common;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.javafee.elibrary.core.exception.RefusedSystemPropertiesLoadingException;
import com.javafee.elibrary.core.process.ProcessFactory;
import com.javafee.elibrary.core.process.ws.FetchCitiesWithElibraryRestApiWS;
import com.javafee.elibrary.hibernate.dao.HibernateDao;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.association.City;
import com.javafee.elibrary.hibernate.dto.common.SystemParameter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class SystemProperties {

	private static SystemProperties systemProperties = null;

	private ResourceBundle resourceBundleLanguage = ResourceBundle.getBundle(Constants.LANGUAGE_RESOURCE_BUNDLE,
			new Locale(Constants.APPLICATION_LANGUAGE));

	@Getter
	private Map<String, SystemParameter> systemParameters;

	@Getter
	@Setter
	private Long citiesDataPackageNumber = 0l;
	@Getter
	@Setter
	private Long citiesFromIndex = 1l;
	@Getter
	@Setter
	private Long citiesPackageSize = 20000l;

	static {
		fetchCitiesPackage();
	}

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
		initializeHibernateUtil();
		initializeSystemParameters();
	}

	private void initializeHibernateUtil() {
		@SuppressWarnings("unused")
		HibernateUtil hibernateUtil = new HibernateUtil();
	}

	public void initializeSystemParameters() {
		this.systemParameters = new HibernateDao<>(SystemParameter.class).findAll().stream()
				.collect(Collectors.toMap(SystemParameter::getName, SystemParameter::_this));
	}

	public static void fetchCitiesPackage() {
		try {
			Common.removeMoreComboBoxCityElementIfExists();
			ProcessFactory.create(FetchCitiesWithElibraryRestApiWS.class).execute();
			Common.getCities().sort(Comparator.comparing(City::getName, Comparator.nullsFirst(Comparator.naturalOrder())));
			Common.prepareMoreComboBoxCityElement(Common.getCities());
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
			log.severe(e.getMessage());
		}
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
