package com.javafee.tabbedform;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.RollbackException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.hibernate.Session;

import com.javafee.common.Constans;
import com.javafee.common.Constans.LendTableColumn;
import com.javafee.common.Constans.Role;
import com.javafee.exception.LogGuiException;
import com.javafee.exception.RefusedLibraryEventLoadingException;
import com.javafee.exception.RefusedLoanServiceEventLoadingException;
import com.javafee.common.IActionForm;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.Author;
import com.javafee.hibernate.dto.library.Book;
import com.javafee.hibernate.dto.library.Category;
import com.javafee.hibernate.dto.library.Client;
import com.javafee.hibernate.dto.library.Lend;
import com.javafee.hibernate.dto.library.PublishingHouse;
import com.javafee.hibernate.dto.library.Volume;
import com.javafee.model.ClientTableModel;
import com.javafee.model.LoanReservationTableModel;
import com.javafee.model.LoanTableModel;
import com.javafee.model.VolumeTableModel;
import com.javafee.startform.LogInEvent;

import helper.ClientHelper;

public class TabLoadServiceEvent implements IActionForm {
	@SuppressWarnings("unused")
	private TabbedForm tabbedForm;

	@SuppressWarnings("unused")
	private Set<Volume> lendBook = new HashSet<Volume>();

	@SuppressWarnings("unused")
	private static TabLoadServiceEvent loadServiceEvent = null;

	public TabLoadServiceEvent(TabbedForm tabbedForm) {
		this.control(tabbedForm);
	}

	public static TabLoadServiceEvent getInstance(TabbedForm tabbedForm) {
		((VolumeTableModel) tabbedForm.getLoanServicePanel_new().getVolumeLoanTable().getModel()).reloadData();
		((ClientTableModel) tabbedForm.getLoanServicePanel_new().getClientTable().getModel()).reloadData();
		if (loadServiceEvent == null) {
			loadServiceEvent = new TabLoadServiceEvent(tabbedForm);
		} else
			new RefusedLoanServiceEventLoadingException("Cannot loan service event loading");
		return loadServiceEvent;
	}

	public void control(TabbedForm tabbedForm) {
		setTabbedForm(tabbedForm);
		initializeForm();

		tabbedForm.getLoanServicePanel_new().getBtnLoan().addActionListener(e -> {
			try {
				prepareReservation();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		});
		// tabbedForm.getLoanServicePanel_new().getBtnNewButton_1().addActionListener(e
		// -> onClickBtnLoan());
		tabbedForm.getLoanServicePanel_new().getBtnReservation().addActionListener(e -> onClickBtnReservation());
		tabbedForm.getLoanServicePanel_new().getBtnProlongation().addActionListener(e -> onClickBtnProlongation());
		tabbedForm.getLoanServicePanel_new().getBtnReturn().addActionListener(e -> onClickBtnReturn());

		tabbedForm.getPanelLoanService().getLoanDataPanel().getCockpitEditionPanel().getBtnAdd()
				.addActionListener(e -> onClickBtnAdd());
		tabbedForm.getPanelLoanService().getBtnProlongation().addActionListener(e -> onClickBtnProlongation2());
		// tabbedForm.getLoanServicePanel_new().getButtonLoanedBookReservation()
		// .addActionListener(e -> reservationQueue());
		tabbedForm.getLoanServicePanel_new().getBtnPenalty().addActionListener(e -> onClickBtnPenalty());
		tabbedForm.getLoanServicePanel_new().getLoanTable().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting())
				onClientTableListSelectionChange();
		});
		tabbedForm.getLoanServicePanel_new().getLoanTable().getModel().addTableModelListener(e -> onTableLoanChanged());
		tabbedForm.getLoanServicePanel_new().getBtnCancelReservation()
				.addActionListener(e -> onClickBtnCancelReservation());

	}

	private void onClickBtnCancelReservation() {
		if (tabbedForm.getLoanServicePanel_new().getReservationTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getLoanServicePanel_new().getReservationTable().convertRowIndexToModel(
					tabbedForm.getLoanServicePanel_new().getReservationTable().getSelectedRow());

			if (selectedRowIndex != -1) {
				Lend selectedLend = ((LoanReservationTableModel) tabbedForm.getLoanServicePanel_new()
						.getReservationTable().getModel()).getLend(selectedRowIndex);
				selectedLend.setReservationClient(null);
				selectedLend.getVolume().setIsReserve(false);

				HibernateUtil.beginTransaction();
				HibernateUtil.getSession().update(Lend.class.getName(), selectedLend);
				HibernateUtil.commitTransaction();

				((LoanReservationTableModel) tabbedForm.getLoanServicePanel_new().getReservationTable().getModel())
						.reloadData();
				((LoanTableModel) tabbedForm.getLoanServicePanel_new().getLoanTable().getModel()).reloadData();

				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabLoanServiceEvent.reservationCancelSuccess"),
						SystemProperties.getInstance().getResourceBundle().getString(
								"tabLoanServiceEvent.reservationCancelSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarning"));
		}
	}

	private void onTableLoanChanged() {
		if (((LoanTableModel) tabbedForm.getLoanServicePanel_new().getLoanTable().getModel()).getLends().isEmpty()) {
			tabbedForm.getLoanServicePanel_new().getBtnProlongation().setEnabled(false);
			tabbedForm.getLoanServicePanel_new().getBtnReturn().setEnabled(false);
			tabbedForm.getLoanServicePanel_new().getBtnReservation().setEnabled(false);
			tabbedForm.getLoanServicePanel_new().getBtnPenalty().setEnabled(false);
		} else {
			tabbedForm.getLoanServicePanel_new().getBtnProlongation().setEnabled(true);
			tabbedForm.getLoanServicePanel_new().getBtnReturn().setEnabled(true);
			tabbedForm.getLoanServicePanel_new().getBtnReservation().setEnabled(true);
			tabbedForm.getLoanServicePanel_new().getBtnPenalty().setEnabled(true);
		}
	}

	private void onClientTableListSelectionChange() {
		if (tabbedForm.getLoanServicePanel_new().getLoanTable().getSelectedRow() != -1
				&& tabbedForm.getLoanServicePanel_new().getLoanTable().convertRowIndexToModel(
						tabbedForm.getLoanServicePanel_new().getLoanTable().getSelectedRow()) != -1) {
			if (calculatePenalty() == new BigDecimal(0).doubleValue())
				tabbedForm.getLoanServicePanel_new().getBtnPenalty().setEnabled(false);
			else
				tabbedForm.getLoanServicePanel_new().getBtnPenalty().setEnabled(true);
		}
	}

	@Override
	public void initializeForm() {
		onTableLoanChanged();
		reloadBookFilterPanel();
		reloaLoadServicePanel();
		setVisibleControls();
	}

	public void setTabbedForm(TabbedForm tabbedForm) {
		this.tabbedForm = tabbedForm;
	}

	private void reloadBookFilterPanel() {
		reloadComboBoxAuthor();
		reloadComboBoxCategory();
		reloadComboBoxPublishingHouse();
	}

	private void reloaLoadServicePanel() {
		reloadComboBoxBook();
		reloadComboBoxClient();
	}

	private void reloadTextAreaDetails() {
		Book selectedBook = (Book) tabbedForm.getPanelLoanService().getLoanDataPanel().getComboBoxBook()
				.getSelectedItem();

		StringBuilder author = new StringBuilder();
		StringBuilder category = new StringBuilder();
		StringBuilder publishingHouse = new StringBuilder();
		selectedBook.getAuthor().forEach(e -> author.append(
				e.getSurname() != null ? e.getSurname() : "-" + " " + e.getSurname() != null ? e.getName() : "-")
				.append("\n"));
		selectedBook.getCategory().forEach(e -> category.append(e.getName() != null ? e.getName() : "-").append("\n"));
		selectedBook.getPublishingHouse()
				.forEach(e -> publishingHouse.append(e.getName() != null ? e.getName() : "-").append("\n"));

		tabbedForm.getPanelLoanService().getBookFilterPanel().getTextAreaDetails()
				.setText(SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.author") + "\n"
						+ author.toString() + "\n"
						+ SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.category") + "\n"
						+ category.toString() + "\n"
						+ SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.publishingHouse")
						+ "\n" + publishingHouse.toString());
	}

	private void reloadComboBoxAuthor() {
		DefaultComboBoxModel<Author> comboBoxAuthor = new DefaultComboBoxModel<Author>();
		HibernateDao<Author, Integer> author = new HibernateDao<Author, Integer>(Author.class);
		List<Author> authorListToSort = author.findAll();
		authorListToSort
				.sort(Comparator.comparing(Author::getSurname, Comparator.nullsFirst(Comparator.naturalOrder())));
		authorListToSort.forEach(c -> comboBoxAuthor.addElement(c));
		comboBoxAuthor.insertElementAt(null, 0);

		tabbedForm.getPanelLoanService().getBookFilterPanel().getComboBoxAuthor().setModel(comboBoxAuthor);
	}

	private void reloadComboBoxCategory() {
		DefaultComboBoxModel<Category> comboBoxCategory = new DefaultComboBoxModel<Category>();
		HibernateDao<Category, Integer> category = new HibernateDao<Category, Integer>(Category.class);
		List<Category> categoryListToSort = category.findAll();
		categoryListToSort
				.sort(Comparator.comparing(Category::getName, Comparator.nullsFirst(Comparator.naturalOrder())));
		categoryListToSort.forEach(c -> comboBoxCategory.addElement(c));
		comboBoxCategory.insertElementAt(null, 0);

		tabbedForm.getPanelLoanService().getBookFilterPanel().getComboBoxCategory().setModel(comboBoxCategory);
	}

	private void reloadComboBoxPublishingHouse() {
		DefaultComboBoxModel<PublishingHouse> comboBoxPublishingHouse = new DefaultComboBoxModel<PublishingHouse>();
		HibernateDao<PublishingHouse, Integer> publishingHouse = new HibernateDao<PublishingHouse, Integer>(
				PublishingHouse.class);
		List<PublishingHouse> publishingHouseListToSort = publishingHouse.findAll();
		publishingHouseListToSort
				.sort(Comparator.comparing(PublishingHouse::getName, Comparator.nullsFirst(Comparator.naturalOrder())));
		publishingHouseListToSort.forEach(c -> comboBoxPublishingHouse.addElement(c));
		comboBoxPublishingHouse.insertElementAt(null, 0);

		tabbedForm.getPanelLoanService().getBookFilterPanel().getComboBoxPublishingHouse()
				.setModel(comboBoxPublishingHouse);
	}

	private void reloadComboBoxBook() {
		DefaultComboBoxModel<Book> comboBoxBook = new DefaultComboBoxModel<Book>();
		HibernateDao<Book, Integer> book = new HibernateDao<Book, Integer>(Book.class);
		List<Book> bookListToSort = book.findAll();
		bookListToSort.sort(Comparator.comparing(Book::getTitle, Comparator.nullsFirst(Comparator.naturalOrder())));
		bookListToSort.forEach(c -> comboBoxBook.addElement(c));
		comboBoxBook.insertElementAt(null, 0);

		tabbedForm.getPanelLoanService().getLoanDataPanel().getComboBoxBook().setModel(comboBoxBook);
	}

	private void reloadComboBoxClient() {
		DefaultComboBoxModel<Client> comboBoxClient = new DefaultComboBoxModel<Client>();
		HibernateDao<Client, Integer> client = new HibernateDao<Client, Integer>(Client.class);
		List<Client> clientListToSort = client.findAll();
		List<Client> filteredActiveClientList = clientListToSort.stream().filter(u -> u.getRegistered() == true)
				.collect(Collectors.toList());
		filteredActiveClientList
				.sort(Comparator.comparing(Client::getSurname, Comparator.nullsFirst(Comparator.naturalOrder())));
		filteredActiveClientList.forEach(c -> comboBoxClient.addElement(c));
		comboBoxClient.insertElementAt(null, 0);

		tabbedForm.getPanelLoanService().getLoanDataPanel().getComboBoxClient().setModel(comboBoxClient);
	}

	private void onClickBtnAdd() {
		if (validateIfInventoryNumberNotExist(
				tabbedForm.getPanelLoanService().getLoanDataPanel().getTextFieldInventoryNumber().getText())) {
			try {
				HibernateUtil.beginTransaction();
				Volume volume = new Volume();
				volume.setBook(
						(Book) tabbedForm.getPanelLoanService().getLoanDataPanel().getComboBoxBook().getSelectedItem());
				volume.setInventoryNumber(
						tabbedForm.getPanelLoanService().getLoanDataPanel().getTextFieldInventoryNumber().getText());
				volume.setIsReadingRoom(
						tabbedForm.getPanelLoanService().getLoanDataPanel().getChckbxIsReadingRoom().isSelected() ? true
								: false);
				volume.setIsLended(false);
				HibernateUtil.getSession().save(volume);
				HibernateUtil.commitTransaction();

				VolumeTableModel vtm = (VolumeTableModel) tabbedForm.getPanelLoanService().getVolumeTablePanel()
						.getVolumeTable().getModel();
				vtm.add(volume);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (RollbackException e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(tabbedForm.getFrame(), "Istnieje numer inwentarzowy.", "Numer",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void onClickBtnLoan() {
		try {

			if (HibernateUtil.getSession()
					.createQuery("select idLend from Lend l where l.volume.inventoryNumber = :inventoryNumber")
					.setParameter("inventoryNumber",
							((VolumeTableModel) tabbedForm.getPanelLoanService().getVolumeTablePanel().getVolumeTable()
									.getModel()).getVolume(
											tabbedForm.getPanelLoanService().getVolumeTablePanel().getVolumeTable()
													.convertRowIndexToModel(tabbedForm.getPanelLoanService()
															.getVolumeTablePanel().getVolumeTable().getSelectedRow()))
											.getInventoryNumber())
					.list().isEmpty() &&

					HibernateUtil.getSession().createQuery(
							"select idLend from Lend l where l.client.idUserData = :idUserData and l.volume.penaltyValue != 0")
							.setParameter("idUserData", ((Client) tabbedForm.getPanelLoanService().getLoanDataPanel()
									.getComboBoxClient().getSelectedItem()).getIdUserData())
							.list().isEmpty()

			) {
				HibernateUtil.beginTransaction();
				Lend loan = new Lend();
				Date lendDate = Constans.APPLICATION_DATE_FORMAT
						.parse(Constans.APPLICATION_DATE_FORMAT.format(new Date()));

				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MONTH, 1);
				loan.setClient((Client) tabbedForm.getPanelLoanService().getLoanDataPanel().getComboBoxClient()
						.getSelectedItem());
				loan.setVolume(((VolumeTableModel) tabbedForm.getPanelLoanService().getVolumeTablePanel()
						.getVolumeTable().getModel())
								.getVolume(tabbedForm.getPanelLoanService().getVolumeTablePanel().getVolumeTable()
										.convertRowIndexToModel(tabbedForm.getPanelLoanService().getVolumeTablePanel()
												.getVolumeTable().getSelectedRow())));
				loan.setLendDate(lendDate);
				loan.setReturnedDate(cal.getTime());
				loan.getVolume().setIsLended(true);
				HibernateUtil.getSession().save(loan);
				HibernateUtil.commitTransaction();

				LoanTableModel vtm = (LoanTableModel) tabbedForm.getPanelLoanService().getLoanTablePanel()
						.getLoanTable().getModel();
				vtm.add(loan);

				((VolumeTableModel) tabbedForm.getPanelLoanService().getVolumeTablePanel().getVolumeTable().getModel())
						.remove(loan.getVolume());
				((VolumeTableModel) tabbedForm.getPanelLoanService().getVolumeTablePanel().getVolumeTable().getModel())
						.fireTableDataChanged();
			} else {
				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.loanError"),
						SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.loanErrorTitle"),
						JOptionPane.ERROR_MESSAGE);

			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (RollbackException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	// private void onClickBtnReturn() {
	// Lend currentLoan = ((LoanTableModel)
	// tabbedForm.getPanelLoanService().getLoanTablePanel().getLoanTable()
	// .getModel()).getLend(
	// tabbedForm.getPanelLoanService().getLoanTablePanel().getLoanTable().convertRowIndexToModel(
	// tabbedForm.getPanelLoanService().getLoanTablePanel().getLoanTable().getSelectedRow()));
	// currentLoan.getVolume().setIsLended(false);
	// if (calculatePenalty() != new BigDecimal(0).doubleValue()) {
	// JOptionPane.showMessageDialog(tabbedForm.getFrame(),
	// SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.penaltyError")
	// + " "
	// + calculatePenalty() + Constans.APPLICATION_CURRENCY,
	// SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.penaltyErrorTitle"),
	// JOptionPane.ERROR_MESSAGE);
	// }
	//
	// try {
	// HibernateUtil.beginTransaction();
	//
	// ((VolumeTableModel)
	// tabbedForm.getPanelLoanService().getVolumeTablePanel().getVolumeTable().getModel())
	// .add(currentLoan.getVolume());
	// ((VolumeTableModel)
	// tabbedForm.getPanelLoanService().getVolumeTablePanel().getVolumeTable().getModel())
	// .fireTableDataChanged();
	//
	// HibernateUtil.getSession().delete(Lend.class.getName(),
	// ((LoanTableModel)
	// tabbedForm.getPanelLoanService().getLoanTablePanel().getLoanTable().getModel())
	// .getLend(tabbedForm.getPanelLoanService().getLoanTablePanel().getLoanTable()
	// .convertRowIndexToModel(tabbedForm.getPanelLoanService().getLoanTablePanel()
	// .getLoanTable().getSelectedRow())));
	// ((LoanTableModel)
	// tabbedForm.getPanelLoanService().getLoanTablePanel().getLoanTable().getModel())
	// .delete(currentLoan);
	//
	// HibernateUtil.commitTransaction();
	// } catch (Exception e) {
	// }
	// }

	@SuppressWarnings("deprecation")
	private void onClickBtnProlongation() {
		final JTable jTable = tabbedForm.getLoanServicePanel_new().getLoanTable();
		if (jTable.convertRowIndexToModel(jTable.getSelectedRow()) != -1) {
			Lend lend = ((LoanTableModel) jTable.getModel())
					.getLend(jTable.convertRowIndexToModel(jTable.getSelectedRow()));

			Date lendData = lend.getLendDate();
			Date returnData = lend.getReturnedDate();

			Calendar cal = Calendar.getInstance();
			cal.setTime(lendData);

			Date deadLine = new Date();
			deadLine.setMonth(lendData.getMonth());
			deadLine.setYear(lendData.getYear());
			deadLine.setDate(lendData.getDate());
			if (lendData.getMonth() == 9) {
				deadLine.setMonth(0);
				deadLine.setYear(deadLine.getYear() + 1);
			} else if (lendData.getMonth() == 10) {
				deadLine.setMonth(1);
				deadLine.setYear(deadLine.getYear() + 1);
			} else if (lendData.getMonth() == 11) {
				deadLine.setMonth(2);
				deadLine.setYear(deadLine.getYear() + 1);
			} else
				deadLine.setMonth(deadLine.getMonth() + 3);

			Boolean error = false;

			if (returnData.getMonth() == 11) {
				returnData.setMonth(0);
				returnData.setYear(returnData.getYear() + 1);
				if (returnData.after(deadLine))
					error = true;
			} else {
				returnData.setMonth(returnData.getMonth() + 1);
				if (returnData.after(deadLine))
					error = true;
			}

			if (!error) {
				lend.setReturnedDate(returnData);

				HibernateUtil.beginTransaction();
				HibernateUtil.getSession().update(Lend.class.getName(), lend);
				HibernateUtil.commitTransaction();

				((LoanTableModel) tabbedForm.getLoanServicePanel_new().getLoanTable().getModel())
						.setLend(tabbedForm.getLoanServicePanel_new().getLoanTable().getSelectedRow(), lend);
				((LoanTableModel) tabbedForm.getLoanServicePanel_new().getLoanTable().getModel()).reloadData();
			} else {
				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle()
								.getString("loanServicePanel.loanLimitExpiredError"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("loanServicePanel.loanLimitExpiredErrorTitle"),
						JOptionPane.ERROR_MESSAGE);
			}

		} else {
			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle()
							.getString("loanServicePanel.notSelectedLoanError"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("loanServicePanel.notSelectedLoanErrorTitle"),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private double calculatePenalty() {
		int diffMonth = 0;
		if (tabbedForm.getLoanServicePanel_new().getLoanTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getLoanServicePanel_new().getLoanTable()
					.convertRowIndexToModel(tabbedForm.getLoanServicePanel_new().getLoanTable().getSelectedRow());

			if (selectedRowIndex != -1) {
				Lend selectedLend = ((LoanTableModel) tabbedForm.getLoanServicePanel_new().getLoanTable().getModel())
						.getLend(selectedRowIndex);

				Date returnDate = selectedLend.getReturnedDate();

				if (new Date().after(returnDate)) {
					Calendar startCalendar = new GregorianCalendar();
					startCalendar.setTime(returnDate);
					Calendar endCalendar = new GregorianCalendar();
					endCalendar.setTime(new Date());

					int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
					diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
				}
				System.out.println(diffMonth * Constans.PENALTY_VALUE);
			}
		}
		return diffMonth * Constans.PENALTY_VALUE;
	}

	private void setVisibleControls() {
		tabbedForm.getPanelLoanService().getBookFilterPanel().getAuthorAddMultiPanel().setVisible(false);
		tabbedForm.getPanelLoanService().getBookFilterPanel().getCategoryAddMultiPanel().setVisible(false);
		tabbedForm.getPanelLoanService().getBookFilterPanel().getPublishingHouseAddMultiPanel().setVisible(false);
		tabbedForm.getPanelLoanService().getBookFilterPanel().getAdmBookDataPanel().setVisible(false);
		tabbedForm.getPanelLoanService().getBookFilterPanel().getComboBoxAuthor().setVisible(false);
		tabbedForm.getPanelLoanService().getBookFilterPanel().getComboBoxCategory().setVisible(false);
		tabbedForm.getPanelLoanService().getBookFilterPanel().getComboBoxPublishingHouse().setVisible(false);
		tabbedForm.getPanelLoanService().getBookFilterPanel().getLblAuthor().setVisible(false);
		tabbedForm.getPanelLoanService().getBookFilterPanel().getLblCategory().setVisible(false);
		tabbedForm.getPanelLoanService().getBookFilterPanel().getLblPublishingHouse().setVisible(false);
		tabbedForm.getPanelLoanService().getBookFilterPanel().getDecisionPanel().getBtnAccept().setVisible(false);

		if (LogInEvent.getRole() == Role.WORKER_ACCOUNTANT || LogInEvent.getRole() == Role.ADMIN) {
			tabbedForm.getPanelLoanService().getLoanDataPanel().getTextFieldInventoryNumber().setVisible(true);
			tabbedForm.getPanelLoanService().getLoanDataPanel().getLblInventoryNumber().setVisible(true);
			tabbedForm.getPanelLoanService().getLoanDataPanel().getChckbxIsReadingRoom().setVisible(true);
			tabbedForm.getPanelLoanService().getLoanDataPanel().getCockpitEditionPanel().setVisible(true);
		} else {
			tabbedForm.getPanelLoanService().getLoanDataPanel().getTextFieldInventoryNumber().setVisible(false);
			tabbedForm.getPanelLoanService().getLoanDataPanel().getLblInventoryNumber().setVisible(false);
			tabbedForm.getPanelLoanService().getLoanDataPanel().getChckbxIsReadingRoom().setVisible(false);
			tabbedForm.getPanelLoanService().getLoanDataPanel().getCockpitEditionPanel().setVisible(false);
		}
	}

	private boolean validateIfInventoryNumberNotExist(String inventoryNumber) {
		boolean result = false;
		Volume volume = (Volume) HibernateUtil.getSession().getNamedQuery("Volume.checkIfInventoryNumberExist")
				.setParameter("inventoryNumber", inventoryNumber).uniqueResult();

		if (volume == null) {
			result = true;
		}

		return result;
	}

	private void prepareReservation() throws ParseException {
		int selectedClientRow = tabbedForm.getLoanServicePanel_new().getClientTable().getSelectedRow();
		int selectedVolumeRow = tabbedForm.getLoanServicePanel_new().getVolumeLoanTable().getSelectedRow();

		if (selectedClientRow != -1 && selectedVolumeRow != -1) {

			final Client selectedClient = ((ClientTableModel) tabbedForm.getLoanServicePanel_new().getClientTable()
					.getModel()).getClient(selectedClientRow);
			final Volume selectedVolume = ((VolumeTableModel) tabbedForm.getLoanServicePanel_new().getVolumeLoanTable()
					.getModel()).getVolume(selectedVolumeRow);

			HibernateUtil.beginTransaction();
			final Date lendDate = Constans.APPLICATION_DATE_FORMAT
					.parse(Constans.APPLICATION_DATE_FORMAT.format(new Date()));
			final Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);

			final Lend loan = new Lend();
			loan.setClient(selectedClient);
			loan.setVolume(selectedVolume);
			loan.setLendDate(lendDate);
			loan.setReturnedDate(cal.getTime());
			loan.getVolume().setIsLended(true);
			HibernateUtil.getSession().save(loan);
			HibernateUtil.commitTransaction();

			changeStatusVolumeISLended(selectedVolume);

			final LoanTableModel vtm = (LoanTableModel) tabbedForm.getLoanServicePanel_new().getLoanTable().getModel();
			vtm.add(loan);

			((VolumeTableModel) tabbedForm.getLoanServicePanel_new().getVolumeLoanTable().getModel())
					.remove(loan.getVolume());
			((VolumeTableModel) tabbedForm.getLoanServicePanel_new().getVolumeLoanTable().getModel())
					.fireTableDataChanged();

			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle().getString("tabLoanServiceEvent.loanSuccess"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanServiceEvent.loanSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle()
							.getString("loanServicePanel.loanLackClientOrVolume"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("loanServicePanel.loanLackClientOrVolumeTitle"),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void changeStatusVolumeISLended(final Volume volume) {
		HibernateUtil.beginTransaction();
		volume.setIsLended(true);
		HibernateUtil.getSession().save(volume);
		HibernateUtil.commitTransaction();
	}

	private void changeStatusVolumeISReturned(final Volume volume) {
		HibernateUtil.beginTransaction();
		volume.setIsLended(false);
		HibernateUtil.getSession().save(volume);
		HibernateUtil.commitTransaction();
	}

	private void onClickBtnProlongation2() {
		final Lend lend = getLendClicked();
		final Integer prolognate = lend.getProlognate();
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);

		if (prolognate < Constans.MAX_PROLONGNATION) {
			lend.setProlognate(prolognate + 1);
			lend.setReturnedDate(getNewDate(lend.getReturnedDate(), cal.getTime()));
			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().save(lend);
			HibernateUtil.commitTransaction();
			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.loanSuccess"),
					SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.loanSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle()
							.getString("loanServicePanel.loanLimitExpiredError"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("loanServicePanel.loanLimitExpiredErrorTitle"),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private Date getNewDate(final Date d1, final Date d2) {
		return new Date(d1.getTime() + d2.getTime());
	}

	private void onClickBtnReturn() {
		final Lend lend = getLendClicked();

		if (calculatePenalty() != new BigDecimal(0).doubleValue()) {
			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.penaltyError") + " "
							+ calculatePenalty() + Constans.APPLICATION_CURRENCY,
					SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.penaltyErrorTitle"),
					JOptionPane.ERROR_MESSAGE);

		} else if (Objects.isNull(lend.getReservationClient())) {
			lend.setIsReturned(true);
			changeStatusVolumeISReturned(lend.getVolume());
			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().delete(lend);
			HibernateUtil.commitTransaction();

			// ((VolumeTableModel)
			// tabbedForm.getLoanServicePanel_new().getVolumeLoanTable().getModel())
			// .add(lend.getVolume());
			((LoanTableModel) tabbedForm.getLoanServicePanel_new().getLoanTable().getModel()).delete(lend);
			((LoanTableModel) tabbedForm.getLoanServicePanel_new().getLoanTable().getModel()).fireTableDataChanged();
			((VolumeTableModel) tabbedForm.getLoanServicePanel_new().getVolumeLoanTable().getModel()).reloadData();

			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanServiceEvent.loanReturnSuccess"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanServiceEvent.loanReturnSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			lend.setClient(lend.getReservationClient());
			lend.setReservationClient(null);
			lend.getVolume().setIsReserve(false);
			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().save(lend);
			HibernateUtil.commitTransaction();

			((LoanTableModel) tabbedForm.getLoanServicePanel_new().getLoanTable().getModel()).reloadData();
			((LoanReservationTableModel) tabbedForm.getLoanServicePanel_new().getReservationTable().getModel())
					.reloadData();

			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanServiceEvent.loanReservationRealizationSuccess") + " ["
							+ lend.getClient().getName() + " " + lend.getClient().getSurname() + "]",
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanServiceEvent.loanReservationRealizationSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private void onClickBtnPenalty() {
		final Lend lend = getLendClicked();

		if (calculatePenalty() != new BigDecimal(0).doubleValue()) {

			// lend.getVolume().setPenaltyValue(new BigDecimal(0));
			// lend.setIsReturned(true);
			changeStatusVolumeISReturned(lend.getVolume());
			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().delete(lend);
			HibernateUtil.commitTransaction();

			((VolumeTableModel) tabbedForm.getLoanServicePanel_new().getVolumeLoanTable().getModel())
					.add(lend.getVolume());
			((LoanTableModel) tabbedForm.getLoanServicePanel_new().getLoanTable().getModel()).delete(lend);
			((LoanTableModel) tabbedForm.getLoanServicePanel_new().getLoanTable().getModel()).reloadData();
			((VolumeTableModel) tabbedForm.getLoanServicePanel_new().getVolumeLoanTable().getModel()).reloadData();
			JOptionPane.showMessageDialog(tabbedForm.getFrame(), "Kara zosta³a sp³acona", "Sp³acona",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.loanError"),
					SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.loanErrorTitle"),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private Lend getLendClicked() {
		final JTable jTable = tabbedForm.getLoanServicePanel_new().getLoanTable();
		final Lend lend = ((LoanTableModel) jTable.getModel())
				.getLend(jTable.convertRowIndexToModel(jTable.getSelectedRow()));

		return lend;
	}

	private Client getClientClicked() {
		int selectedClientRow = tabbedForm.getLoanServicePanel_new().getClientTable().getSelectedRow();
		final Client selectedClient = ((ClientTableModel) tabbedForm.getLoanServicePanel_new().getClientTable()
				.getModel()).getClient(selectedClientRow);

		return selectedClient;
	}

	private void onClickBtnReservation() {
		if (tabbedForm.getLoanServicePanel_new().getClientTable().getSelectedRow() != -1
				&& tabbedForm.getLoanServicePanel_new().getLoanTable().getSelectedRow() != -1) {
			int selectedClientRowIndex = tabbedForm.getLoanServicePanel_new().getClientTable()
					.convertRowIndexToModel(tabbedForm.getLoanServicePanel_new().getClientTable().getSelectedRow());
			int selectedLoanRowIndex = tabbedForm.getLoanServicePanel_new().getLoanTable()
					.convertRowIndexToModel(tabbedForm.getLoanServicePanel_new().getLoanTable().getSelectedRow());
			if (selectedClientRowIndex != -1 && selectedLoanRowIndex != -1) {
				Client selectedClient = ((ClientTableModel) tabbedForm.getLoanServicePanel_new().getClientTable()
						.getModel()).getClient(selectedClientRowIndex);
				Client clientShallowClone = (Client) selectedClient.clone();
				Lend selectedLoan = ((LoanTableModel) tabbedForm.getLoanServicePanel_new().getLoanTable().getModel())
						.getLend(selectedLoanRowIndex);
				Lend lendShallowClone = (Lend) selectedLoan.clone();

				 if (!selectedClient.equals(selectedLoan.getClient())) {

				if (!selectedLoan.getVolume().getIsReserve()) {
					lendShallowClone.getVolume().setIsReserve(true);
					lendShallowClone.setReservationClient(clientShallowClone);

					HibernateUtil.beginTransaction();
					HibernateUtil.getSession()
							.evict(((ClientTableModel) tabbedForm.getLoanServicePanel_new().getClientTable().getModel())
									.getClient(selectedClientRowIndex));
					HibernateUtil.getSession()
							.evict(((LoanTableModel) tabbedForm.getLoanServicePanel_new().getLoanTable().getModel())
									.getLend(selectedLoanRowIndex));
					HibernateUtil.getSession().update(Lend.class.getName(), lendShallowClone);
					HibernateUtil.commitTransaction();

					((LoanTableModel) tabbedForm.getLoanServicePanel_new().getLoanTable().getModel())
							.setLend(selectedLoanRowIndex, lendShallowClone);
					((LoanTableModel) tabbedForm.getLoanServicePanel_new().getLoanTable().getModel())
							.fireTableDataChanged();
					((LoanReservationTableModel) tabbedForm.getLoanServicePanel_new().getReservationTable().getModel())
							.reloadData();

					Utils.displayOptionPane(
							SystemProperties.getInstance().getResourceBundle()
									.getString("tabLoanServiceEvent.reservationSuccess"),
							SystemProperties.getInstance().getResourceBundle().getString(
									"tabLoanServiceEvent.reservationSuccessTitle"),
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(tabbedForm.getFrame(),
							SystemProperties.getInstance().getResourceBundle()
									.getString("tabLoanServiceEvent.reservationError"),
							SystemProperties.getInstance().getResourceBundle()
									.getString("tabLoanServiceEvent.reservationErrorTitle"),
							JOptionPane.ERROR_MESSAGE);
				}
				
			} else {
				JOptionPane.showMessageDialog(tabbedForm.getFrame(), SystemProperties.getInstance().getResourceBundle()
						.getString("tabLoanServiceEvent.reservationError1"), SystemProperties.getInstance().getResourceBundle()
						.getString("tabLoanServiceEvent.reservationErrorTitle"),
						JOptionPane.ERROR_MESSAGE);
			}
				
			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarning"));
		}

		// if (selectedRowIndex != -1) {
		//
		// int selectedVolumeRow =
		// tabbedForm.getLoanServicePanel_new().getVolumeLoanTable().getSelectedRow();
		// final Volume volume = ((VolumeTableModel)
		// tabbedForm.getLoanServicePanel_new().getVolumeLoanTable()
		// .getModel()).getVolume(selectedVolumeRow);
		// final Client client = getClientClicked();
		//
		// final Lend lend = new Lend();
		// lend.setClient(client);
		//
		// volume.setIsReserve(true);
		// HibernateUtil.beginTransaction();
		// HibernateUtil.getSession().save(volume);
		// HibernateUtil.commitTransaction();
		// ((LoanReservationTableModel)
		// tabbedForm.getLoanServicePanel_new().getReservationTable().getModel())
		// .add(lend);
		// ((LoanReservationTableModel)
		// tabbedForm.getLoanServicePanel_new().getReservationTable().getModel()).fireTableDataChanged();

	}

	private void reservationQueue() {
		final Lend lend = getLendClicked();
		final Client client = getClientClicked();

		if (Objects.isNull(lend.getQueueClient())) {
			lend.setQueueClient(client.getIdUserData());
			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().save(lend);
			HibernateUtil.commitTransaction();
			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.loanReReservationOn")
							+ getNameAndSurnameCient(client),
					SystemProperties.getInstance().getResourceBundle()
							.getString("loanServicePanel.loanReReservationOnTitle"),
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(tabbedForm.getFrame(), "Ksi¹¿ka jest ju¿ zerezerwowana", "B³¹d",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private String getNameAndSurnameCient(final Client client) {
		return client.getName() + " " + client.getSurname();
	}
}
