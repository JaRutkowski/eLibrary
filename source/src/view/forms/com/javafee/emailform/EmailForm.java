package com.javafee.emailform;

import java.awt.Toolkit;

import javax.swing.JFrame;

import com.javafee.common.Constans;
import com.javafee.common.Utils;
import com.javafee.startform.StartForm;
import com.javafee.tabbedform.emails.CreatePagePanel;
import com.javafee.tabbedform.emails.SendedPagePanel;
import com.javafee.tabbedform.emails.TemplatePagePanel;
import com.javafee.tabbedform.emails.WorkingCopyPagePanel;

import lombok.Getter;

import java.awt.GridBagLayout;
import javax.swing.JTabbedPane;
import java.awt.GridBagConstraints;

public class EmailForm {
	@Getter
	private JFrame frame;
	
	@Getter
	private JTabbedPane tabbedPane;
	@Getter
	private CreatePagePanel panelCreatePage;
	@Getter
	private SendedPagePanel panelSendedPage;
	@Getter
	private WorkingCopyPagePanel panelWorkingCopyPage;
	@Getter
	private TemplatePagePanel panelTemplatePage;

	// EmailForm window = new EmailForm();
	// window.frame.setVisible(true);

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
		
		panelCreatePage = new CreatePagePanel();
		
		panelSendedPage = new SendedPagePanel();
		
		panelWorkingCopyPage = new WorkingCopyPagePanel();
		
		panelTemplatePage = new TemplatePagePanel();
	}
}
