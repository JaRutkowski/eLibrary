package com.javafee.elibrary.core.tabbedform;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;
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
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.Validator;
import com.javafee.elibrary.core.common.action.IEvent;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.exception.RefusedRegistrationException;
import com.javafee.elibrary.core.model.WorkerTableModel;
import com.javafee.elibrary.core.startform.RegistrationEvent;
import com.javafee.elibrary.core.tabbedform.admworkers.frames.WorkerAddModFrame;
import com.javafee.elibrary.hibernate.dao.HibernateDao;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.association.City;
import com.javafee.elibrary.hibernate.dto.library.Worker;

public class WorkerAddModEvent implements IEvent {
	private Context context;
	private WorkerTableModel workerTableModel;

	private WorkerAddModFrame workerAddModFrame;

	public void control(Context context, WorkerTableModel workerTableModel) {
		this.context = context;
		this.workerTableModel = workerTableModel;
		openWorkerAddModFrame(context);
	}

	@Override
	public void initializeEventHandlers() {
		workerAddModFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				Params.getInstance().remove("selectedRowIndex");
				Params.getInstance().remove("selectedWorker");
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
		Worker workerShallowClone = (Worker) Params.getInstance().get("selectedWorker");

		workerShallowClone.setPeselNumber(workerAddModFrame.getWorkerDataPanel().getTextFieldPeselNumber().getText());
		workerShallowClone
				.setDocumentNumber(workerAddModFrame.getWorkerDataPanel().getTextFieldDocumentNumber().getText());
		workerShallowClone.setName(workerAddModFrame.getWorkerDataPanel().getTextFieldName().getText());
		workerShallowClone.setSurname(workerAddModFrame.getWorkerDataPanel().getTextFieldSurname().getText());
		workerShallowClone.setAddress(workerAddModFrame.getWorkerDataPanel().getTextFieldAddress().getText());
		workerShallowClone.setCity((City) workerAddModFrame.getWorkerDataPanel().getComboBoxCity().getSelectedItem());
		if (workerAddModFrame.getWorkerDataPanel().getRadioButtonMale().isSelected()
				|| workerAddModFrame.getWorkerDataPanel().getRadioButtonFemale().isSelected())
			workerShallowClone.setSex(workerAddModFrame.getWorkerDataPanel().getRadioButtonMale().isSelected()
					? Constants.DATA_BASE_MALE_SIGN
					: Constants.DATA_BASE_FEMALE_SIGN);
		workerShallowClone
				.setBirthDate(workerAddModFrame.getWorkerDataPanel().getDateChooserBirthDate().getDate());
		workerShallowClone.setEMail(workerAddModFrame.getWorkerDataPanel().getTextFieldEMail().getText());
		workerShallowClone.setLogin(workerAddModFrame.getWorkerDataPanel().getTextFieldLogin().getText());

		if (!"".equals(workerShallowClone.getPeselNumber())
				&& workerShallowClone.getPeselNumber().length() != Constants.DATA_BASE_PESEL_NUMBER_LENGHT) {
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
				Params.getInstance().remove("selectedWorker");
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
			initializeEventHandlers();
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
		DefaultComboBoxModel<City> comboBoxCityModel = new DefaultComboBoxModel<City>();
		HibernateDao<City, Integer> city = new HibernateDao<City, Integer>(City.class);
		List<City> cityListToSort = city.findAll();
		cityListToSort.sort(Comparator.comparing(City::getName, Comparator.nullsFirst(Comparator.naturalOrder())));
		cityListToSort.forEach(c -> comboBoxCityModel.addElement(c));

		workerAddModFrame.getWorkerDataPanel().getComboBoxCity().setModel(comboBoxCityModel);
	}

	private void fillRegistrationPanel() {
		Common.fillUserDataPanel(workerAddModFrame.getWorkerDataPanel(), (Worker) Params.getInstance().get("selectedWorker"));
	}

	@SuppressWarnings("unchecked")
	private void registerNow() {
		if (Validator.validateWorkerPesel(workerAddModFrame.getWorkerDataPanel().getTextFieldPeselNumber().getText())) {
			try {
				Character sex = workerAddModFrame.getWorkerDataPanel().getGroupRadioButtonSex().getSelection() != null
						? workerAddModFrame.getWorkerDataPanel().getGroupRadioButtonSex().getSelection()
						.getActionCommand().charAt(0)
						: null;
				Date birthDate = workerAddModFrame.getWorkerDataPanel().getDateChooserBirthDate().getDate() != null
						? Constants.APPLICATION_DATE_FORMAT.parse(Constants.APPLICATION_DATE_FORMAT
						.format(workerAddModFrame.getWorkerDataPanel().getDateChooserBirthDate().getDate()))
						: null;

				RegistrationEvent.forceClearRegistrationEvenet();
				if (!"".equals(workerAddModFrame.getWorkerDataPanel().getTextFieldPeselNumber().getText())
						&& workerAddModFrame.getWorkerDataPanel().getTextFieldPeselNumber().getText()
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
								workerAddModFrame.getWorkerDataPanel().getTextFieldPeselNumber().getText(),
								workerAddModFrame.getWorkerDataPanel().getTextFieldDocumentNumber().getText(),
								workerAddModFrame.getWorkerDataPanel().getTextFieldName().getText(),
								workerAddModFrame.getWorkerDataPanel().getTextFieldSurname().getText(),
								workerAddModFrame.getWorkerDataPanel().getTextFieldAddress().getText(),
								(City) workerAddModFrame.getWorkerDataPanel().getComboBoxCity().getSelectedItem(),
								sex, birthDate,
								workerAddModFrame.getWorkerDataPanel().getTextFieldLogin().getText(),
								workerAddModFrame.getWorkerDataPanel().getTextFieldEMail().getText(),
								String.valueOf(
										workerAddModFrame.getWorkerDataPanel().getPasswordField().getPassword()),
								Role.WORKER_LIBRARIAN);
						workerTableModel.add((Worker) RegistrationEvent.userData);
						workerAddModFrame.dispose();
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
					errorBuilder.append(MessageFormat.format(SystemProperties.getInstance().getResourceBundle()
									.getString("startForm.registrationError7"),
							SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MIN_PASSWORD_LENGTH).getValue(),
							SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MAX_PASSWORD_LENGTH).getValue()));
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