package com.javafee.elibrary.core.common.networkservice;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.tabbedform.Actions;

public class NetworkServiceListener implements INetworkService {

	private NetworkConnectionCheckerService runnable = null;

	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

	@Override
	public void initialize(Actions actions) {
		runnable = new NetworkConnectionCheckerService();
		runnable.setActions(actions);
		scheduler.scheduleAtFixedRate(runnable, Constants.APPLICATION_NETWORK_SERVICE_LISTENER_DURATION,
				Constants.APPLICATION_NETWORK_SERVICE_LISTENER_DURATION, TimeUnit.SECONDS);
	}

	@Override
	public void destroy() {
		runnable.terminate();
		scheduler.shutdown();
	}

	@Override
	public boolean isRunning() {
		return !scheduler.isTerminated();
	}
}
