package com.javafee.common.watchservice;

import java.util.function.Consumer;

import com.javafee.emailform.TabTemplatePageEvent;

public interface IWatchService {

	void initialize(TabTemplatePageEvent tabTemplatePageEvent, Consumer<TabTemplatePageEvent> c);

	void destroy();

}
