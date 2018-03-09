package com.javafee.tabbedform;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.javafee.common.Constans;
import com.javafee.common.Constans.Context;
import com.javafee.common.Constans.Role;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.common.Validator;
import com.javafee.exception.LogGuiException;
import com.javafee.exception.RefusedRegistrationException;
import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.association.City;
import com.javafee.hibernate.dto.common.UserData;
import com.javafee.hibernate.dto.library.Client;
import com.javafee.model.ClientTableModel;
import com.javafee.startform.RegistrationEvent;
import com.javafee.startform.RegistrationEvent.RegistrationFailureCause;
import com.javafee.tabbedform.clients.frames.ClientAddModFrame;

public class ClientAddModEvent {

	private ClientAddModFrame clientAddModFrame;

	private RegistrationEvent registrationEvent;
	private ClientTableModel clientTableModel;

	public void control(Context context, ClientTableModel clientTableModel) {
		this.clientTableModel = clientTableModel;
		openClientAddModFrame(context);

		clientAddModFrame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
				Params.getInstance().remove("selectedRowIndex");
				Params.getInstance().remove("selectedClientShallowClone");
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});

		clientAddModFrame.getCockpitConfirmationPanel().getBtnAccept()
				.addActionListener(e -> onClickBtnAccept(context));
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
		Client clientShallowClone = (Client) Params.getInstance().get("selectedClientShallowClone");
		// Params.getInstance().remove("selectedClientShallowClone");

		clientShallowClone.setPeselNumber(clientAddModFrame.getClientDataPanel().getTextFieldPeselNumber().getText());
		clientShallowClone
				.setDocumentNumber(clientAddModFrame.getClientDataPanel().getTextFieldDocumentNumber().getText());
		clientShallowClone.setName(clientAddModFrame.getClientDataPanel().getTextFieldName().getText());
		clientShallowClone.setSurname(clientAddModFrame.getClientDataPanel().getTextFieldSurname().getText());
		clientShallowClone.setAddress(clientAddModFrame.getClientDataPanel().getTextFieldAddress().getText());
		clientShallowClone.setCity((City) clientAddModFrame.getClientDataPanel().getComboBoxCity().getSelectedItem());
		if(clientAddModFrame.getClientDataPanel().getRadioButtonMale().isSelected() || clientAddModFrame.getClientDataPanel().getRadioButtonFemale().isSelected())
		clientShallowClone.setSex(
				clientAddModFrame.getClientDataPanel().getRadioButtonMale().isSelected() ? Constans.DATA_BASE_MALE_SIGN
						: Constans.DATA_BASE_FEMALE_SIGN);
		clientShallowClone
				.setBirthDate(clientAddModFrame.getClientDataPanel().getDateChooserBirthDate().getDate() != null
						? clientAddModFrame.getClientDataPanel().getDateChooserBirthDate().getDate()
						: null);
		clientShallowClone.setEMail(clientAddModFrame.getClientDataPanel().getTextFieldEMail().getText());
		clientShallowClone.setLogin(clientAddModFrame.getClientDataPanel().getTextFieldLogin().getText());

		// if (!validatePasswordFieldIsEmpty())
		// Utils.displayOptionPane(
		// SystemProperties.getInstance().getResourceBundle()
		// .getString("tabClientEvent.validatePasswordFieldIsEmptyWarning2"),
		// SystemProperties.getInstance().getResourceBundle()
		// .getString("tabClientEvent.validatePasswordFieldIsEmptyWarning2Title"),
		// JOptionPane.WARNING_MESSAGE);
		if (!"".equals(clientShallowClone.getPeselNumber())
				&& clientShallowClone.getPeselNumber().length() != Constans.DATA_BASE_PESEL_NUMBER_LENGHT) {
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

				Params.getInstance().remove("selectedClientShallowClone");
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

		//

		// reloadClientTable();
		//
		// } else {
		// LogGuiException.logWarning(
		// SystemProperties.getInstance().getResourceBundle()
		// .getString("clientTableModel.constraintViolationErrorTitle"),
		// SystemProperties.getInstance().getResourceBundle()
		// .getString("clientTableModel.constraintViolationError"));
		// }
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
			clientAddModFrame.setVisible(true);
		} else {
			clientAddModFrame.toFront();
		}
	}

	private void reloadRegistrationPanel() {
		reloadComboBoxCity();
		fillRegistrationPanel();
	}

	private void reloadComboBoxCity() {
		DefaultComboBoxModel<City> comboBoxCity = new DefaultComboBoxModel<City>();
		HibernateDao<City, Integer> city = new HibernateDao<City, Integer>(City.class);
		List<City> cityListToSort = city.findAll();
		cityListToSort.sort(Comparator.comparing(City::getName, Comparator.nullsFirst(Comparator.naturalOrder())));
		cityListToSort.forEach(c -> comboBoxCity.addElement(c));

		clientAddModFrame.getClientDataPanel().getComboBoxCity().setModel(comboBoxCity);
	}

	private void fillRegistrationPanel() {
		clientAddModFrame.getClientDataPanel().getTextFieldPeselNumber()
				.setText(((Client) Params.getInstance().get("selectedClientShallowClone")).getPeselNumber() != null
						? ((Client) Params.getInstance().get("selectedClientShallowClone")).getPeselNumber().toString()
						: "");

		clientAddModFrame.getClientDataPanel().getTextFieldDocumentNumber()
				.setText(((Client) Params.getInstance().get("selectedClientShallowClone")).getDocumentNumber() != null
						? ((Client) Params.getInstance().get("selectedClientShallowClone")).getDocumentNumber()
								.toString()
						: "");

		clientAddModFrame.getClientDataPanel().getTextFieldLogin()
				.setText(((Client) Params.getInstance().get("selectedClientShallowClone")).getLogin() != null
						? ((Client) Params.getInstance().get("selectedClientShallowClone")).getLogin().toString()
						: "");
		
		clientAddModFrame.getClientDataPanel().getTextFieldEMail()
		.setText(((Client) Params.getInstance().get("selectedClientShallowClone")).getEMail() != null
				? ((Client) Params.getInstance().get("selectedClientShallowClone")).getEMail().toString()
				: "");

		clientAddModFrame.getClientDataPanel().getTextFieldName()
				.setText(((Client) Params.getInstance().get("selectedClientShallowClone")).getName() != null
						? ((Client) Params.getInstance().get("selectedClientShallowClone")).getName().toString()
						: "");

		clientAddModFrame.getClientDataPanel().getTextFieldSurname()
				.setText(((Client) Params.getInstance().get("selectedClientShallowClone")).getSurname() != null
						? ((Client) Params.getInstance().get("selectedClientShallowClone")).getSurname().toString()
						: "");

		clientAddModFrame.getClientDataPanel().getTextFieldAddress()
				.setText(((Client) Params.getInstance().get("selectedClientShallowClone")).getAddress() != null
						? ((Client) Params.getInstance().get("selectedClientShallowClone")).getAddress().toString()
						: "");

		clientAddModFrame.getClientDataPanel().getComboBoxCity()
				.setSelectedItem(((Client) Params.getInstance().get("selectedClientShallowClone")).getCity() != null
						? ((Client) Params.getInstance().get("selectedClientShallowClone")).getCity()
						: null);

		if (((Client) Params.getInstance().get("selectedClientShallowClone")).getSex() != null
				&& Constans.DATA_BASE_MALE_SIGN.toString()
						.equals(((Client) Params.getInstance().get("selectedClientShallowClone")).getSex().toString()))
			clientAddModFrame.getClientDataPanel().getGroupRadioButtonSex()
					.setSelected(clientAddModFrame.getClientDataPanel().getRadioButtonMale().getModel(), true);
		else if (((Client) Params.getInstance().get("selectedClientShallowClone")).getSex() != null
				&& Constans.DATA_BASE_FEMALE_SIGN.toString()
						.equals(((Client) Params.getInstance().get("selectedClientShallowClone")).getSex().toString()))
			clientAddModFrame.getClientDataPanel().getGroupRadioButtonSex()
					.setSelected(clientAddModFrame.getClientDataPanel().getRadioButtonFemale().getModel(), true);

		try {
			clientAddModFrame.getClientDataPanel().getDateChooserBirthDate()
					.setDate(((Client) Params.getInstance().get("selectedClientShallowClone")).getBirthDate() != null
							? Constans.APPLICATION_DATE_FORMAT.parse(Constans.APPLICATION_DATE_FORMAT.format(
									((Client) Params.getInstance().get("selectedClientShallowClone")).getBirthDate()))
							: null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void registerNow() {
		if (Validator.validateClientPesel(clientAddModFrame.getClientDataPanel().getTextFieldPeselNumber().getText())) {
			try {
				Character sex = clientAddModFrame.getClientDataPanel().getGroupRadioButtonSex().getSelection() != null
						? clientAddModFrame.getClientDataPanel().getGroupRadioButtonSex().getSelection()
								.getActionCommand().charAt(0)
						: null;
				Date birthDate = clientAddModFrame.getClientDataPanel().getDateChooserBirthDate().getDate() != null
						? Constans.APPLICATION_DATE_FORMAT.parse(Constans.APPLICATION_DATE_FORMAT
								.format(clientAddModFrame.getClientDataPanel().getDateChooserBirthDate().getDate()))
						: null;

				RegistrationEvent.forceClearRegistrationEvenet();
				boolean result = true;
				List<UserData> ud = HibernateUtil.getSession().createQuery("from UserData").list();
				for (UserData u : ud) {
					if (u.getLogin().equals(clientAddModFrame.getClientDataPanel().getTextFieldLogin().getText()))
						result = false;
				}
				if (result) {
					if (!"".equals(clientAddModFrame.getClientDataPanel().getTextFieldPeselNumber().getText())
							&& clientAddModFrame.getClientDataPanel().getTextFieldPeselNumber().getText()
									.length() != Constans.DATA_BASE_PESEL_NUMBER_LENGHT) {
						Utils.displayOptionPane(
								SystemProperties.getInstance().getResourceBundle()
										.getString("clientAddModEvent.updatingClientPeselError"),
								SystemProperties.getInstance().getResourceBundle().getString(
										"clientAddModEvent.updatingClientPeselErrorTitle"),
								JOptionPane.ERROR_MESSAGE);
					} else {
						if (birthDate == null) {

							registrationEvent = RegistrationEvent.getInstance(
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
						} else if (birthDate.before(new Date())) {
							registrationEvent = RegistrationEvent.getInstance(
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
							// Utils.displayOptionPane(
							// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError9"),
							// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationErrorTitle"),
							// JOptionPane.INFORMATION_MESSAGE);
							Params.getInstance().add("INCORRECT_BIRTH_DATE",
									RegistrationFailureCause.INCORRECT_BIRTH_DATE);
							throw new RefusedRegistrationException("Cannot register to the system");
						}
					}
				} else {
					Utils.displayOptionPane(
							SystemProperties.getInstance().getResourceBundle()
									.getString("workerAddModEvent.existingLogin"),
							SystemProperties.getInstance().getResourceBundle()
									.getString("workerAddModEvent.existingLoginTitle"),
							JOptionPane.ERROR_MESSAGE);
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

			if (registrationEvent != null)
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