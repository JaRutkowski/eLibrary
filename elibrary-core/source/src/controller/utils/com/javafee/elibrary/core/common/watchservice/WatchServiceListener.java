package com.javafee.elibrary.core.common.watchservice;

import java.util.function.Consumer;

import com.javafee.elibrary.core.emailform.TabTemplatePageEvent;

public class WatchServiceListener implements IWatchService {

	private Thread thread = null;
	private WatchServiceDirectory runnable = null;

	@Override
	public void initialize(TabTemplatePageEvent tabTemplatePageEvent, Consumer<TabTemplatePageEvent> c) {
		runnable = new WatchServiceDirectory();
		runnable.setC(c);
		runnable.setTabTemplatePageEvent(tabTemplatePageEvent);
		thread = new Thread(runnable);
		thread.start();
	}

	@Override
	public void destroy() {
		if (thread != null) {
			thread.interrupt();
			runnable.terminate();
		}
	}

	@Override
	public boolean isRunning() {
		return thread.isAlive();
	}
}
