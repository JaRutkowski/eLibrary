package com.javafee.emailform;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import com.javafee.common.BaseForm;
import com.javafee.common.Constants;
import com.javafee.common.SystemProperties;
import com.javafee.emailform.emails.ComposePagePanel;
import com.javafee.emailform.emails.DraftPagePanel;
import com.javafee.emailform.emails.OutboxMailPagePanel;
import com.javafee.emailform.emails.TemplatePagePanel;
import com.javafee.unicomponent.jmenubar.CustomJMenu;
import com.javafee.unicomponent.jmenubar.CustomJMenuItem;
import com.javafee.unicomponent.jtabbedpane.CustomJTabbedPane;

import lombok.Getter;

public class EmailForm extends BaseForm {
	@Getter
	private JTabbedPane tabbedPane;
	@Getter
	private ComposePagePanel panelComposePage;
	@Getter
	private OutboxMailPagePanel panelOutboxPage;
	@Getter
	private DraftPagePanel panelDraftPage;
	@Getter
	private TemplatePagePanel panelTemplatePage;
	@Getter
	private JMenuBar menuBar;
	@Getter
	private JMenu menuTemplate;
	@Getter
	private JMenuItem menuSaveAsTemplate;
	@Getter
	private JMenuItem menuLoadTemplate;
	@Getter
	private JMenuItem menuManageTemplate;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		super.initialize();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		frame.setMinimumSize(Constants.EMAIL_FORM_MINIMUM_SIZE);
		frame.getContentPane().setLayout(gridBagLayout);
		frame.setBounds(100, 100, 576, 424);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		tabbedPane = new CustomJTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridheight = 2;
		gbc_tabbedPane.gridwidth = 2;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		frame.getContentPane().add(tabbedPane, gbc_tabbedPane);

		menuBar = new JMenuBar();
		menuTemplate = new CustomJMenu(
				SystemProperties.getInstance().getResourceBundle().getString("emailForm.menuTemplateTitle"));
		menuBar.add(menuTemplate);

		menuSaveAsTemplate = new CustomJMenuItem(
				SystemProperties.getInstance().getResourceBundle().getString("emailForm.menuItemSaveAsTemplateTitle"),
				KeyEvent.VK_CONTROL);
		menuSaveAsTemplate.setAccelerator(Constants.SHURTCUT_SAVE_TEMPLATE);
		menuTemplate.add(menuSaveAsTemplate);

		menuLoadTemplate = new CustomJMenuItem(
				SystemProperties.getInstance().getResourceBundle().getString("emailForm.menuItemLoadTemplateTitle"),
				KeyEvent.VK_CONTROL);
		menuLoadTemplate.setAccelerator(Constants.SHURTCUT_LOAD_TEMPLATE);
		menuTemplate.add(menuLoadTemplate);

		menuManageTemplate = new CustomJMenuItem(
				SystemProperties.getInstance().getResourceBundle().getString("emailForm.menuItemManageTemplateTitle"),
				KeyEvent.VK_CONTROL);
		menuManageTemplate.setAccelerator(Constants.SHURTCUT_MANAGE_TEMPLATE);
		menuTemplate.add(menuManageTemplate);

		frame.setJMenuBar(menuBar);

		panelComposePage = new ComposePagePanel();

		panelOutboxPage = new OutboxMailPagePanel();

		panelDraftPage = new DraftPagePanel();

		panelTemplatePage = new TemplatePagePanel();
	}
}
