package com.javafee.tabbedform;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.RollbackException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.javafee.common.Constans;
import com.javafee.common.IActionForm;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.exception.RefusedAdmDictionaryEventLoadingException;
import com.javafee.exception.RefusedClientsEventLoadingException;
import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.Author;
import com.javafee.hibernate.dto.library.Book;
import com.javafee.hibernate.dto.library.Category;
import com.javafee.hibernate.dto.library.PublishingHouse;
import com.javafee.hibernate.dto.library.Volume;
import com.javafee.tabbedform.admdictionaries.AdmDictionaryPanel;

public class TabAdmDictioaryEvent implements IActionForm {
	private TabbedForm tabbedForm;

	private String pressedRadioButton = Constans.RADIO_BUTTON_AUTHOR;

	@SuppressWarnings("unused")
	private static TabAdmDictioaryEvent admDictionaryEvent = null;

	public TabAdmDictioaryEvent(TabbedForm tabbedForm) {
		this.control(tabbedForm);
	}

	public static TabAdmDictioaryEvent getInstance(TabbedForm tabbedForm) {
		if (admDictionaryEvent == null) {
			admDictionaryEvent = new TabAdmDictioaryEvent(tabbedForm);
		} else
			new RefusedAdmDictionaryEventLoadingException("Cannot adm dictionary event loading");
		return admDictionaryEvent;
	}

	public void control(TabbedForm tabbedForm) {
		setTabbedForm(tabbedForm);
		initializeForm();
		
		fillDictionaryData(Constans.RADIO_BUTTON_AUTHOR);
		fillDictionaryData(Constans.RADIO_BUTTON_CATEGORY);
		fillDictionaryData(Constans.RADIO_BUTTON_PUBLISHING_HOUSE);
		
		tabbedForm.getPanelAdmDictionary().getRadioButtonAuthor()
				.addActionListener(e -> switchRadioButtonsEnable(Constans.RADIO_BUTTON_AUTHOR));
		tabbedForm.getPanelAdmDictionary().getRadioButtonCategory()
				.addActionListener(e -> switchRadioButtonsEnable(Constans.RADIO_BUTTON_CATEGORY));
		tabbedForm.getPanelAdmDictionary().getRadioButtonPublishingHouse()
				.addActionListener(e -> switchRadioButtonsEnable(Constans.RADIO_BUTTON_PUBLISHING_HOUSE));
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

	public void setTabbedForm(TabbedForm tabbedForm) {
		this.tabbedForm = tabbedForm;
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
		DefaultComboBoxModel<Author> comboBoxAuthor = new DefaultComboBoxModel<Author>();
		HibernateDao<Author, Integer> author = new HibernateDao<Author, Integer>(Author.class);
		List<Author> authorListToSort = author.findAll();
		authorListToSort
				.sort(Comparator.comparing(Author::getSurname, Comparator.nullsFirst(Comparator.naturalOrder())));
		authorListToSort.forEach(c -> comboBoxAuthor.addElement(c));
		comboBoxAuthor.insertElementAt(null, 0);

		tabbedForm.getPanelAdmDictionary().getComboBoxAuthor().setModel(comboBoxAuthor);
	}

	private void reloadComboBoxCategory() {
		DefaultComboBoxModel<Category> comboBoxCategory = new DefaultComboBoxModel<Category>();
		HibernateDao<Category, Integer> category = new HibernateDao<Category, Integer>(Category.class);
		List<Category> categoryListToSort = category.findAll();
		categoryListToSort
				.sort(Comparator.comparing(Category::getName, Comparator.nullsFirst(Comparator.naturalOrder())));
		categoryListToSort.forEach(c -> comboBoxCategory.addElement(c));
		comboBoxCategory.insertElementAt(null, 0);

		tabbedForm.getPanelAdmDictionary().getComboBoxCategory().setModel(comboBoxCategory);
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

		tabbedForm.getPanelAdmDictionary().getComboBoxPublishingHouse().setModel(comboBoxPublishingHouse);
	}

	private void onClickBtnAdd(String pressedRadioButton) {
		System.out.println("dodawnie" + pressedRadioButton);
		final AdmDictionaryPanel admDictionaryPanel = tabbedForm.getPanelAdmDictionary();
		switch (pressedRadioButton) {
		case Constans.RADIO_BUTTON_AUTHOR:
			try {
				HibernateUtil.beginTransaction();
				final Author author = new Author();
				author.setName(admDictionaryPanel.getTextFieldAuthorName().getText() != null
						? admDictionaryPanel.getTextFieldAuthorName().getText().toString()
						: null);
				author.setNickname(admDictionaryPanel.getTextFieldAuthorNickname().getText() != null
						? admDictionaryPanel.getTextFieldAuthorNickname().getText().toString()
						: null);
				author.setSurname(admDictionaryPanel.getTextFieldAuthorSurname().getText() != null
						? admDictionaryPanel.getTextFieldAuthorSurname().getText().toString()
						: null);
				author.setBirthDate(admDictionaryPanel.getDateChooserBirthDate().getDate() != null
						? admDictionaryPanel.getDateChooserBirthDate().getDate()
						: null);
				HibernateUtil.getSession().save(author);
				HibernateUtil.commitTransaction();
				reloadComboBoxAuthor();
				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						"Dodano autora pomyœlnie: " + author.getName() + " " + author.getSurname(), "Sukces",
						JOptionPane.INFORMATION_MESSAGE);

			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (RollbackException e) {
				e.printStackTrace();
			}
			break;
		case Constans.RADIO_BUTTON_CATEGORY:
			try {
				HibernateUtil.beginTransaction();
				final Category category = new Category();
				category.setName(admDictionaryPanel.getTextFieldCategoryName().getText() != null
						? tabbedForm.getPanelAdmDictionary().getTextFieldCategoryName().getText().toString()
						: null);
				HibernateUtil.getSession().save(category);
				HibernateUtil.commitTransaction();
				reloadComboBoxCategory();
				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						"Dodano kategorie pomyœlnie: " + category.getName(), "Sukces", JOptionPane.INFORMATION_MESSAGE);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (RollbackException e) {
				e.printStackTrace();
			}
			break;
		case Constans.RADIO_BUTTON_PUBLISHING_HOUSE:
			try {
				HibernateUtil.beginTransaction();
				final PublishingHouse publishingHouse = new PublishingHouse();
				publishingHouse.setName(admDictionaryPanel.getTextFieldPublishingHouseName().getText() != null
						? admDictionaryPanel.getTextFieldPublishingHouseName().getText().toString()
						: null);
				HibernateUtil.getSession().save(publishingHouse);
				HibernateUtil.commitTransaction();
				reloadComboBoxPublishingHouse();
				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						"Dodano wydawnictwo pomyœlnie: " + publishingHouse.getName(), "Sukces",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (RollbackException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	private void onClickBtnModify(String pressedRadioButton) {
		final AdmDictionaryPanel admDictionaryPanel = tabbedForm.getPanelAdmDictionary();
		switch (pressedRadioButton) {
		case Constans.RADIO_BUTTON_AUTHOR:
				final Author authorClicked = ((Author) admDictionaryPanel.getComboBoxAuthor().getSelectedItem());
				final org.hibernate.query.Query query = HibernateUtil.getSession()
						.createSQLQuery("select * from lib_book_author where id_author = :idAuthor")
						.addEntity(Book.class).setParameter("idAuthor", authorClicked.getIdAuthor());
				final List<Author> resultAuthor = query.list();

				if (resultAuthor.isEmpty()) {
					try {
				HibernateUtil.beginTransaction();
				authorClicked.setIdAuthor(authorClicked.getIdAuthor());
				authorClicked.setName(admDictionaryPanel.getTextFieldAuthorName().getText() != null
						? admDictionaryPanel.getTextFieldAuthorName().getText().toString()
						: null);
				authorClicked.setNickname(admDictionaryPanel.getTextFieldAuthorNickname().getText() != null
						? admDictionaryPanel.getTextFieldAuthorNickname().getText().toString()
						: null);
				authorClicked.setSurname(admDictionaryPanel.getTextFieldAuthorSurname().getText() != null
						? admDictionaryPanel.getTextFieldAuthorSurname().getText().toString()
						: null);
				authorClicked.setBirthDate(admDictionaryPanel.getDateChooserBirthDate().getDate() != null
						? admDictionaryPanel.getDateChooserBirthDate().getDate()
						: null);
				HibernateUtil.getSession().update(authorClicked);
				HibernateUtil.commitTransaction();
				reloadComboBoxAuthor();
				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.modifyDictionaryElementSuccess"), SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.modifyDictionaryElementSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);

			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (RollbackException e) {
				e.printStackTrace();
			}
			} else {
				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.modifyDictionaryElementWarning"), SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.modifyDictionaryElementWarningTitle"),
						JOptionPane.ERROR_MESSAGE);
			}
			break;
		case Constans.RADIO_BUTTON_CATEGORY:
				final Category categoryClicked = ((Category) admDictionaryPanel.getComboBoxCategory()
						.getSelectedItem());
				final org.hibernate.query.Query query1 = HibernateUtil.getSession()
						.createSQLQuery("select * from lib_book_category where id_category = :idCategory")
						.setParameter("idCategory", categoryClicked.getIdCategory());
				final List<Category> resultCategory = query1.list();

				if (resultCategory.isEmpty()) {
				try {
				HibernateUtil.beginTransaction();
				categoryClicked.setIdCategory(categoryClicked.getIdCategory());
				categoryClicked.setName(admDictionaryPanel.getTextFieldCategoryName().getText() != null
						? tabbedForm.getPanelAdmDictionary().getTextFieldCategoryName().getText().toString()
						: null);
				HibernateUtil.getSession().update(categoryClicked);
				HibernateUtil.commitTransaction();
				reloadComboBoxCategory();
				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.modifyDictionaryElementSuccess"), SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.modifyDictionaryElementSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (RollbackException e) {
				e.printStackTrace();
			}}else {
				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.modifyDictionaryElementWarning"), SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.modifyDictionaryElementWarningTitle"),
						JOptionPane.ERROR_MESSAGE);
			}
			break;
		case Constans.RADIO_BUTTON_PUBLISHING_HOUSE:
				final PublishingHouse publishingClicked = ((PublishingHouse) admDictionaryPanel.getComboBoxPublishingHouse()
						.getSelectedItem());
				final org.hibernate.query.Query query2 = HibernateUtil.getSession()
						.createSQLQuery("select * from lib_book_publishing_house where id_publishing_house = :idP")
						.setParameter("idP", publishingClicked.getIdPublishingHouse());
				final List<PublishingHouse> resultPuBlishinHouse = query2.list();

				if (resultPuBlishinHouse.isEmpty()) {
				try {
				HibernateUtil.beginTransaction();
				publishingClicked.setIdPublishingHouse(publishingClicked.getIdPublishingHouse());
				publishingClicked.setName(admDictionaryPanel.getTextFieldPublishingHouseName().getText() != null
						? admDictionaryPanel.getTextFieldPublishingHouseName().getText().toString()
						: null);
				HibernateUtil.getSession().update(publishingClicked);
				HibernateUtil.commitTransaction();
				reloadComboBoxPublishingHouse();
				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.modifyDictionaryElementSuccess"), SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.modifyDictionaryElementSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (RollbackException e) {
				e.printStackTrace();
			}}else {
				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.modifyDictionaryElementWarning"), SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.modifyDictionaryElementWarningTitle"),
						JOptionPane.ERROR_MESSAGE);
			}
			break;
		}
	}

	private void onClickBtnDelete(String pressedRadioButton) {
		final AdmDictionaryPanel admDictionaryPanel = tabbedForm.getPanelAdmDictionary();

		switch (pressedRadioButton) {
		case Constans.RADIO_BUTTON_AUTHOR:
			if (Utils.displayConfirmDialog(
					SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
					"") == JOptionPane.YES_OPTION) {
				final Author author = ((Author) admDictionaryPanel.getComboBoxAuthor().getSelectedItem());

				final org.hibernate.query.Query query = HibernateUtil.getSession()
						.createSQLQuery("select * from lib_book_author where id_author = :idAuthor")
						.addEntity(Book.class).setParameter("idAuthor", author.getIdAuthor());
				final List<Author> resultAuthor = query.list();

				if (resultAuthor.isEmpty()) {
					try {
						admDictionaryPanel.getComboBoxAuthor().removeItem(author);

						HibernateUtil.beginTransaction();
						HibernateUtil.getSession().delete(author);
						HibernateUtil.commitTransaction();
						reloadComboBoxAuthor();
						fillDictionaryData(Constans.RADIO_BUTTON_AUTHOR);
						JOptionPane.showMessageDialog(tabbedForm.getFrame(),
								SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.deleteDictionaryElementSuccess"), SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.deleteDictionaryElementSuccessTitle"),
								JOptionPane.INFORMATION_MESSAGE);

					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (RollbackException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(tabbedForm.getFrame(),
							SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.deleteDictionaryElementWarning"), SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.deleteDictionaryElementWarningTitle"),
							JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		case Constans.RADIO_BUTTON_CATEGORY:
			if (Utils.displayConfirmDialog(
					SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
					"") == JOptionPane.YES_OPTION) {
				final Category category = ((Category) admDictionaryPanel.getComboBoxCategory().getSelectedItem());
				final org.hibernate.query.Query query1 = HibernateUtil.getSession()
						.createSQLQuery("select * from lib_book_category where id_category = :idCategory")
						.setParameter("idCategory", category.getIdCategory());
				final List<Category> resultCategory = query1.list();

				if (resultCategory.isEmpty()) {
					try {
						admDictionaryPanel.getComboBoxCategory().removeItem(category);

						HibernateUtil.beginTransaction();
						HibernateUtil.getSession().delete(category);
						HibernateUtil.commitTransaction();
						reloadComboBoxCategory();
						fillDictionaryData(Constans.RADIO_BUTTON_CATEGORY);
						JOptionPane.showMessageDialog(tabbedForm.getFrame(),
								SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.deleteDictionaryElementSuccess"), SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.deleteDictionaryElementSuccessTitle"),
								JOptionPane.INFORMATION_MESSAGE);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (RollbackException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(tabbedForm.getFrame(),
							SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.deleteDictionaryElementWarning"), SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.deleteDictionaryElementWarningTitle"),
							JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		case Constans.RADIO_BUTTON_PUBLISHING_HOUSE:
			if (Utils.displayConfirmDialog(
					SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
					"") == JOptionPane.YES_OPTION) {
			final PublishingHouse publishingHouse = ((PublishingHouse) admDictionaryPanel.getComboBoxPublishingHouse()
					.getSelectedItem());
			final org.hibernate.query.Query query2 = HibernateUtil.getSession()
					.createSQLQuery("select * from lib_book_publishing_house where id_publishing_house = :idP")
					.setParameter("idP", publishingHouse.getIdPublishingHouse());
			final List<PublishingHouse> resultPuBlishinHouse = query2.list();

			if (resultPuBlishinHouse.isEmpty()) {
				try {
					HibernateUtil.beginTransaction();

					HibernateUtil.getSession().delete(publishingHouse);
					HibernateUtil.commitTransaction();
					reloadComboBoxPublishingHouse();
					fillDictionaryData(Constans.RADIO_BUTTON_PUBLISHING_HOUSE);
					JOptionPane.showMessageDialog(tabbedForm.getFrame(),
							SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.deleteDictionaryElementSuccess"), SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.deleteDictionaryElementSuccessTitle"),
							JOptionPane.INFORMATION_MESSAGE);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (RollbackException e) {
					e.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.deleteDictionaryElementWarning"), SystemProperties.getInstance().getResourceBundle().getString("tabDictionaryEvent.deleteDictionaryElementWarningTitle"),
						JOptionPane.ERROR_MESSAGE);
			}
			}
			break;

		}
	}

	private <T> void delete(final String nameParameter, final String entityName, final T parameter) {
		final org.hibernate.query.Query query = HibernateUtil.getSession()
				.createQuery("delete " + entityName + " where " + nameParameter + " = " + parameter);
		int result = query.executeUpdate();

	}

	private void onChangeComboBoxAuthor() {
		fillDictionaryData(Constans.RADIO_BUTTON_AUTHOR);
	}

	private void onChangeComboBoxCategory() {
		fillDictionaryData(Constans.RADIO_BUTTON_CATEGORY);
	}

	private void onChangeComboBoxPublishingHouse() {
		fillDictionaryData(Constans.RADIO_BUTTON_PUBLISHING_HOUSE);
	}

	private void fillDictionaryData(String pressedRadioButton) {
		switch (pressedRadioButton) {
		case Constans.RADIO_BUTTON_AUTHOR:
			Author author = (Author) tabbedForm.getPanelAdmDictionary().getComboBoxAuthor().getSelectedItem() != null
					? (Author) tabbedForm.getPanelAdmDictionary().getComboBoxAuthor().getSelectedItem()
					: null;
			if (author != null) {
				tabbedForm.getPanelAdmDictionary().getTextFieldAuthorName().setText(author.getName());
				tabbedForm.getPanelAdmDictionary().getTextFieldAuthorNickname().setText(author.getNickname());
				tabbedForm.getPanelAdmDictionary().getTextFieldAuthorSurname().setText(author.getSurname());
				tabbedForm.getPanelAdmDictionary().getDateChooserBirthDate().setDate(author.getBirthDate());
			}
			break;
		case Constans.RADIO_BUTTON_CATEGORY:
			Category category = (Category) tabbedForm.getPanelAdmDictionary().getComboBoxCategory()
					.getSelectedItem() != null
							? (Category) tabbedForm.getPanelAdmDictionary().getComboBoxCategory().getSelectedItem()
							: null;
			if (category != null)
				tabbedForm.getPanelAdmDictionary().getTextFieldCategoryName().setText(category.getName());
			break;
		case Constans.RADIO_BUTTON_PUBLISHING_HOUSE:
			PublishingHouse publishingHouse = (PublishingHouse) tabbedForm.getPanelAdmDictionary()
					.getComboBoxPublishingHouse().getSelectedItem() != null
							? (PublishingHouse) tabbedForm.getPanelAdmDictionary().getComboBoxPublishingHouse()
									.getSelectedItem()
							: null;
			if (publishingHouse != null)
				tabbedForm.getPanelAdmDictionary().getTextFieldPublishingHouseName().setText(publishingHouse.getName());
			break;
		}
	}

	private void switchRadioButtonsEnable(String pressedRadioButton) {
		System.out.println("switched" + pressedRadioButton);
		switch (pressedRadioButton) {
		case Constans.RADIO_BUTTON_AUTHOR:
			this.pressedRadioButton = Constans.RADIO_BUTTON_AUTHOR;
			tabbedForm.getPanelAdmDictionary().getTextFieldAuthorName().setEnabled(true);
			tabbedForm.getPanelAdmDictionary().getTextFieldAuthorNickname().setEnabled(true);
			tabbedForm.getPanelAdmDictionary().getTextFieldAuthorSurname().setEnabled(true);
			tabbedForm.getPanelAdmDictionary().getDateChooserBirthDate().setEnabled(true);
			tabbedForm.getPanelAdmDictionary().getTextFieldCategoryName().setEnabled(false);
			tabbedForm.getPanelAdmDictionary().getTextFieldPublishingHouseName().setEnabled(false);
			break;
		case Constans.RADIO_BUTTON_CATEGORY:
			this.pressedRadioButton = Constans.RADIO_BUTTON_CATEGORY;
			tabbedForm.getPanelAdmDictionary().getTextFieldAuthorName().setEnabled(false);
			tabbedForm.getPanelAdmDictionary().getTextFieldAuthorNickname().setEnabled(false);
			tabbedForm.getPanelAdmDictionary().getTextFieldAuthorSurname().setEnabled(false);
			tabbedForm.getPanelAdmDictionary().getDateChooserBirthDate().setEnabled(false);
			tabbedForm.getPanelAdmDictionary().getTextFieldCategoryName().setEnabled(true);
			tabbedForm.getPanelAdmDictionary().getTextFieldPublishingHouseName().setEnabled(false);
			break;
		case Constans.RADIO_BUTTON_PUBLISHING_HOUSE:
			this.pressedRadioButton = Constans.RADIO_BUTTON_PUBLISHING_HOUSE;
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
