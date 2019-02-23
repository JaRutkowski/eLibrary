package com.javafee.watchservice;

import java.util.function.Consumer;

import com.javafee.emailform.TabTemplatePageEvent;

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
			try {
				runnable.terminate();
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
