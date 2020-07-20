package com.javafee.elibrary.core.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
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

	@Getter
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.LANGUAGE_RESOURCE_BUNDLE,
			new Locale(Constants.APPLICATION_LANGUAGE));

	@Getter
	private static Properties configProperties;
	@Getter
	private static Map<String, SystemParameter> systemParameters;

	@Getter
	@Setter
	private Long citiesDataPackageNumber = 0l;
	@Getter
	@Setter
	private Long citiesFromIndex = 1l;

	static {
		initializeHibernateUtil();
		initializeCoreProperties();
		initializeSystemParameters();
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

	/**
	 * Initializes DB connection using settings which are stored in the configuration
	 * properties file.
	 *
	 * @see Constants APPLICATION_PROPERTIES
	 */
	private static void initializeHibernateUtil() {
		@SuppressWarnings("unused")
		HibernateUtil hibernateUtil = new HibernateUtil();
	}

	private static void initializeCoreProperties() {
		configProperties = new Properties();
		try (InputStream resourceAsStream = HibernateUtil.class.getClassLoader().getResourceAsStream(Constants.APPLICATION_PROPERTIES)) {
			configProperties.load(resourceAsStream);
		} catch (IOException e) {
			log.severe(e.getMessage());
		}
	}

	/**
	 * Initializes System Parameters using data stored in DB in <code>com_system_parameters</code> table.
	 * System Parameters are fed by the system as part of the process
	 * <code>{@link com.javafee.elibrary.core.process.initializator.FeedSystemParametersProcess FeedSystemParametersProcess}</code>
	 * if mentioned table is empty. Additionally, number of System Parameters accordingly to the
	 * <code>{@link com.javafee.elibrary.hibernate.dao.common.Constants DATA_BASE_NUMBER_OF_SYSTEM_PARAMETERS}</code>.
	 */
	public static void initializeSystemParameters() {
		systemParameters = new HibernateDao<>(SystemParameter.class).findAll().stream()
				.collect(Collectors.toMap(SystemParameter::getName, SystemParameter::_this));
	}

	/**
	 * Connects to the <i>eLibrary</i> REST API WS and downloads <code>{@link City}</code> data package.
	 * Data package size is set in System Parameters. If these parameters are not set, then system is using
	 * predefined default value stored in <code>{@link Constants}</code>.
	 *
	 * @see Constants APPLICATION_PREDEFINED_COMBO_BOX_PACKAGE_SIZE
	 */
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

	public void setResourceBundleLanguage(ResourceBundle resourceBundleLanguage) {
		this.resourceBundle = resourceBundleLanguage;
	}

	public void setResourceBundleLanguage(String language) {
		this.resourceBundle = ResourceBundle.getBundle("messages", new Locale(language));
	}

	public void setResourceBundleLanguage(Locale locale) {
		this.resourceBundle = ResourceBundle.getBundle("messages", locale);
	}
}
