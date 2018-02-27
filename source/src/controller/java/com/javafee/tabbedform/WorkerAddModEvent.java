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
import com.javafee.hibernate.dto.library.Worker;
import com.javafee.model.WorkerTableModel;
import com.javafee.startform.RegistrationEvent;
import com.javafee.startform.RegistrationEvent.RegistrationFailureCause;
import com.javafee.tabbedform.admworkers.frames.WorkerAddModFrame;

public class WorkerAddModEvent {

	private WorkerAddModFrame workerAddModFrame;

	private RegistrationEvent registrationEvent;
	private WorkerTableModel workerTableModel;

	public void control(Context context, WorkerTableModel workerTableModel) {
		this.workerTableModel = workerTableModel;
		openWorkerAddModFrame(context);

		workerAddModFrame.addWindowListener(new WindowListener() {

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
				Params.getInstance().remove("selectedWorkerShallowClone");
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});

		workerAddModFrame.getCockpitConfirmationPanel().getBtnAccept()
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
		Worker workerShallowClone = (Worker) Params.getInstance().get("selectedWorkerShallowClone");
		// Params.getInstance().remove("selectedWorkerShallowClone");

		workerShallowClone.setPeselNumber(workerAddModFrame.getWorkerDataPanel().getTextFieldPeselNumber().getText());
		workerShallowClone
				.setDocumentNumber(workerAddModFrame.getWorkerDataPanel().getTextFieldDocumentNumber().getText());
		workerShallowClone.setName(workerAddModFrame.getWorkerDataPanel().getTextFieldName().getText());
		workerShallowClone.setSurname(workerAddModFrame.getWorkerDataPanel().getTextFieldSurname().getText());
		workerShallowClone.setAddress(workerAddModFrame.getWorkerDataPanel().getTextFieldAddress().getText());
		workerShallowClone.setCity((City) workerAddModFrame.getWorkerDataPanel().getComboBoxCity().getSelectedItem());
		if(workerAddModFrame.getWorkerDataPanel().getRadioButtonMale().isSelected() || workerAddModFrame.getWorkerDataPanel().getRadioButtonFemale().isSelected())
		workerShallowClone.setSex(
				workerAddModFrame.getWorkerDataPanel().getRadioButtonMale().isSelected() ? Constans.DATA_BASE_MALE_SIGN
						: Constans.DATA_BASE_FEMALE_SIGN);
		workerShallowClone
				.setBirthDate(workerAddModFrame.getWorkerDataPanel().getDateChooserBirthDate().getDate() != null
						? workerAddModFrame.getWorkerDataPanel().getDateChooserBirthDate().getDate()
						: null);
		workerShallowClone.setLogin(workerAddModFrame.getWorkerDataPanel().getTextFieldLogin().getText());

		// if (!validatePasswordFieldIsEmpty())
		// Utils.displayOptionPane(
		// SystemProperties.getInstance().getResourceBundle()
		// .getString("tabClientEvent.validatePasswordFieldIsEmptyWarning2"),
		// SystemProperties.getInstance().getResourceBundle()
		// .getString("tabClientEvent.validatePasswordFieldIsEmptyWarning2Title"),
		// JOptionPane.WARNING_MESSAGE);
		if (!"".equals(workerShallowClone.getPeselNumber())
				&& workerShallowClone.getPeselNumber().length() != Constans.DATA_BASE_PESEL_NUMBER_LENGHT) {
			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle()
							.getString("clientAddModEvent.updatingClientPeselError"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("clientAddModEvent.updatingClientPeselErrorTitle"),
					JOptionPane.ERROR_MESSAGE);
		} else {
			if (Validator.validateClientUpdate(workerShallowClone)) {
				HibernateUtil.beginTransaction();
				HibernateUtil.getSession()
						.evict(workerTableModel.getWorker((Integer) Params.getInstance().get("selectedRowIndex")));
				HibernateUtil.getSession().update(Worker.class.getName(), workerShallowClone);
				HibernateUtil.commitTransaction();
				workerTableModel.setWorker((Integer) Params.getInstance().get("selectedRowIndex"), workerShallowClone);
				workerTableModel.fireTableDataChanged();
				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle()
								.getString("workerAddModEvent.updatingWorkerSuccess"),
						SystemProperties.getInstance().getResourceBundle().getString(
								"workerAddModEvent.updatingWorkerSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);
				Params.getInstance().remove("selectedWorkerShallowClone");
				Params.getInstance().remove("selectedRowIndex");
				workerAddModFrame.dispose();
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

	private void openWorkerAddModFrame(Context context) {
		if (workerAddModFrame == null || (workerAddModFrame != null && !workerAddModFrame.isDisplayable())) {
			workerAddModFrame = new WorkerAddModFrame();
			if (context == Context.MODIFICATION) {
				workerAddModFrame.getWorkerDataPanel().getLblPassword().setVisible(false);
				workerAddModFrame.getWorkerDataPanel().getPasswordField().setVisible(false);
				reloadRegistrationPanel();
			}
			reloadComboBoxCity();
			workerAddModFrame.setVisible(true);
		} else {
			workerAddModFrame.toFront();
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

		workerAddModFrame.getWorkerDataPanel().getComboBoxCity().setModel(comboBoxCity);
	}

	private void fillRegistrationPanel() {
		workerAddModFrame.getWorkerDataPanel().getTextFieldPeselNumber()
				.setText(((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getPeselNumber() != null
						? ((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getPeselNumber().toString()
						: "");

		workerAddModFrame.getWorkerDataPanel().getTextFieldDocumentNumber()
				.setText(((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getDocumentNumber() != null
						? ((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getDocumentNumber()
								.toString()
						: "");

		workerAddModFrame.getWorkerDataPanel().getTextFieldLogin()
				.setText(((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getLogin() != null
						? ((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getLogin().toString()
						: "");

		workerAddModFrame.getWorkerDataPanel().getTextFieldName()
				.setText(((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getName() != null
						? ((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getName().toString()
						: "");

		workerAddModFrame.getWorkerDataPanel().getTextFieldSurname()
				.setText(((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getSurname() != null
						? ((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getSurname().toString()
						: "");

		workerAddModFrame.getWorkerDataPanel().getTextFieldAddress()
				.setText(((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getAddress() != null
						? ((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getAddress().toString()
						: "");

		workerAddModFrame.getWorkerDataPanel().getComboBoxCity().getModel()
				.setSelectedItem(((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getCity() != null
						? ((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getCity()
						: null);

		if (((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getSex() != null
				&& Constans.DATA_BASE_MALE_SIGN.toString()
						.equals(((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getSex().toString()))
			workerAddModFrame.getWorkerDataPanel().getGroupRadioButtonSex()
					.setSelected(workerAddModFrame.getWorkerDataPanel().getRadioButtonMale().getModel(), true);
		else if (((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getSex() != null
				&& Constans.DATA_BASE_FEMALE_SIGN.toString()
						.equals(((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getSex().toString()))
			workerAddModFrame.getWorkerDataPanel().getGroupRadioButtonSex()
					.setSelected(workerAddModFrame.getWorkerDataPanel().getRadioButtonFemale().getModel(), true);

		try {
			workerAddModFrame.getWorkerDataPanel().getDateChooserBirthDate()
					.setDate(((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getBirthDate() != null
							? Constans.APPLICATION_DATE_FORMAT.parse(Constans.APPLICATION_DATE_FORMAT.format(
									((Worker) Params.getInstance().get("selectedWorkerShallowClone")).getBirthDate()))
							: null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void registerNow() {
		if (Validator.validateWorkerPesel(workerAddModFrame.getWorkerDataPanel().getTextFieldPeselNumber().getText())) {
			try {
				Character sex = workerAddModFrame.getWorkerDataPanel().getGroupRadioButtonSex().getSelection() != null
						? workerAddModFrame.getWorkerDataPanel().getGroupRadioButtonSex().getSelection()
								.getActionCommand().charAt(0)
						: null;
				Date birthDate = workerAddModFrame.getWorkerDataPanel().getDateChooserBirthDate().getDate() != null
						? Constans.APPLICATION_DATE_FORMAT.parse(Constans.APPLICATION_DATE_FORMAT
								.format(workerAddModFrame.getWorkerDataPanel().getDateChooserBirthDate().getDate()))
						: null;

				RegistrationEvent.forceClearRegistrationEvenet();
				boolean result = true;
				List<UserData> ud = HibernateUtil.getSession().createQuery("from UserData").list();
				for (UserData u : ud) {
					if (u.getLogin().equals(workerAddModFrame.getWorkerDataPanel().getTextFieldLogin().getText()))
						result = false;
				}
				 if(result) {
					 if (!"".equals(workerAddModFrame.getWorkerDataPanel().getTextFieldPeselNumber().getText())
								&& workerAddModFrame.getWorkerDataPanel().getTextFieldPeselNumber().getText()
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
							workerAddModFrame.getWorkerDataPanel().getTextFieldPeselNumber().getText(),
							workerAddModFrame.getWorkerDataPanel().getTextFieldDocumentNumber().getText(),
							workerAddModFrame.getWorkerDataPanel().getTextFieldName().getText(),
							workerAddModFrame.getWorkerDataPanel().getTextFieldSurname().getText(),
							workerAddModFrame.getWorkerDataPanel().getTextFieldAddress().getText(),
							(City) workerAddModFrame.getWorkerDataPanel().getComboBoxCity().getSelectedItem(), sex,
							birthDate, workerAddModFrame.getWorkerDataPanel().getTextFieldLogin().getText(),
							String.valueOf(workerAddModFrame.getWorkerDataPanel().getPasswordField().getPassword()),
							Role.WORKER_LIBRARIAN);
					workerTableModel.add((Worker) RegistrationEvent.userData);
					workerAddModFrame.dispose();
				} else if (birthDate.before(new Date())) {
					registrationEvent = RegistrationEvent.getInstance(
							workerAddModFrame.getWorkerDataPanel().getTextFieldPeselNumber().getText(),
							workerAddModFrame.getWorkerDataPanel().getTextFieldDocumentNumber().getText(),
							workerAddModFrame.getWorkerDataPanel().getTextFieldName().getText(),
							workerAddModFrame.getWorkerDataPanel().getTextFieldSurname().getText(),
							workerAddModFrame.getWorkerDataPanel().getTextFieldAddress().getText(),
							(City) workerAddModFrame.getWorkerDataPanel().getComboBoxCity().getSelectedItem(), sex,
							birthDate, workerAddModFrame.getWorkerDataPanel().getTextFieldLogin().getText(),
							String.valueOf(workerAddModFrame.getWorkerDataPanel().getPasswordField().getPassword()),
							Role.WORKER_LIBRARIAN);

					workerTableModel.add((Worker) RegistrationEvent.userData);
					workerAddModFrame.dispose();
				} else {
					// Utils.displayOptionPane(
					// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError9"),
					// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationErrorTitle"),
					// JOptionPane.INFORMATION_MESSAGE);
					Params.getInstance().add("INCORRECT_BIRTH_DATE", RegistrationFailureCause.INCORRECT_BIRTH_DATE);
					throw new RefusedRegistrationException("Cannot register to the system");
				}}
				 } else {
						Utils.displayOptionPane(
								SystemProperties.getInstance().getResourceBundle().getString("workerAddModEvent.existingLogin"),
								SystemProperties.getInstance().getResourceBundle()
										.getString("workerAddModEvent.existingLoginTitle"),
								JOptionPane.ERROR_MESSAGE);
				 }
			}

			// if (birthDate != null && birthDate.before(new Date())) {
			// registrationEvent = RegistrationEvent.getInstance(
			// workerAddModFrame.getWorkerDataPanel().getTextFieldPeselNumber().getText(),
			// workerAddModFrame.getWorkerDataPanel().getTextFieldDocumentNumber().getText(),
			// workerAddModFrame.getWorkerDataPanel().getTextFieldName().getText(),
			// workerAddModFrame.getWorkerDataPanel().getTextFieldSurname().getText(),
			// workerAddModFrame.getWorkerDataPanel().getTextFieldAddress().getText(),
			// (City)
			// workerAddModFrame.getWorkerDataPanel().getComboBoxCity().getSelectedItem(),
			// sex,
			// birthDate,
			// workerAddModFrame.getWorkerDataPanel().getTextFieldLogin().getText(),
			// String.valueOf(workerAddModFrame.getWorkerDataPanel().getPasswordField().getPassword()),
			// Role.WORKER_LIBRARIAN);
			//
			// workerTableModel.add((Worker) RegistrationEvent.userData);
			//
			// workerAddModFrame.dispose();
			// } else {
			// Utils.displayOptionPane(
			// "Podana data urodzenia nie jest wczeniejszza od be¿¹cej, wype³nione muszê
			// byæ dane nazwiska, imienia, numeru pesel.",
			// "Z³a data", JOptionPane.INFORMATION_MESSAGE);
			// }
			catch (RefusedRegistrationException e) {
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
		} else

		{
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

		if (workerAddModFrame.getWorkerDataPanel().getTextFieldLogin().getText().isEmpty()
				|| workerAddModFrame.getWorkerDataPanel().getPasswordField().getPassword().length == 0)
			JOptionPane.showMessageDialog(workerAddModFrame,
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