package com.javafee.tabbedform.admworkers.frames;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.javafee.common.Utils;
import com.javafee.tabbedform.admworkers.WorkerDataPanel;
import com.javafee.uniform.CockpitConfirmationPanel;

import lombok.Getter;

public class WorkerAddModFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	@Getter
	private WorkerDataPanel workerDataPanel;
	@Getter
	private CockpitConfirmationPanel cockpitConfirmationPanel;

	public WorkerAddModFrame() {
		setBackground(Utils.getApplicationUserDefineColor());
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(WorkerAddModFrame.class.getResource("/images/splashScreen.jpg")));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		workerDataPanel = new WorkerDataPanel();
		GridBagConstraints gbc_workerDataPanel = new GridBagConstraints();
		gbc_workerDataPanel.gridheight = 2;
		gbc_workerDataPanel.insets = new Insets(0, 0, 5, 0);
		gbc_workerDataPanel.fill = GridBagConstraints.BOTH;
		gbc_workerDataPanel.gridx = 0;
		gbc_workerDataPanel.gridy = 0;
		workerDataPanel.getBtnRegisterNow().setVisible(false);
		contentPane.add(workerDataPanel, gbc_workerDataPanel);

		cockpitConfirmationPanel = new CockpitConfirmationPanel();
		GridBagConstraints gbc_cockpitConfirmationPanel = new GridBagConstraints();
		gbc_cockpitConfirmationPanel.fill = GridBagConstraints.BOTH;
		gbc_cockpitConfirmationPanel.gridx = 0;
		gbc_cockpitConfirmationPanel.gridy = 2;
		contentPane.add(cockpitConfirmationPanel, gbc_cockpitConfirmationPanel);
	}
}