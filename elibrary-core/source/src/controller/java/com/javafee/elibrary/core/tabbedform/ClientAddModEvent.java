package com.javafee.elibrary.core.tabbedform;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Constants.Context;
import com.javafee.elibrary.core.common.Constants.Role;
import com.javafee.elibrary.core.common.IEvent;
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.Validator;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.exception.RefusedRegistrationException;
import com.javafee.elibrary.core.model.ClientTableModel;
import com.javafee.elibrary.core.startform.RegistrationEvent;
import com.javafee.elibrary.core.tabbedform.clients.frames.ClientAddModFrame;
import com.javafee.elibrary.hibernate.dao.HibernateDao;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.association.City;
import com.javafee.elibrary.hibernate.dto.library.Client;

public class ClientAddModEvent implements IEvent {
	private Context context;
	private ClientAddModFrame clientAddModFrame;

	private ClientTableModel clientTableModel;

	public void control(Context context, ClientTableModel clientTableModel) {
		this.context = context;
		this.clientTableModel = clientTableModel;
		openClientAddModFrame(context);
	}

	@Override
	public void initializeEventHandlers() {
		clientAddModFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				Params.getInstance().remove("selectedRowIndex");
				Params.getInstance().remove("selectedClient");
			}
		});

		clientAddModFrame.getCockpitConfirmationPanel().getBtnAccept()
				.addActionListener(e -> onClickBtnAccept(context));
	}

	private void openClientAddModFrame(Context context) {
		if (clientAddModFrame == null || (clientAddModFrame != null && !clientAddModFrame.isDisplayable())) {
			clientAddModFrame = new ClientAddModFrame();
			if (context == Context.MODIFICATION) {
				clientAddModFrame.getClientDataPanel().getLblPassword().setVisible(false);
				clientAddModFrame.getClientDataPanel().getPasswordField().setVisible(false);
				reloadRegistrationPanel();
			}
			reloadComboBoxCity();
			initializeEventHandlers();
			clientAddModFrame.setVisible(true);
		} else {
			clientAddModFrame.toFront();
		}
	}

	private void onClickBtnAccept(Context context) {
		if (context == Context.ADDITION) {
			if (validateRegistration())
				registerNow();
		} else if (context == Context.MODIFICATION) {
			modificateClient();
		}
	}

	private void modificateClient() {
		Client clientShallowClone = (Client) Params.getInstance().get("selectedClient");

		clientShallowClone.setPeselNumber(clientAddModFrame.getClientDataPanel().getTextFieldPeselNumber().getText());
		clientShallowClone
				.setDocumentNumber(clientAddModFrame.getClientDataPanel().getTextFieldDocumentNumber().getText());
		clientShallowClone.setName(clientAddModFrame.getClientDataPanel().getTextFieldName().getText());
		clientShallowClone.setSurname(clientAddModFrame.getClientDataPanel().getTextFieldSurname().getText());
		clientShallowClone.setAddress(clientAddModFrame.getClientDataPanel().getTextFieldAddress().getText());
		clientShallowClone.setCity((City) clientAddModFrame.getClientDataPanel().getComboBoxCity().getSelectedItem());
		if (clientAddModFrame.getClientDataPanel().getRadioButtonMale().isSelected()
				|| clientAddModFrame.getClientDataPanel().getRadioButtonFemale().isSelected())
			clientShallowClone.setSex(clientAddModFrame.getClientDataPanel().getRadioButtonMale().isSelected()
					? Constants.DATA_BASE_MALE_SIGN
					: Constants.DATA_BASE_FEMALE_SIGN);
		clientShallowClone
				.setBirthDate(clientAddModFrame.getClientDataPanel().getDateChooserBirthDate().getDate());
		clientShallowClone.setEMail(clientAddModFrame.getClientDataPanel().getTextFieldEMail().getText());
		clientShallowClone.setLogin(clientAddModFrame.getClientDataPanel().getTextFieldLogin().getText());

		if (!"".equals(clientShallowClone.getPeselNumber())
				&& clientShallowClone.getPeselNumber().length() != Constants.DATA_BASE_PESEL_NUMBER_LENGHT) {
			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle()
							.getString("clientAddModEvent.updatingClientPeselError"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("clientAddModEvent.updatingClientPeselErrorTitle"),
					JOptionPane.ERROR_MESSAGE);
		} else {
			if (Validator.validateClientUpdate(clientShallowClone)) {
				HibernateUtil.beginTransaction();
				HibernateUtil.getSession()
						.evict(clientTableModel.getClient((Integer) Params.getInstance().get("selectedRowIndex")));
				HibernateUtil.getSession().update(Client.class.getName(), clientShallowClone);
				HibernateUtil.commitTransaction();

				clientTableModel.setClient((Integer) Params.getInstance().get("selectedRowIndex"), clientShallowClone);
				clientTableModel.fireTableDataChanged();

				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle()
								.getString("clientAddModEvent.updatingClientSuccess"),
						SystemProperties.getInstance().getResourceBundle().getString(
								"clientAddModEvent.updatingClientSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);

				Params.getInstance().remove("selectedClient");
				Params.getInstance().remove("selectedRowIndex");

				clientAddModFrame.dispose();
			} else {
				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle()
								.getString("clientAddModEvent.updatingClientError"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("clientAddModEvent.updatingClientErrorTitle"),
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void reloadRegistrationPanel() {
		reloadComboBoxCity();
		fillRegistrationPanel();
	}

	private void reloadComboBoxCity() {
		DefaultComboBoxModel<City> comboBoxCityModel = new DefaultComboBoxModel<City>();
		HibernateDao<City, Integer> city = new HibernateDao<City, Integer>(City.class);
		List<City> cityListToSort = city.findAll();
		cityListToSort.sort(Comparator.comparing(City::getName, Comparator.nullsFirst(Comparator.naturalOrder())));
		cityListToSort.forEach(c -> comboBoxCityModel.addElement(c));

		clientAddModFrame.getClientDataPanel().getComboBoxCity().setModel(comboBoxCityModel);
	}

	private void fillRegistrationPanel() {
		Common.fillUserDataPanel(clientAddModFrame.getClientDataPanel(), (Client) Params.getInstance().get("selectedClient"));
	}

	private void registerNow() {
		if (Validator.validateClientPesel(clientAddModFrame.getClientDataPanel().getTextFieldPeselNumber().getText())) {
			try {
				Character sex = clientAddModFrame.getClientDataPanel().getGroupRadioButtonSex().getSelection() != null
						? clientAddModFrame.getClientDataPanel().getGroupRadioButtonSex().getSelection()
						.getActionCommand().charAt(0)
						: null;
				Date birthDate = clientAddModFrame.getClientDataPanel().getDateChooserBirthDate().getDate() != null
						? Constants.APPLICATION_DATE_FORMAT.parse(Constants.APPLICATION_DATE_FORMAT
						.format(clientAddModFrame.getClientDataPanel().getDateChooserBirthDate().getDate()))
						: null;

				RegistrationEvent.forceClearRegistrationEvenet();
				if (!"".equals(clientAddModFrame.getClientDataPanel().getTextFieldPeselNumber().getText())
						&& clientAddModFrame.getClientDataPanel().getTextFieldPeselNumber().getText()
						.length() != Constants.DATA_BASE_PESEL_NUMBER_LENGHT) {
					Utils.displayOptionPane(
							SystemProperties.getInstance().getResourceBundle()
									.getString("clientAddModEvent.updatingClientPeselError"),
							SystemProperties.getInstance().getResourceBundle().getString(
									"clientAddModEvent.updatingClientPeselErrorTitle"),
							JOptionPane.ERROR_MESSAGE);
				} else {
					if (birthDate == null || birthDate.before(new Date())) {
						RegistrationEvent.getInstance(
								clientAddModFrame.getClientDataPanel().getTextFieldPeselNumber().getText(),
								clientAddModFrame.getClientDataPanel().getTextFieldDocumentNumber().getText(),
								clientAddModFrame.getClientDataPanel().getTextFieldName().getText(),
								clientAddModFrame.getClientDataPanel().getTextFieldSurname().getText(),
								clientAddModFrame.getClientDataPanel().getTextFieldAddress().getText(),
								(City) clientAddModFrame.getClientDataPanel().getComboBoxCity().getSelectedItem(),
								sex, birthDate,
								clientAddModFrame.getClientDataPanel().getTextFieldLogin().getText(),
								clientAddModFrame.getClientDataPanel().getTextFieldEMail().getText(),
								String.valueOf(
										clientAddModFrame.getClientDataPanel().getPasswordField().getPassword()),
								Role.CLIENT);

						clientTableModel.add((Client) RegistrationEvent.userData);

						clientAddModFrame.dispose();
					} else {
						Params.getInstance().add("INCORRECT_BIRTH_DATE",
								RegistrationEvent.RegistrationFailureCause.INCORRECT_BIRTH_DATE);
						throw new RefusedRegistrationException("Cannot register to the system");
					}
				}
			} catch (RefusedRegistrationException e) {
				StringBuilder errorBuilder = new StringBuilder();

				if (Params.getInstance().get("ALREADY_REGISTERED") != null) {
					errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.registrationError5"));
					Params.getInstance().remove("ALREADY_REGISTERED");
				}
				if (Params.getInstance().get("PARAMETERS_ERROR") != null) {
					errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.registrationError6"));
				}
				if (Params.getInstance().get("WEAK_PASSWORD") != null) {
					errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.registrationError7"));
				}
				if (Params.getInstance().get("INCORRECT_BIRTH_DATE") != null) {
					errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.registrationError9"));
				}

				LogGuiException.logError(SystemProperties.getInstance().getResourceBundle()
						.getString("startForm.registrationErrorTitle"), errorBuilder.toString(), e);
				clearRegistrationFailsInParams();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (RegistrationEvent.getRegistrationEvent() != null)
				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationSuccess2"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("startForm.registrationSuccess2Title"),
						JOptionPane.INFORMATION_MESSAGE);
		} else {
			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle()
							.getString("clientAddModEvent.updatingClientError"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("clientAddModEvent.updatingClientErrorTitle"),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void clearRegistrationFailsInParams() {
		Params.getInstance().remove("ALREADY_REGISTERED");
		Params.getInstance().remove("PARAMETERS_ERROR");
		Params.getInstance().remove("WEAK_PASSWORD");
		Params.getInstance().remove("INCORRECT_BIRTH_DATE");
	}

	private boolean validateRegistration() {
		boolean result = false;

		if (clientAddModFrame.getClientDataPanel().getTextFieldLogin().getText().isEmpty()
				|| clientAddModFrame.getClientDataPanel().getPasswordField().getPassword().length == 0)
			JOptionPane.showMessageDialog(clientAddModFrame,
					SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.validateRegistrationError8"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.validateRegistrationError8Title"),
					JOptionPane.ERROR_MESSAGE);
		else
			result = true;

		return result;
	}
}