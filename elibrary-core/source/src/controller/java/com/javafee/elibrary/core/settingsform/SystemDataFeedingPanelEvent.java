package com.javafee.elibrary.core.settingsform;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import org.oxbow.swingbits.util.Strings;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Pair;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.action.IActionForm;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.model.SystemDataFeedingTableModel;
import com.javafee.elibrary.core.model.pojo.SystemDataFeedingPojo;
import com.javafee.elibrary.core.process.ProcessFactory;
import com.javafee.elibrary.core.process.initializator.FeedAdministratorDataProcess;
import com.javafee.elibrary.core.process.initializator.FeedLibraryDataProcess;
import com.javafee.elibrary.core.process.initializator.FeedMessageTypesProcess;
import com.javafee.elibrary.core.process.initializator.FeedSystemDataProcess;
import com.javafee.elibrary.core.process.initializator.FeedSystemParametersProcess;
import com.javafee.elibrary.core.unicomponent.jtable.actiontable.Action;
import com.javafee.elibrary.hibernate.dao.HibernateDao;
import com.javafee.elibrary.hibernate.dao.common.Common;
import com.javafee.elibrary.hibernate.dto.association.MessageType;
import com.javafee.elibrary.hibernate.dto.common.SystemData;
import com.javafee.elibrary.hibernate.dto.common.SystemParameter;

import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class SystemDataFeedingPanelEvent implements IActionForm {
	@Setter
	private SettingsForm settingsForm;

	protected static SystemDataFeedingPanelEvent systemDataFeedingPanelEvent = null;

	private SystemDataFeedingPanelEvent(SettingsForm settingsForm) {
		this.control(settingsForm);
	}

	public static SystemDataFeedingPanelEvent getInstance(SettingsForm settingsForm) {
		if (systemDataFeedingPanelEvent == null)
			systemDataFeedingPanelEvent = new SystemDataFeedingPanelEvent(settingsForm);

		return systemDataFeedingPanelEvent;
	}

	public void control(SettingsForm settingsForm) {
		setSettingsForm(settingsForm);
		initializeForm();

		((SystemDataFeedingTableModel) settingsForm.getSettingsPanel().getSystemDataFeedingPanel().getSystemDataFeedingTablePanel()
				.getSystemDataFeedingTable().getModel()).prepareActionData(Arrays.asList(
				Arrays.asList(new Action(SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTablePanel.administratorData.btnRun"),
								e -> onClickBtnRun()),
						new Action(SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTablePanel.administratorData.btnCheck"),
								e -> onClickBtnCheck())),
				Arrays.asList(new Action(SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTablePanel.messagesAndNotificationsDictionaryData.btnRun"),
								e -> onClickBtnRun()),
						new Action(SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTablePanel.messagesAndNotificationsDictionaryData.btnCheck"),
								e -> onClickBtnCheck())),
				Arrays.asList(new Action(SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTablePanel.systemParametersData.btnRun"),
								e -> onClickBtnRun()),
						new Action(SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTablePanel.systemParametersData.btnCheck"),
								e -> onClickBtnCheck())),
				Arrays.asList(new Action(SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTablePanel.systemData.btnRun"),
								e -> onClickBtnRun()),
						new Action(SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTablePanel.systemData.btnCheck"),
								e -> onClickBtnCheck())),
				Arrays.asList(new Action(SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTablePanel.libraryData.btnRun"),
								e -> onClickBtnRun()),
						new Action(SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTablePanel.libraryData.btnCheck"),
								e -> onClickBtnCheck()))));
		settingsForm.getSettingsPanel().getSystemDataFeedingPanel().getSystemDataFeedingTablePanel()
				.getSystemDataFeedingTable().initialize(2);
		settingsForm.getSettingsPanel().getSystemDataFeedingPanel().getBtnCheckAllDataValues().addActionListener(e -> onClickBtnCheckAllDataValues());
		settingsForm.getSettingsPanel().getSystemDataFeedingPanel().getBtnRunAll().addActionListener(e -> onClickBtnRunAll());
	}

	@Override
	public void initializeForm() {

	}

	private void onClickBtnRun() {
		int selectedRowIndex = settingsForm.getSettingsPanel().getSystemDataFeedingPanel().getSystemDataFeedingTablePanel()
				.getSystemDataFeedingTable().convertRowIndexToModel(settingsForm.getSettingsPanel().getSystemDataFeedingPanel().getSystemDataFeedingTablePanel()
						.getSystemDataFeedingTable().getSelectedRow());
		if (selectedRowIndex != -1) {
			try {
				switch (Constants.SystemDataFeedingTableData.SystemDataFeedingTableRow.getByNumber(settingsForm.getSettingsPanel().getSystemDataFeedingPanel().getSystemDataFeedingTablePanel()
						.getSystemDataFeedingTable().getSelectedRow())) {
					case ROW_ADMINISTRATOR_DATA:
						performRunForAdministratorData();
						break;
					case ROW_MESSAGES_AND_NOTIFICATIONS_DICTIONARIES_DATA:
						performRunForMessagesAndNotificationsDictionariesData();
						break;
					case ROW_SYSTEM_PARAMETERS_DATA:
						performRunForSystemParametersData();
						break;
					case ROW_SYSTEM_DATA:
						performRunForSystemData();
						break;
					case ROW_LIBRARY_DATA:
						performRunForLibraryData();
						break;
				}
				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle()
								.getString("systemDataFeedingPanelEvent.runningProcessSuccess"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("systemDataFeedingPanelEvent.runningProcessSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);
			} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
				LogGuiException.logError(
						SystemProperties.getInstance().getResourceBundle().getString("errorDialog.title"), e);
				log.severe(e.getMessage());
			}
		}
	}

	private void onClickBtnCheck() {
		int selectedRowIndex = settingsForm.getSettingsPanel().getSystemDataFeedingPanel().getSystemDataFeedingTablePanel()
				.getSystemDataFeedingTable().convertRowIndexToModel(settingsForm.getSettingsPanel().getSystemDataFeedingPanel().getSystemDataFeedingTablePanel()
						.getSystemDataFeedingTable().getSelectedRow());
		if (selectedRowIndex != -1) {
			SystemDataFeedingPojo selectedSystemDataFeedingPojo = ((SystemDataFeedingTableModel) settingsForm.getSettingsPanel().getSystemDataFeedingPanel().getSystemDataFeedingTablePanel()
					.getSystemDataFeedingTable().getModel())
					.getSystemDataFeedingPojo(selectedRowIndex);
			switch (Constants.SystemDataFeedingTableData.SystemDataFeedingTableRow.getByNumber(selectedRowIndex)) {
				case ROW_ADMINISTRATOR_DATA:
					performCheckForAdministratorData(selectedSystemDataFeedingPojo);
					break;
				case ROW_MESSAGES_AND_NOTIFICATIONS_DICTIONARIES_DATA:
					performCheckForMessagesAndNotificationsDictionariesData(selectedSystemDataFeedingPojo);
					break;
				case ROW_SYSTEM_PARAMETERS_DATA:
					performCheckForSystemParametersData(selectedSystemDataFeedingPojo);
					break;
				case ROW_SYSTEM_DATA:
					performCheckForSystemData(selectedSystemDataFeedingPojo);
					break;
				case ROW_LIBRARY_DATA:
					performCheckForLibraryData(selectedSystemDataFeedingPojo);
					break;
			}
		}
	}

	private void onClickBtnCheckAllDataValues() {
		boolean administrationDataValidation = performCheckForAdministratorData(((SystemDataFeedingTableModel) settingsForm.getSettingsPanel().getSystemDataFeedingPanel()
				.getSystemDataFeedingTablePanel().getSystemDataFeedingTable().getModel())
				.getSystemDataFeedingPojo(Constants.SystemDataFeedingTableData.SystemDataFeedingTableRow.ROW_ADMINISTRATOR_DATA.getIndex())),
				messagesAndNotificationsDictionariesDataValidation = performCheckForMessagesAndNotificationsDictionariesData(((SystemDataFeedingTableModel) settingsForm.getSettingsPanel().getSystemDataFeedingPanel()
						.getSystemDataFeedingTablePanel().getSystemDataFeedingTable().getModel())
						.getSystemDataFeedingPojo(Constants.SystemDataFeedingTableData.SystemDataFeedingTableRow.ROW_MESSAGES_AND_NOTIFICATIONS_DICTIONARIES_DATA.getIndex())),
				systemParametersDataValidation = performCheckForSystemParametersData(((SystemDataFeedingTableModel) settingsForm.getSettingsPanel().getSystemDataFeedingPanel()
						.getSystemDataFeedingTablePanel().getSystemDataFeedingTable().getModel())
						.getSystemDataFeedingPojo(Constants.SystemDataFeedingTableData.SystemDataFeedingTableRow.ROW_SYSTEM_PARAMETERS_DATA.getIndex())),
				systemDataValidation = performCheckForSystemData(((SystemDataFeedingTableModel) settingsForm.getSettingsPanel().getSystemDataFeedingPanel()
						.getSystemDataFeedingTablePanel().getSystemDataFeedingTable().getModel())
						.getSystemDataFeedingPojo(Constants.SystemDataFeedingTableData.SystemDataFeedingTableRow.ROW_SYSTEM_DATA.getIndex())),
				libraryDataValidation = performCheckForLibraryData(((SystemDataFeedingTableModel) settingsForm.getSettingsPanel().getSystemDataFeedingPanel()
						.getSystemDataFeedingTablePanel().getSystemDataFeedingTable().getModel())
						.getSystemDataFeedingPojo(Constants.SystemDataFeedingTableData.SystemDataFeedingTableRow.ROW_LIBRARY_DATA.getIndex()));
		if (administrationDataValidation && messagesAndNotificationsDictionariesDataValidation && systemParametersDataValidation && systemDataValidation && libraryDataValidation)
			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle()
							.getString("systemDataFeedingPanelEvent.checkingAllDataValidationSuccess"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("systemDataFeedingPanelEvent.checkingAllDataValidationSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
		else
			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle()
							.getString("systemDataFeedingPanelEvent.checkingAllDataValidationError"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("systemDataFeedingPanelEvent.checkingAllDataValidationErrorTitle"),
					JOptionPane.ERROR_MESSAGE);
	}

	private void onClickBtnRunAll() {
		try {
			performRunForAdministratorData();
			performRunForMessagesAndNotificationsDictionariesData();
			performRunForSystemParametersData();
			performRunForSystemData();
			performRunForLibraryData();
			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle()
							.getString("systemDataFeedingPanelEvent.runningProcessesSuccess"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("systemDataFeedingPanelEvent.runningProcessSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
			LogGuiException.logError(
					SystemProperties.getInstance().getResourceBundle().getString("errorDialog.title"), e);
			log.severe(e.getMessage());
		}
	}

	private boolean performCheckForAdministratorData(SystemDataFeedingPojo selectedSystemDataFeedingPojo) {
		Pair<Boolean, String> validatedAdminData = validateAdministratorData();
		selectedSystemDataFeedingPojo.setData(validatedAdminData.getFirst()
				? validatedAdminData.getSecond()
				: SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingPanelEvent.validationDataFailed"));
		((SystemDataFeedingTableModel) settingsForm.getSettingsPanel().getSystemDataFeedingPanel().getSystemDataFeedingTablePanel()
				.getSystemDataFeedingTable().getModel()).update(
				Constants.SystemDataFeedingTableData.SystemDataFeedingTableRow.ROW_ADMINISTRATOR_DATA.getIndex(),
				selectedSystemDataFeedingPojo);
		return validatedAdminData.getFirst();
	}

	private boolean performCheckForMessagesAndNotificationsDictionariesData(SystemDataFeedingPojo selectedSystemDataFeedingPojo) {
		Pair<Boolean, String> validatedMessagesAndNotificationsDictionariesData = validateMessagesAndNotificationsDictionariesData();
		selectedSystemDataFeedingPojo.setData(validatedMessagesAndNotificationsDictionariesData.getFirst()
				? validatedMessagesAndNotificationsDictionariesData.getSecond()
				: SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingPanelEvent.validationDataFailed"));
		((SystemDataFeedingTableModel) settingsForm.getSettingsPanel().getSystemDataFeedingPanel().getSystemDataFeedingTablePanel()
				.getSystemDataFeedingTable().getModel()).update(
				Constants.SystemDataFeedingTableData.SystemDataFeedingTableRow.ROW_MESSAGES_AND_NOTIFICATIONS_DICTIONARIES_DATA.getIndex(),
				selectedSystemDataFeedingPojo);
		return validatedMessagesAndNotificationsDictionariesData.getFirst();
	}

	private boolean performCheckForSystemParametersData(SystemDataFeedingPojo selectedSystemDataFeedingPojo) {
		Pair<Boolean, String> validatedSystemParametersData = validateSystemParametersData();
		selectedSystemDataFeedingPojo.setData(validatedSystemParametersData.getFirst()
				? validatedSystemParametersData.getSecond()
				: SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingPanelEvent.validationDataFailed"));
		((SystemDataFeedingTableModel) settingsForm.getSettingsPanel().getSystemDataFeedingPanel().getSystemDataFeedingTablePanel()
				.getSystemDataFeedingTable().getModel()).update(
				Constants.SystemDataFeedingTableData.SystemDataFeedingTableRow.ROW_SYSTEM_PARAMETERS_DATA.getIndex(),
				selectedSystemDataFeedingPojo);
		return validatedSystemParametersData.getFirst();
	}

	private boolean performCheckForSystemData(SystemDataFeedingPojo selectedSystemDataFeedingPojo) {
		Pair<Boolean, String> validatedSystemData = validateSystemData();
		selectedSystemDataFeedingPojo.setData(validatedSystemData.getFirst()
				? validatedSystemData.getSecond()
				: SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingPanelEvent.validationDataFailed"));
		((SystemDataFeedingTableModel) settingsForm.getSettingsPanel().getSystemDataFeedingPanel().getSystemDataFeedingTablePanel()
				.getSystemDataFeedingTable().getModel()).update(
				Constants.SystemDataFeedingTableData.SystemDataFeedingTableRow.ROW_SYSTEM_DATA.getIndex(),
				selectedSystemDataFeedingPojo);
		return validatedSystemData.getFirst();
	}

	private boolean performCheckForLibraryData(SystemDataFeedingPojo selectedSystemDataFeedingPojo) {
		Pair<Boolean, String> validatedLibraryData = validateLibraryData();
		selectedSystemDataFeedingPojo.setData(validatedLibraryData.getFirst()
				? validatedLibraryData.getSecond()
				: SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingPanelEvent.validationDataFailed"));
		((SystemDataFeedingTableModel) settingsForm.getSettingsPanel().getSystemDataFeedingPanel().getSystemDataFeedingTablePanel()
				.getSystemDataFeedingTable().getModel()).update(
				Constants.SystemDataFeedingTableData.SystemDataFeedingTableRow.ROW_LIBRARY_DATA.getIndex(),
				selectedSystemDataFeedingPojo);
		return validatedLibraryData.getFirst();
	}

	private void performRunForAdministratorData() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		ProcessFactory.create(FeedAdministratorDataProcess.class).execute();
	}

	private void performRunForMessagesAndNotificationsDictionariesData() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		ProcessFactory.create(FeedMessageTypesProcess.class).execute();
	}

	private void performRunForSystemParametersData() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		ProcessFactory.create(FeedSystemParametersProcess.class).execute();
	}

	private void performRunForSystemData() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		ProcessFactory.create(FeedSystemDataProcess.class).execute();
	}

	private void performRunForLibraryData() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		ProcessFactory.create(FeedLibraryDataProcess.class).execute();
	}

	private Pair<Boolean, String> validateAdministratorData() {
		String adminData = Common.findAdminDataByIdAndGetPresentationValue(com.javafee.elibrary.hibernate.dao.common.Constants.DATA_BASE_ADMIN_ID);
		String[] adminDataArray = !Strings.isEmpty(adminData) ? adminData.replace("[", "").replace("]", "").split(",") : null;
		return new Pair<>((!Strings.isEmpty(adminData) && adminDataArray != null && adminDataArray.length > 0)
				&& Constants.DATA_BASE_ADMIN_ID.equals(Integer.parseInt(adminDataArray[0]))
				&& Constants.DATA_BASE_ADMIN_LOGIN.equals(adminDataArray[1])
				&& Constants.DATA_BASE_ADMIN_PASSWORD.equals(adminDataArray[2]), adminData);
	}

	private Pair<Boolean, String> validateMessagesAndNotificationsDictionariesData() {
		List<MessageType> messageTypeList = new HibernateDao<>(MessageType.class).findAll();
		String messagesAndNotificationsDictionariesData = messageTypeList != null && !messageTypeList.isEmpty() ? messageTypeList.toString() : "";
		return new Pair<>((!Strings.isEmpty(messagesAndNotificationsDictionariesData))
				&& messagesAndNotificationsDictionariesData.contains(Constants.DATA_BASE_MESSAGE_TYPE_USR_MESSAGE)
				&& messagesAndNotificationsDictionariesData.contains(Constants.DATA_BASE_MESSAGE_TYPE_SYS_MESSAGE)
				&& messagesAndNotificationsDictionariesData.contains(Constants.DATA_BASE_MESSAGE_TYPE_SYS_NOTIFICATION), messagesAndNotificationsDictionariesData);
	}

	private Pair<Boolean, String> validateSystemParametersData() {
		List<SystemParameter> systemParametersList = new HibernateDao<>(SystemParameter.class).findAll();
		String systemParametersData = systemParametersList != null && !systemParametersList.isEmpty() ? systemParametersList.toString() : "";
		Integer numberOfSystemParameters = new HibernateDao<>(SystemData.class).findByPrimaryKey(Constants.DATA_BASE_SYSTEM_DATA_ID).getNumberOfSystemParameters(),
				numberOfFetchedSystemParameters = !Strings.isEmpty(systemParametersData) ? systemParametersData.split("],").length : 0;
		return new Pair<>(numberOfSystemParameters.equals(numberOfFetchedSystemParameters), systemParametersData);
	}

	private Pair<Boolean, String> validateSystemData() {
		String systemData = Common.findSystemDataByIdAndGetPresentationValue(com.javafee.elibrary.hibernate.dao.common.Constants.DATA_BASE_SYSTEM_DATA_ID);
		return new Pair<>(!Strings.isEmpty(systemData), systemData);
	}

	private Pair<Boolean, String> validateLibraryData() {
		String libraryData = Common.findLibraryDataByIdAndGetPresentationValue(com.javafee.elibrary.hibernate.dao.common.Constants.DATA_BASE_LIBRARY_DATA_ID);
		return new Pair<>(!Strings.isEmpty(libraryData), libraryData);
	}
}
