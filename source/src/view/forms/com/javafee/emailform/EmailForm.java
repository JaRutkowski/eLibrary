package com.javafee.emailform;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import com.javafee.common.Constans;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.startform.StartForm;
import com.javafee.tabbedform.emails.ComposePagePanel;
import com.javafee.tabbedform.emails.DraftPagePanel;
import com.javafee.tabbedform.emails.SentMailPagePanel;
import com.javafee.tabbedform.emails.TemplatePagePanel;

import lombok.Getter;

public class EmailForm {
	@Getter
	private JFrame frame;
	
	@Getter
	private JTabbedPane tabbedPane;
	@Getter
	private ComposePagePanel panelComposePage;
	@Getter
	private SentMailPagePanel panelSentMailPage;
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

	public EmailForm() {
		initialize();
	}

	private void initialize() {
		Utils.setLookAndFeel();
		frame = new JFrame();
		frame.getContentPane().setBackground(Utils.getApplicationColor());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		frame.setTitle(Constans.APPLICATION_NAME);
		frame.setIconImage(
				Toolkit.getDefaultToolkit().getImage(StartForm.class.getResource("/images/splashScreen.jpg")));
		frame.setBounds(100, 100, 576, 424);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridheight = 2;
		gbc_tabbedPane.gridwidth = 2;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		frame.getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		menuBar = new JMenuBar();
		menuTemplate = new JMenu(SystemProperties.getInstance().getResourceBundle().getString("emailForm.menuTemplateTitle"));
		menuBar.add(menuTemplate);
		
		menuSaveAsTemplate = new JMenuItem(SystemProperties.getInstance().getResourceBundle().getString("emailForm.menuItemSaveAsTemplateTitle"), KeyEvent.VK_CONTROL);
		menuSaveAsTemplate.setAccelerator(Constans.SHURTCUT_SAVE_TEMPLATE);
		menuTemplate.add(menuSaveAsTemplate);

		menuLoadTemplate = new JMenuItem(SystemProperties.getInstance().getResourceBundle().getString("emailForm.menuItemLoadTemplateTitle"), KeyEvent.VK_CONTROL);
		menuLoadTemplate.setAccelerator(Constans.SHURTCUT_LOAD_TEMPLATE);
		menuTemplate.add(menuLoadTemplate);
		
		menuManageTemplate = new JMenuItem(SystemProperties.getInstance().getResourceBundle().getString("emailForm.menuItemManageTemplateTitle"), KeyEvent.VK_CONTROL);
		menuManageTemplate.setAccelerator(Constans.SHURTCUT_MANAGE_TEMPLATE);
		menuTemplate.add(menuManageTemplate);

		frame.setJMenuBar(menuBar);
		
		panelComposePage = new ComposePagePanel();
		
		panelSentMailPage = new SentMailPagePanel();
		
		panelDraftPage = new DraftPagePanel();
		
		panelTemplatePage = new TemplatePagePanel();
	}
}
