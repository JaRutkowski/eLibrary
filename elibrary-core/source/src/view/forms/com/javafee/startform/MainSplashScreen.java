package com.javafee.startform;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import com.javafee.common.SystemProperties;
import com.javafee.unicomponent.jlabel.CustomJLabel;

class MainSplashScreen extends JWindow {
	private static final long serialVersionUID = 1L;

	private static MainSplashScreen mainSplashScreen;

	private MainSplashScreen(String filename, final Frame f, int waitTime) {
		super(f);
		JLabel pictureLabel = new CustomJLabel(new ImageIcon(filename));
		getContentPane().add(pictureLabel, BorderLayout.CENTER);
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension labelSize = pictureLabel.getPreferredSize();
		setLocation(screenSize.width / 2 - (labelSize.width / 2), screenSize.height / 2 - (labelSize.height / 2));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setVisible(false);
				dispose();
			}
		});

		final int pause = waitTime;
		final Runnable closerRunner = () -> {
			setVisible(false);
			f.setVisible(true);
			dispose();
		};

		Runnable waitRunner = () -> {
			try {
				setVisible(true);
				SystemProperties.getInstance().initializeSystem();
				Thread.sleep(pause);
				SwingUtilities.invokeAndWait(closerRunner);
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		};

		setVisible(true);
		Thread splashThread = new Thread(waitRunner, "SplashThread");
		splashThread.start();
	}

	public static MainSplashScreen getInstance(String filename, final Frame f, int waitTime) {
		if (mainSplashScreen == null)
			mainSplashScreen = new MainSplashScreen(filename, f, waitTime);

		return mainSplashScreen;
	}

	public static Boolean isNull() {
		return mainSplashScreen == null;
	}
}