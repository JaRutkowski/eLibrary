package com.javafee.elibrary.core.common.watchservice;

import java.util.function.Consumer;

import com.javafee.elibrary.core.emailform.TabTemplatePageEvent;

public interface IWatchService {
	void initialize(TabTemplatePageEvent tabTemplatePageEvent, Consumer<TabTemplatePageEvent> c);

	void destroy();

	boolean isRunning();
}
