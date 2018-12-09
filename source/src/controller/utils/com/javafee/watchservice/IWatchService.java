package com.javafee.watchservice;

import java.util.function.Consumer;

import com.javafee.emailform.TabTemplatePageEvent;

public interface IWatchService {

	public void initialize(TabTemplatePageEvent tabTemplatePageEvent, Consumer<TabTemplatePageEvent> c);

	public void destroy();

}
