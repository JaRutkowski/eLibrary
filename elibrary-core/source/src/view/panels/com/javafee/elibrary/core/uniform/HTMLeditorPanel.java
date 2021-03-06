package com.javafee.elibrary.core.uniform;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.startform.RegistrationPanel;
import com.javafee.elibrary.core.unicomponent.border.CustomTitledBorder;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButton;
import com.javafee.elibrary.core.unicomponent.jeditorpane.CustomJEditorPane;
import com.javafee.elibrary.core.unicomponent.jtextarea.CustomJTextArea;

import lombok.Getter;

public class HTMLeditorPanel extends BasePanel {

	private static final long serialVersionUID = -7643308432142313985L;

	@Getter
	private JTextArea textAreaHTMLeditor;
	@Getter
	private JEditorPane editorPanePreview;
	@Getter
	private JButton btnParse;
	@Getter
	private JButton btnValidate;
	@Getter
	private JList<String> listStatus;
	private JScrollPane scrollPaneListStatus;

	public HTMLeditorPanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{359, 51, 366, 0};
		gridBagLayout.rowHeights = new int[]{228, 292, 100, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JPanel htmlEditorPanel = new JPanel();
		htmlEditorPanel.setBorder(new CustomTitledBorder(null,
				SystemProperties.getInstance().getResourceBundle().getString("htmlEditor.htmlEditorPanelBorderTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, null));
		htmlEditorPanel.setBackground(Utils.getApplicationUserDefinedColor());
		GridBagConstraints gbc_htmlEditorPanel = new GridBagConstraints();
		gbc_htmlEditorPanel.gridheight = 2;
		gbc_htmlEditorPanel.insets = new Insets(0, 0, 5, 5);
		gbc_htmlEditorPanel.fill = GridBagConstraints.BOTH;
		gbc_htmlEditorPanel.gridx = 0;
		gbc_htmlEditorPanel.gridy = 0;
		add(htmlEditorPanel, gbc_htmlEditorPanel);
		GridBagLayout gbl_htmlEditorPanel = new GridBagLayout();
		gbl_htmlEditorPanel.columnWidths = new int[]{189, 0};
		gbl_htmlEditorPanel.rowHeights = new int[]{0, 0};
		gbl_htmlEditorPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_htmlEditorPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		htmlEditorPanel.setLayout(gbl_htmlEditorPanel);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		htmlEditorPanel.add(scrollPane, gbc_scrollPane);

		textAreaHTMLeditor = new CustomJTextArea();
		scrollPane.setViewportView(textAreaHTMLeditor);

		btnParse = new CustomJButton();
		btnParse.setToolTipText(
				SystemProperties.getInstance().getResourceBundle().getString("htmlEditor.btnCompileTooltip"));
		btnParse.setIcon(
				new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnRoundParse-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton.insets = new Insets(5, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 0;
		add(btnParse, gbc_btnNewButton);

		JPanel previewPanel = new JPanel();
		previewPanel.setBorder(new CustomTitledBorder(null,
				SystemProperties.getInstance().getResourceBundle().getString("htmlEditor.previewPanelBorderTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, null));
		previewPanel.setBackground(Utils.getApplicationUserDefinedColor());
		GridBagConstraints gbc_previewPanel = new GridBagConstraints();
		gbc_previewPanel.gridheight = 2;
		gbc_previewPanel.insets = new Insets(0, 5, 5, 5);
		gbc_previewPanel.fill = GridBagConstraints.BOTH;
		gbc_previewPanel.gridx = 2;
		gbc_previewPanel.gridy = 0;
		add(previewPanel, gbc_previewPanel);
		GridBagLayout gbl_previewPanel = new GridBagLayout();
		gbl_previewPanel.columnWidths = new int[]{228, 0};
		gbl_previewPanel.rowHeights = new int[]{0, 0};
		gbl_previewPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_previewPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		previewPanel.setLayout(gbl_previewPanel);

		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		previewPanel.add(scrollPane_1, gbc_scrollPane_1);

		editorPanePreview = new CustomJEditorPane();
		editorPanePreview.setContentType("text/html");
		scrollPane_1.setViewportView(editorPanePreview);

		btnValidate = new CustomJButton();
		btnValidate.setToolTipText(
				SystemProperties.getInstance().getResourceBundle().getString("htmlEditor.btnVerifyTooltip"));
		btnValidate
				.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/button-bulb-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnNewButton1 = new GridBagConstraints();
		gbc_btnNewButton1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton1.insets = new Insets(0, 0, 10, 5);
		gbc_btnNewButton1.gridx = 1;
		gbc_btnNewButton1.gridy = 1;
		add(btnValidate, gbc_btnNewButton1);

		scrollPaneListStatus = new JScrollPane();
		GridBagConstraints gbc_scrollPane_listStatus = new GridBagConstraints();
		gbc_scrollPane_listStatus.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_listStatus.gridwidth = 3;
		gbc_scrollPane_listStatus.gridx = 0;
		gbc_scrollPane_listStatus.gridy = 2;
		add(scrollPaneListStatus, gbc_scrollPane_listStatus);

		listStatus = new JList<String>();
		scrollPaneListStatus.setViewportView(listStatus);
		listStatus.setEnabled(false);

	}

}
