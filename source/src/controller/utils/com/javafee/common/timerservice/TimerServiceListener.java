package com.javafee.common.timerservice;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

import com.javafee.common.Constants;

public class TimerServiceListener implements ITimerService {

	private Calendar calendar;
	private Timer timer;
	private int currentSecond;

	@Override
	public void initialize(JLabel label) {
		reset();
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (currentSecond == 60)
					reset();
				label.setText(String.format("%s:%02d ", Constants.APPLICATION_TIME_FORMAT.format(calendar.getTime()),
						currentSecond));
				currentSecond++;
			}
		}, 0, 1000);
	}

	@Override
	public void destroy() {
		timer.cancel();
	}

	private void reset() {
		calendar = Calendar.getInstance();
		currentSecond = calendar.get(Calendar.SECOND);
	}

}
