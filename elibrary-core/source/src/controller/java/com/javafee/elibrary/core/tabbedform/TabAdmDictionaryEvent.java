package com.javafee.elibrary.core.tabbedform;

import java.util.Comparator;
import java.util.List;

import javax.persistence.RollbackException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import org.hibernate.query.NativeQuery;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.IActionForm;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.exception.RefusedAdmDictionaryEventLoadingException;
import com.javafee.elibrary.core.tabbedform.admdictionaries.AdmDictionaryPanel;
import com.javafee.elibrary.hibernate.dao.HibernateDao;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.library.Author;
import com.javafee.elibrary.hibernate.dto.library.Book;
import com.javafee.elibrary.hibernate.dto.library.Category;
import com.javafee.elibrary.hibernate.dto.library.PublishingHouse;

import lombok.Setter;

public class TabAdmDictionaryEvent implements IActionForm {
	@Setter
	private TabbedForm tabbedForm;

	private String pressedRadioButton = Constants.RADIO_BUTTON_AUTHOR;

	protected static TabAdmDictionaryEvent admDictionaryEvent = null;

	public TabAdmDictionaryEvent(TabbedForm tabbedForm) {
		this.control(tabbedForm);
	}

	public static TabAdmDictionaryEvent getInstance(TabbedForm tabbedForm) {
		if (admDictionaryEvent == null) {
			admDictionaryEvent = new TabAdmDictionaryEvent(tabbedForm);
		} else
			new RefusedAdmDictionaryEventLoadingException("Cannot adm dictionary event loading");
		return admDictionaryEvent;
	}

	public void control(TabbedForm tabbedForm) {
		setTabbedForm(tabbedForm);
		initializeForm();

		fillDictionaryData(Constants.RADIO_BUTTON_AUTHOR);
		fillDictionaryData(Constants.RADIO_BUTTON_CATEGORY);
		fillDictionaryData(Constants.RADIO_BUTTON_PUBLISHING_HOUSE);

		tabbedForm.getPanelAdmDictionary().getRadioButtonAuthor()
				.addActionListener(e -> switchRadioButtonsEnable(Constants.RADIO_BUTTON_AUTHOR));
		tabbedForm.getPanelAdmDictionary().getRadioButtonCategory()
				.addActionListener(e -> switchRadioButtonsEnable(Constants.RADIO_BUTTON_CATEGORY));
		tabbedForm.getPanelAdmDictionary().getRadioButtonPublishingHouse()
				.addActionListener(e -> switchRadioButtonsEnable(Constants.RADIO_BUTTON_PUBLISHING_HOUSE));
		tabbedForm.getPanelAdmDictionary().getComboBoxAuthor().addActionListener(e -> onChangeComboBoxAuthor());
		tabbedForm.getPanelAdmDictionary().getComboBoxCategory().addActionListener(e -> onChangeComboBoxCategory());
		tabbedForm.getPanelAdmDictionary().getComboBoxPublishingHouse()
				.addActionListener(e -> onChangeComboBoxPublishingHouse());
		tabbedForm.getPanelAdmDictionary().getCockpitEditionPanel().getBtnAdd()
				.addActionListener(e -> onClickBtnAdd(pressedRadioButton));
		tabbedForm.getPanelAdmDictionary().getCockpitEditionPanel().getBtnModify()
				.addActionListener(e -> onClickBtnModify(pressedRadioButton));
		tabbedForm.getPanelAdmDictionary().getCockpitEditionPanel().getBtnDelete()
				.addActionListener(e -> onClickBtnDelete(pressedRadioButton));
	}

	@Override
	public void initializeForm() {
		reloadComboBoxes();
	}

	private void reloadComboBoxes() {
		reloadComboBoxAuthor();
		reloadComboBoxCategory();
		reloadComboBoxPublishingHouse();
	}

	private void reloadComboBoxAuthor() {
		DefaultComboBoxModel<Author> comboBoxAuthorModel = new DefaultComboBoxModel<Author>();
		HibernateDao<Author, Integer> author = new HibernateDao<Author, Integer>(Author.class);
		List<Author> authorListToSort = author.findAll();
		authorListToSort
				.sort(Comparator.comparing(Author::getSurname, Comparator.nullsFirst(Comparator.naturalOrder())));
		authorListToSort.forEach(c -> comboBoxAuthorModel.addElement(c));
		comboBoxAuthorModel.insertElementAt(null, 0);

		tabbedForm.getPanelAdmDictionary().getComboBoxAuthor().setModel(comboBoxAuthorModel);
	}

	private void reloadComboBoxCategory() {
		DefaultComboBoxModel<Category> comboBoxCategoryModel = new DefaultComboBoxModel<Category>();
		HibernateDao<Category, Integer> category = new HibernateDao<Category, Integer>(Category.class);
		List<Category> categoryListToSort = category.findAll();
		categoryListToSort
				.sort(Comparator.comparing(Category::getName, Comparator.nullsFirst(Comparator.naturalOrder())));
		categoryListToSort.forEach(c -> comboBoxCategoryModel.addElement(c));
		comboBoxCategoryModel.insertElementAt(null, 0);

		tabbedForm.getPanelAdmDictionary().getComboBoxCategory().setModel(comboBoxCategoryModel);
	}

	private void reloadComboBoxPublishingHouse() {
		DefaultComboBoxModel<PublishingHouse> comboBoxPublishingHouseModel = new DefaultComboBoxModel<PublishingHouse>();
		HibernateDao<PublishingHouse, Integer> publishingHouse = new HibernateDao<PublishingHouse, Integer>(
				PublishingHouse.class);
		List<PublishingHouse> publishingHouseListToSort = publishingHouse.findAll();
		publishingHouseListToSort
				.sort(Comparator.comparing(PublishingHouse::getName, Comparator.nullsFirst(Comparator.naturalOrder())));
		publishingHouseListToSort.forEach(c -> comboBoxPublishingHouseModel.addElement(c));
		comboBoxPublishingHouseModel.insertElementAt(null, 0);

		tabbedForm.getPanelAdmDictionary().getComboBoxPublishingHouse().setModel(comboBoxPublishingHouseModel);
	}

	private void onClickBtnAdd(String pressedRadioButton) {
		final AdmDictionaryPanel admDictionaryPanel = tabbedForm.getPanelAdmDictionary();
		Object resultObjectToSave = null;
		try {
			HibernateUtil.beginTransaction();
			switch (pressedRadioButton) {
				case Constants.RADIO_BUTTON_AUTHOR:
					Author author = new Author();
					author.setName(admDictionaryPanel.getTextFieldAuthorName().getText());
					author.setNickname(admDictionaryPanel.getTextFieldAuthorNickname().getText());
					author.setSurname(admDictionaryPanel.getTextFieldAuthorSurname().getText());
					author.setBirthDate(admDictionaryPanel.getDateChooserBirthDate().getDate());
					resultObjectToSave = author;
					reloadComboBoxAuthor();
					break;
				case Constants.RADIO_BUTTON_CATEGORY:
					final Category category = new Category();
					category.setName(admDictionaryPanel.getTextFieldCategoryName().getText() != null
							? tabbedForm.getPanelAdmDictionary().getTextFieldCategoryName().getText()
							: null);
					resultObjectToSave = category;
					reloadComboBoxCategory();
					break;
				case Constants.RADIO_BUTTON_PUBLISHING_HOUSE:
					final PublishingHouse publishingHouse = new PublishingHouse();
					publishingHouse.setName(admDictionaryPanel.getTextFieldPublishingHouseName().getText());
					resultObjectToSave = publishingHouse;
					reloadComboBoxPublishingHouse();
					break;
			}
			HibernateUtil.getSession().save(resultObjectToSave);
			HibernateUtil.commitTransaction();
			reloadComboBoxes();
			JOptionPane.showMessageDialog(admDictionaryPanel,
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabDictionaryEvent.additionDictionaryElementSuccess"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabDictionaryEvent.additionDictionaryElementSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
		} catch (NumberFormatException | IllegalStateException | RollbackException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void onClickBtnModify(String pressedRadioButton) {
		final AdmDictionaryPanel admDictionaryPanel = tabbedForm.getPanelAdmDictionary();
		try {
			switch (pressedRadioButton) {
				case Constants.RADIO_BUTTON_AUTHOR:
					final Author authorClicked = ((Author) admDictionaryPanel.getComboBoxAuthor().getSelectedItem());
					final NativeQuery<Author> authorQuery = HibernateUtil.getSession()
							.createNativeQuery("select * from lib_book_author where id_author = :idAuthor")
							.addEntity(Book.class).setParameter("idAuthor", authorClicked.getIdAuthor());
					final List<Author> resultAuthor = authorQuery.list();

					if (resultAuthor.isEmpty()) {
						HibernateUtil.beginTransaction();
						authorClicked.setIdAuthor(authorClicked.getIdAuthor());
						authorClicked.setName(admDictionaryPanel.getTextFieldAuthorName().getText());
						authorClicked.setNickname(admDictionaryPanel.getTextFieldAuthorNickname().getText());
						authorClicked.setSurname(admDictionaryPanel.getTextFieldAuthorSurname().getText());
						authorClicked.setBirthDate(admDictionaryPanel.getDateChooserBirthDate().getDate());
						HibernateUtil.getSession().update(authorClicked);
						HibernateUtil.commitTransaction();
						reloadComboBoxAuthor();
						JOptionPane.showMessageDialog(admDictionaryPanel,
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabDictionaryEvent.modifyDictionaryElementSuccess"),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabDictionaryEvent.modifyDictionaryElementSuccessTitle"),
								JOptionPane.INFORMATION_MESSAGE);
					} else
						LogGuiException.logError(
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabDictionaryEvent.modifyDictionaryElementWarningTitle"),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabDictionaryEvent.modifyDictionaryElementWarning"));
					fillDictionaryData(Constants.RADIO_BUTTON_AUTHOR);
					break;
				case Constants.RADIO_BUTTON_CATEGORY:
					final Category categoryClicked = ((Category) admDictionaryPanel.getComboBoxCategory()
							.getSelectedItem());
					final NativeQuery<Category> categoryQuery = HibernateUtil.getSession()
							.createNativeQuery("select * from lib_book_category where id_category = :idCategory")
							.setParameter("idCategory", categoryClicked.getIdCategory());
					final List<Category> resultCategory = categoryQuery.list();

					if (resultCategory.isEmpty()) {
						HibernateUtil.beginTransaction();
						categoryClicked.setIdCategory(categoryClicked.getIdCategory());
						categoryClicked.setName(admDictionaryPanel.getTextFieldCategoryName().getText() != null
								? tabbedForm.getPanelAdmDictionary().getTextFieldCategoryName().getText()
								: null);
						HibernateUtil.getSession().update(categoryClicked);
						HibernateUtil.commitTransaction();
						reloadComboBoxCategory();
						JOptionPane.showMessageDialog(admDictionaryPanel,
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabDictionaryEvent.modifyDictionaryElementSuccess"),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabDictionaryEvent.modifyDictionaryElementSuccessTitle"),
								JOptionPane.INFORMATION_MESSAGE);
					} else
						LogGuiException.logError(
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabDictionaryEvent.modifyDictionaryElementWarningTitle"),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabDictionaryEvent.modifyDictionaryElementWarning"));
					fillDictionaryData(Constants.RADIO_BUTTON_CATEGORY);
					break;
				case Constants.RADIO_BUTTON_PUBLISHING_HOUSE:
					final PublishingHouse publishingClicked = ((PublishingHouse) admDictionaryPanel
							.getComboBoxPublishingHouse().getSelectedItem());
					final NativeQuery<PublishingHouse> publishingHouseQuery = HibernateUtil.getSession()
							.createNativeQuery("select * from lib_book_publishing_house where id_publishing_house = :idP")
							.setParameter("idP", publishingClicked.getIdPublishingHouse());
					final List<PublishingHouse> resultPuBlishinHouse = publishingHouseQuery.list();

					if (resultPuBlishinHouse.isEmpty()) {
						HibernateUtil.beginTransaction();
						publishingClicked.setIdPublishingHouse(publishingClicked.getIdPublishingHouse());
						publishingClicked.setName(admDictionaryPanel.getTextFieldPublishingHouseName().getText());
						HibernateUtil.getSession().update(publishingClicked);
						HibernateUtil.commitTransaction();
						reloadComboBoxPublishingHouse();
						JOptionPane.showMessageDialog(admDictionaryPanel,
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabDictionaryEvent.modifyDictionaryElementSuccess"),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabDictionaryEvent.modifyDictionaryElementSuccessTitle"),
								JOptionPane.INFORMATION_MESSAGE);
					} else
						LogGuiException.logError(
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabDictionaryEvent.modifyDictionaryElementWarningTitle"),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabDictionaryEvent.modifyDictionaryElementWarning"));
					fillDictionaryData(Constants.RADIO_BUTTON_PUBLISHING_HOUSE);
					break;
			}
		} catch (NumberFormatException | IllegalStateException | RollbackException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void onClickBtnDelete(String pressedRadioButton) {
		final AdmDictionaryPanel admDictionaryPanel = tabbedForm.getPanelAdmDictionary();

		try {
			switch (pressedRadioButton) {
				case Constants.RADIO_BUTTON_AUTHOR:
					if (Utils.displayConfirmDialog(
							SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
							"") == JOptionPane.YES_OPTION) {
						final Author author = ((Author) admDictionaryPanel.getComboBoxAuthor().getSelectedItem());

						final NativeQuery<Author> authorQuery = HibernateUtil.getSession()
								.createNativeQuery("select * from lib_book_author where id_author = :idAuthor")
								.addEntity(Book.class).setParameter("idAuthor", author.getIdAuthor());
						final List<Author> resultAuthor = authorQuery.list();

						if (resultAuthor.isEmpty()) {
							admDictionaryPanel.getComboBoxAuthor().removeItem(author);

							HibernateUtil.beginTransaction();
							HibernateUtil.getSession().delete(author);
							HibernateUtil.commitTransaction();
							reloadComboBoxAuthor();
							fillDictionaryData(Constants.RADIO_BUTTON_AUTHOR);
							JOptionPane.showMessageDialog(tabbedForm.getFrame(),
									SystemProperties.getInstance().getResourceBundle()
											.getString("tabDictionaryEvent.deleteDictionaryElementSuccess"),
									SystemProperties.getInstance().getResourceBundle()
											.getString("tabDictionaryEvent.deleteDictionaryElementSuccessTitle"),
									JOptionPane.INFORMATION_MESSAGE);
						} else
							LogGuiException.logError(
									SystemProperties.getInstance().getResourceBundle()
											.getString("tabDictionaryEvent.deleteDictionaryElementWarning"),
									SystemProperties.getInstance().getResourceBundle()
											.getString("tabDictionaryEvent.deleteDictionaryElementWarningTitle"));
					}
					break;
				case Constants.RADIO_BUTTON_CATEGORY:
					if (Utils.displayConfirmDialog(
							SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
							"") == JOptionPane.YES_OPTION) {
						final Category category = ((Category) admDictionaryPanel.getComboBoxCategory().getSelectedItem());
						final NativeQuery<Category> categoryQuery = HibernateUtil.getSession()
								.createNativeQuery("select * from lib_book_category where id_category = :idCategory")
								.setParameter("idCategory", category.getIdCategory());
						final List<Category> resultCategory = categoryQuery.list();

						if (resultCategory.isEmpty()) {
							admDictionaryPanel.getComboBoxCategory().removeItem(category);

							HibernateUtil.beginTransaction();
							HibernateUtil.getSession().delete(category);
							HibernateUtil.commitTransaction();
							reloadComboBoxCategory();
							fillDictionaryData(Constants.RADIO_BUTTON_CATEGORY);
							JOptionPane.showMessageDialog(tabbedForm.getFrame(),
									SystemProperties.getInstance().getResourceBundle()
											.getString("tabDictionaryEvent.deleteDictionaryElementSuccess"),
									SystemProperties.getInstance().getResourceBundle()
											.getString("tabDictionaryEvent.deleteDictionaryElementSuccessTitle"),
									JOptionPane.INFORMATION_MESSAGE);
						} else
							JOptionPane.showMessageDialog(tabbedForm.getFrame(),
									SystemProperties.getInstance().getResourceBundle()
											.getString("tabDictionaryEvent.deleteDictionaryElementWarning"),
									SystemProperties.getInstance().getResourceBundle()
											.getString("tabDictionaryEvent.deleteDictionaryElementWarningTitle"),
									JOptionPane.ERROR_MESSAGE);
					}
					break;
				case Constants.RADIO_BUTTON_PUBLISHING_HOUSE:
					if (Utils.displayConfirmDialog(
							SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
							"") == JOptionPane.YES_OPTION) {
						final PublishingHouse publishingHouse = ((PublishingHouse) admDictionaryPanel
								.getComboBoxPublishingHouse().getSelectedItem());
						final NativeQuery<PublishingHouse> publishingHouseQuery = HibernateUtil.getSession()
								.createNativeQuery(
										"select * from lib_book_publishing_house where id_publishing_house = :idP")
								.setParameter("idP", publishingHouse.getIdPublishingHouse());
						final List<PublishingHouse> resultPuBlishinHouse = publishingHouseQuery.list();

						if (resultPuBlishinHouse.isEmpty()) {
							HibernateUtil.beginTransaction();

							HibernateUtil.getSession().delete(publishingHouse);
							HibernateUtil.commitTransaction();
							reloadComboBoxPublishingHouse();
							fillDictionaryData(Constants.RADIO_BUTTON_PUBLISHING_HOUSE);
							JOptionPane.showMessageDialog(tabbedForm.getFrame(),
									SystemProperties.getInstance().getResourceBundle()
											.getString("tabDictionaryEvent.deleteDictionaryElementSuccess"),
									SystemProperties.getInstance().getResourceBundle()
											.getString("tabDictionaryEvent.deleteDictionaryElementSuccessTitle"),
									JOptionPane.INFORMATION_MESSAGE);

						} else
							JOptionPane.showMessageDialog(tabbedForm.getFrame(),
									SystemProperties.getInstance().getResourceBundle()
											.getString("tabDictionaryEvent.deleteDictionaryElementWarning"),
									SystemProperties.getInstance().getResourceBundle()
											.getString("tabDictionaryEvent.deleteDictionaryElementWarningTitle"),
									JOptionPane.ERROR_MESSAGE);
					}
					break;
			}
		} catch (NumberFormatException | IllegalStateException | RollbackException e) {
			e.printStackTrace();
		}
	}

	private void onChangeComboBoxAuthor() {
		fillDictionaryData(Constants.RADIO_BUTTON_AUTHOR);
	}

	private void onChangeComboBoxCategory() {
		fillDictionaryData(Constants.RADIO_BUTTON_CATEGORY);
	}

	private void onChangeComboBoxPublishingHouse() {
		fillDictionaryData(Constants.RADIO_BUTTON_PUBLISHING_HOUSE);
	}

	private void fillDictionaryData(String pressedRadioButton) {
		switch (pressedRadioButton) {
			case Constants.RADIO_BUTTON_AUTHOR:
				Author author = (Author) tabbedForm.getPanelAdmDictionary().getComboBoxAuthor().getSelectedItem();
				if (author != null) {
					tabbedForm.getPanelAdmDictionary().getTextFieldAuthorName().setText(author.getName());
					tabbedForm.getPanelAdmDictionary().getTextFieldAuthorNickname().setText(author.getNickname());
					tabbedForm.getPanelAdmDictionary().getTextFieldAuthorSurname().setText(author.getSurname());
					tabbedForm.getPanelAdmDictionary().getDateChooserBirthDate().setDate(author.getBirthDate());
				}
				break;
			case Constants.RADIO_BUTTON_CATEGORY:
				Category category = (Category) tabbedForm.getPanelAdmDictionary().getComboBoxCategory().getSelectedItem();
				if (category != null)
					tabbedForm.getPanelAdmDictionary().getTextFieldCategoryName().setText(category.getName());
				break;
			case Constants.RADIO_BUTTON_PUBLISHING_HOUSE:
				PublishingHouse publishingHouse = (PublishingHouse) tabbedForm.getPanelAdmDictionary().getComboBoxPublishingHouse()
						.getSelectedItem();
				if (publishingHouse != null)
					tabbedForm.getPanelAdmDictionary().getTextFieldPublishingHouseName().setText(publishingHouse.getName());
				break;
		}
	}

	private void switchRadioButtonsEnable(String pressedRadioButton) {
		switch (pressedRadioButton) {
			case Constants.RADIO_BUTTON_AUTHOR:
				this.pressedRadioButton = Constants.RADIO_BUTTON_AUTHOR;
				tabbedForm.getPanelAdmDictionary().getTextFieldAuthorName().setEnabled(true);
				tabbedForm.getPanelAdmDictionary().getTextFieldAuthorNickname().setEnabled(true);
				tabbedForm.getPanelAdmDictionary().getTextFieldAuthorSurname().setEnabled(true);
				tabbedForm.getPanelAdmDictionary().getDateChooserBirthDate().setEnabled(true);
				tabbedForm.getPanelAdmDictionary().getTextFieldCategoryName().setEnabled(false);
				tabbedForm.getPanelAdmDictionary().getTextFieldPublishingHouseName().setEnabled(false);
				break;
			case Constants.RADIO_BUTTON_CATEGORY:
				this.pressedRadioButton = Constants.RADIO_BUTTON_CATEGORY;
				tabbedForm.getPanelAdmDictionary().getTextFieldAuthorName().setEnabled(false);
				tabbedForm.getPanelAdmDictionary().getTextFieldAuthorNickname().setEnabled(false);
				tabbedForm.getPanelAdmDictionary().getTextFieldAuthorSurname().setEnabled(false);
				tabbedForm.getPanelAdmDictionary().getDateChooserBirthDate().setEnabled(false);
				tabbedForm.getPanelAdmDictionary().getTextFieldCategoryName().setEnabled(true);
				tabbedForm.getPanelAdmDictionary().getTextFieldPublishingHouseName().setEnabled(false);
				break;
			case Constants.RADIO_BUTTON_PUBLISHING_HOUSE:
				this.pressedRadioButton = Constants.RADIO_BUTTON_PUBLISHING_HOUSE;
				tabbedForm.getPanelAdmDictionary().getTextFieldAuthorName().setEnabled(false);
				tabbedForm.getPanelAdmDictionary().getTextFieldAuthorNickname().setEnabled(false);
				tabbedForm.getPanelAdmDictionary().getTextFieldAuthorSurname().setEnabled(false);
				tabbedForm.getPanelAdmDictionary().getDateChooserBirthDate().setEnabled(false);
				tabbedForm.getPanelAdmDictionary().getTextFieldCategoryName().setEnabled(false);
				tabbedForm.getPanelAdmDictionary().getTextFieldPublishingHouseName().setEnabled(true);
				break;
		}
	}
}
