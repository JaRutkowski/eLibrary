package com.javafee.elibrary.core.common.networkservice;

import com.javafee.elibrary.core.tabbedform.Actions;

public interface INetworkService {
	void initialize(Actions actions);

	void destroy();

	boolean isRunning();
}
