package com.javafee.common.watchservice;

import com.javafee.emailform.TabTemplatePageEvent;

import java.util.function.Consumer;

public interface IWatchService {

	void initialize(TabTemplatePageEvent tabTemplatePageEvent, Consumer<TabTemplatePageEvent> c);

	void destroy();

}
