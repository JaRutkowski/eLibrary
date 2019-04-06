package com.javafee.common.networkservice;

import java.awt.*;

import javax.swing.ImageIcon;

import com.javafee.common.Common;
import com.javafee.startform.RegistrationPanel;
import com.javafee.tabbedform.Actions;

import lombok.Setter;

public class NetworkConnectionCheckerService implements Runnable {

	@Setter
	private volatile boolean running = true;

	@Setter
	private Actions actions;

	public void terminate() {
		running = false;
	}

	@Override
	public void run() {
		while (running) {
			if (Common.checkInternetConnectivity())
				actions.reloadLblInternetConnectivityStatus(
						new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/sign-check-ico.png"))
								.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
			else
				actions.reloadLblInternetConnectivityStatus(
						new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/sign-error-ico.png"))
								.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		}
	}

}
