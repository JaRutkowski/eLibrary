package com.javafee.tabbedform.library;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.hibernate.dto.library.Author;
import com.javafee.hibernate.dto.library.Category;
import com.javafee.hibernate.dto.library.PublishingHouse;
import com.javafee.uniform.AddMultiPanel;
import com.javafee.uniform.DecisionPanel;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.JScrollPane;

public class BookFilterPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private DecisionPanel decisionPanel;
	private AdmBookDataPanel admBookDataPanel;
	private AddMultiPanel authorAddMultiPanel;
	private AddMultiPanel categoryAddMultiPanel;
	private AddMultiPanel publishingHouseAddMultiPanel;

	private JComboBox<Author> comboBoxAuthor;
	private JComboBox<Category> comboBoxCategory;
	private JComboBox<PublishingHouse> comboBoxPublishingHouse;
	private JTextArea textAreaDetails;
	private JLabel lblAuthor;
	private JLabel lblCategory;
	private JLabel lblPublishingHouse;
	private JScrollPane scrollPane;

	public BookFilterPanel() {
		setBackground(Utils.getApplicationColor());
		setBorder(
				new TitledBorder(null, SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.bookFilterPanelBorderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 188, 205, 175, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 33, 0, 27, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		lblAuthor = new JLabel(SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.lblAuthor"));
		GridBagConstraints gbc_lblAuthor = new GridBagConstraints();
		gbc_lblAuthor.anchor = GridBagConstraints.WEST;
		gbc_lblAuthor.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthor.gridx = 0;
		gbc_lblAuthor.gridy = 0;
		add(lblAuthor, gbc_lblAuthor);

		comboBoxAuthor = new JComboBox<Author>();
		GridBagConstraints gbc_comboBoxAuthor = new GridBagConstraints();
		gbc_comboBoxAuthor.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxAuthor.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxAuthor.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxAuthor.gridx = 0;
		gbc_comboBoxAuthor.gridy = 1;
		add(comboBoxAuthor, gbc_comboBoxAuthor);

		authorAddMultiPanel = new AddMultiPanel();
		GridBagConstraints gbc_authorAddMultiPanel = new GridBagConstraints();
		gbc_authorAddMultiPanel.anchor = GridBagConstraints.NORTH;
		gbc_authorAddMultiPanel.insets = new Insets(0, 0, 5, 5);
		gbc_authorAddMultiPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_authorAddMultiPanel.gridx = 1;
		gbc_authorAddMultiPanel.gridy = 1;
		add(authorAddMultiPanel, gbc_authorAddMultiPanel);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 5;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);
		
		textAreaDetails = new JTextArea();
		scrollPane.setViewportView(textAreaDetails);
		textAreaDetails.setBackground(UIManager.getColor("Panel.background"));
		textAreaDetails.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textAreaDetails.setEditable(false);
		textAreaDetails.setEnabled(false);

		lblCategory = new JLabel(SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.lblCategory"));
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.anchor = GridBagConstraints.WEST;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 0;
		gbc_lblCategory.gridy = 2;
		add(lblCategory, gbc_lblCategory);

		comboBoxCategory = new JComboBox<Category>();
		GridBagConstraints gbc_comboBoxCategory = new GridBagConstraints();
		gbc_comboBoxCategory.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxCategory.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxCategory.gridx = 0;
		gbc_comboBoxCategory.gridy = 3;
		add(comboBoxCategory, gbc_comboBoxCategory);

		categoryAddMultiPanel = new AddMultiPanel();
		GridBagConstraints gbc_categoryAddMultiPanel = new GridBagConstraints();
		gbc_categoryAddMultiPanel.anchor = GridBagConstraints.NORTH;
		gbc_categoryAddMultiPanel.insets = new Insets(0, 0, 5, 5);
		gbc_categoryAddMultiPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_categoryAddMultiPanel.gridx = 1;
		gbc_categoryAddMultiPanel.gridy = 3;
		add(categoryAddMultiPanel, gbc_categoryAddMultiPanel);

		lblPublishingHouse = new JLabel(SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.lblPublishingHouse"));
		GridBagConstraints gbc_lblPublishingHouse = new GridBagConstraints();
		gbc_lblPublishingHouse.anchor = GridBagConstraints.WEST;
		gbc_lblPublishingHouse.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublishingHouse.gridx = 0;
		gbc_lblPublishingHouse.gridy = 4;
		add(lblPublishingHouse, gbc_lblPublishingHouse);

		comboBoxPublishingHouse = new JComboBox<PublishingHouse>();
		GridBagConstraints gbc_comboBoxPublishingHouse = new GridBagConstraints();
		gbc_comboBoxPublishingHouse.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxPublishingHouse.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxPublishingHouse.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxPublishingHouse.gridx = 0;
		gbc_comboBoxPublishingHouse.gridy = 5;
		add(comboBoxPublishingHouse, gbc_comboBoxPublishingHouse);

		admBookDataPanel = new AdmBookDataPanel();
		admBookDataPanel.setEnabled(false);
		admBookDataPanel.setVisible(false);

		publishingHouseAddMultiPanel = new AddMultiPanel();
		GridBagConstraints gbc_publishingHouseAddMultiPanel = new GridBagConstraints();
		gbc_publishingHouseAddMultiPanel.anchor = GridBagConstraints.NORTH;
		gbc_publishingHouseAddMultiPanel.insets = new Insets(0, 0, 5, 5);
		gbc_publishingHouseAddMultiPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_publishingHouseAddMultiPanel.gridx = 1;
		gbc_publishingHouseAddMultiPanel.gridy = 5;
		add(publishingHouseAddMultiPanel, gbc_publishingHouseAddMultiPanel);
		
		GridBagConstraints gbc_admBookDataPanel = new GridBagConstraints();
		gbc_admBookDataPanel.gridwidth = 3;
		gbc_admBookDataPanel.insets = new Insets(0, 0, 5, 0);
		gbc_admBookDataPanel.fill = GridBagConstraints.BOTH;
		gbc_admBookDataPanel.gridx = 0;
		gbc_admBookDataPanel.gridy = 6;
		add(admBookDataPanel, gbc_admBookDataPanel);

		decisionPanel = new DecisionPanel();
		GridBagConstraints gbc_decisionPanel = new GridBagConstraints();
		gbc_decisionPanel.insets = new Insets(0, 0, 0, 5);
		gbc_decisionPanel.anchor = GridBagConstraints.NORTH;
		gbc_decisionPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_decisionPanel.gridx = 1;
		gbc_decisionPanel.gridy = 7;
		add(decisionPanel, gbc_decisionPanel);
	}

	public JComboBox<Author> getComboBoxAuthor() {
		return comboBoxAuthor;
	}

	public void setComboBoxAuthor(JComboBox<Author> comboBoxAuthor) {
		this.comboBoxAuthor = comboBoxAuthor;
	}

	public JComboBox<Category> getComboBoxCategory() {
		return comboBoxCategory;
	}

	public void setComboBoxCategory(JComboBox<Category> comboBoxCategory) {
		this.comboBoxCategory = comboBoxCategory;
	}

	public JComboBox<PublishingHouse> getComboBoxPublishingHouse() {
		return comboBoxPublishingHouse;
	}

	public void setComboBoxPublishingHouse(JComboBox<PublishingHouse> comboBoxPublishingHouse) {
		this.comboBoxPublishingHouse = comboBoxPublishingHouse;
	}

	public DecisionPanel getDecisionPanel() {
		return decisionPanel;
	}

	public AdmBookDataPanel getAdmBookDataPanel() {
		return admBookDataPanel;
	}
	
	public AddMultiPanel getAuthorAddMultiPanel() {
		return authorAddMultiPanel;
	}
	
	public AddMultiPanel getCategoryAddMultiPanel() {
		return categoryAddMultiPanel;
	}
	
	public AddMultiPanel getPublishingHouseAddMultiPanel() {
		return publishingHouseAddMultiPanel;
	}
	
	public JTextArea getTextAreaDetails() {
		return textAreaDetails;
	}

	public JLabel getLblAuthor() {
		return lblAuthor;
	}

	public void setLblAuthor(JLabel lblAuthor) {
		this.lblAuthor = lblAuthor;
	}

	public JLabel getLblCategory() {
		return lblCategory;
	}

	public void setLblCategory(JLabel lblCategory) {
		this.lblCategory = lblCategory;
	}

	public JLabel getLblPublishingHouse() {
		return lblPublishingHouse;
	}

	public void setLblPublishingHouse(JLabel lblPublishingHouse) {
		this.lblPublishingHouse = lblPublishingHouse;
	}
	
	
}
