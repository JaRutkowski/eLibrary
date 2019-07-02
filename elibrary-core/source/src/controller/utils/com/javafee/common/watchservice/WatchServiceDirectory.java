package com.javafee.common.watchservice;

import com.javafee.common.Constants;
import com.javafee.emailform.TabTemplatePageEvent;

import com.javafee.startform.LogInEvent;
import com.javafee.hibernate.dao.common.Common;
import com.javafee.hibernate.dto.common.SystemProperties;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;
import java.util.function.Consumer;

public class WatchServiceDirectory implements Runnable {

	@Setter
	private volatile boolean running = true;

	@Setter
	private TabTemplatePageEvent tabTemplatePageEvent;
	@Setter
	private Consumer<TabTemplatePageEvent> c;

	public void terminate() {
		running = false;
	}

	@Override
	public void run() {
		while (running) {
			try {
				Optional<SystemProperties> systemProperties = Common
						.findSystemPropertiesByUserDataId(
								LogInEvent.getWorker() != null ? LogInEvent.getWorker().getIdUserData()
										: Constants.DATA_BASE_ADMIN_ID);
				if (systemProperties.isPresent()) {
					WatchService watchService;
					watchService = FileSystems.getDefault().newWatchService();

					Path path = Paths.get(systemProperties.get().getTemplateDirectory());

					path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
							StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

					WatchKey key;
					try {
						while ((key = watchService.take()) != null) {
							key.pollEvents().get(0);
							c.accept(tabTemplatePageEvent);
							key.reset();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
