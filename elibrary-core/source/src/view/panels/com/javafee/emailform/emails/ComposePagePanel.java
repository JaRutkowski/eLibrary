package com.javafee.emailform.emails;

import java.awt.*;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.hibernate.dto.library.Client;

import lombok.Getter;

public class ComposePagePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel lblTo;
	@Getter
	private JLabel lblCC;
	@Getter
	private JLabel lblBCC;
	private JLabel lblTopic;

	@Getter
	private JCheckBox chckbxCC;
	@Getter
	private JCheckBox chckbxBCC;

	@Getter
	private JComboBox<Client> comboBoxTo;
	@Getter
	private JComboBox<Client> comboBoxCC;
	@Getter
	private JComboBox<Client> comboBoxBCC;

	@Getter
	private JTextField textFieldTopic;

	@Getter
	private JEditorPane editorPaneContent;
	@Getter
	private ComposeNavigationPanel composeNavigationEmailPanel;

	public ComposePagePanel() {
		Utils.setLookAndFeel();
		setBackground(Utils.getApplicationUserDefineColor());
		setBorder(new TitledBorder(null,
				SystemProperties.getInstance().getResourceBundle()
						.getString("composePagePanel.composePagePanelBorderTitle"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{37, 300, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 200, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		lblTo = new JLabel(SystemProperties.getInstance().getResourceBundle().getString("composePagePanel.lblTo"));
		GridBagConstraints gbc_lblTo = new GridBagConstraints();
		gbc_lblTo.insets = new Insets(0, 5, 5, 5);
		gbc_lblTo.gridx = 0;
		gbc_lblTo.gridy = 0;
		add(lblTo, gbc_lblTo);

		comboBoxTo = new JComboBox<Client>();
		GridBagConstraints gbc_comboBoxTo = new GridBagConstraints();
		gbc_comboBoxTo.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxTo.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxTo.gridx = 1;
		gbc_comboBoxTo.gridy = 0;
		add(comboBoxTo, gbc_comboBoxTo);

		chckbxCC = new JCheckBox(
				SystemProperties.getInstance().getResourceBundle().getString("composePagePanel.chckbxCC"));
		chckbxCC.setBackground(Utils.getApplicationUserDefineColor());
		GridBagConstraints gbc_chckbxCC = new GridBagConstraints();
		gbc_chckbxCC.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxCC.gridx = 2;
		gbc_chckbxCC.gridy = 0;
		add(chckbxCC, gbc_chckbxCC);

		chckbxBCC = new JCheckBox(
				SystemProperties.getInstance().getResourceBundle().getString("composePagePanel.chckbxBCC"));
		chckbxBCC.setBackground(Utils.getApplicationUserDefineColor());
		GridBagConstraints gbc_chckbxBCC = new GridBagConstraints();
		gbc_chckbxBCC.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxBCC.gridx = 3;
		gbc_chckbxBCC.gridy = 0;
		add(chckbxBCC, gbc_chckbxBCC);

		lblCC = new JLabel(SystemProperties.getInstance().getResourceBundle().getString("composePagePanel.lblCC"));
		GridBagConstraints gbc_lblCC = new GridBagConstraints();
		gbc_lblCC.insets = new Insets(0, 5, 5, 5);
		gbc_lblCC.gridx = 0;
		gbc_lblCC.gridy = 1;
		add(lblCC, gbc_lblCC);

		comboBoxCC = new JComboBox<Client>();
		GridBagConstraints gbc_comboBoxCC = new GridBagConstraints();
		gbc_comboBoxCC.gridwidth = 3;
		gbc_comboBoxCC.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxCC.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxCC.gridx = 1;
		gbc_comboBoxCC.gridy = 1;
		add(comboBoxCC, gbc_comboBoxCC);

		lblBCC = new JLabel(SystemProperties.getInstance().getResourceBundle().getString("composePagePanel.lblBCC"));
		GridBagConstraints gbc_lblBCC = new GridBagConstraints();
		gbc_lblBCC.insets = new Insets(0, 5, 5, 5);
		gbc_lblBCC.gridx = 0;
		gbc_lblBCC.gridy = 2;
		add(lblBCC, gbc_lblBCC);

		comboBoxBCC = new JComboBox<Client>();
		GridBagConstraints gbc_comboBoxBCC = new GridBagConstraints();
		gbc_comboBoxBCC.gridwidth = 3;
		gbc_comboBoxBCC.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxBCC.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxBCC.gridx = 1;
		gbc_comboBoxBCC.gridy = 2;
		add(comboBoxBCC, gbc_comboBoxBCC);

		lblTopic = new JLabel(
				SystemProperties.getInstance().getResourceBundle().getString("composePagePanel.lblTopic"));
		GridBagConstraints gbc_lblTopic = new GridBagConstraints();
		gbc_lblTopic.insets = new Insets(0, 5, 5, 5);
		gbc_lblTopic.gridx = 0;
		gbc_lblTopic.gridy = 3;
		add(lblTopic, gbc_lblTopic);

		textFieldTopic = new JTextField();
		GridBagConstraints gbc_textFieldTopic = new GridBagConstraints();
		gbc_textFieldTopic.gridwidth = 3;
		gbc_textFieldTopic.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldTopic.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTopic.gridx = 1;
		gbc_textFieldTopic.gridy = 3;
		add(textFieldTopic, gbc_textFieldTopic);
		textFieldTopic.setColumns(10);

		editorPaneContent = new JEditorPane();
		editorPaneContent.setContentType("text/html");
		editorPaneContent.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_textAreaContent = new GridBagConstraints();
		gbc_textAreaContent.gridwidth = 3;
		gbc_textAreaContent.insets = new Insets(0, 0, 5, 0);
		gbc_textAreaContent.fill = GridBagConstraints.BOTH;
		gbc_textAreaContent.gridx = 1;
		gbc_textAreaContent.gridy = 4;
		add(editorPaneContent, gbc_textAreaContent);

		composeNavigationEmailPanel = new ComposeNavigationPanel();
		GridBagConstraints gbc_composeNavigationEmailPanel = new GridBagConstraints();
		gbc_composeNavigationEmailPanel.anchor = GridBagConstraints.WEST;
		gbc_composeNavigationEmailPanel.gridwidth = 3;
		gbc_composeNavigationEmailPanel.fill = GridBagConstraints.VERTICAL;
		gbc_composeNavigationEmailPanel.gridx = 1;
		gbc_composeNavigationEmailPanel.gridy = 5;
		add(composeNavigationEmailPanel, gbc_composeNavigationEmailPanel);
	}
}
