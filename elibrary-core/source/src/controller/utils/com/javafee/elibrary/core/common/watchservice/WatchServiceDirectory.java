package com.javafee.elibrary.core.common.watchservice;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Optional;
import java.util.function.Consumer;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.emailform.TabTemplatePageEvent;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.hibernate.dao.common.Common;
import com.javafee.elibrary.hibernate.dto.common.SystemProperties;

import lombok.Setter;
import lombok.extern.java.Log;

@Log
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
						.findSystemPropertiesByUserAccountId(
								LogInEvent.getWorker() != null ? LogInEvent.getWorker().getIdUserData()
										: Constants.DATA_BASE_ADMIN_ID);
				if (systemProperties.isPresent()) {
					Optional.ofNullable(systemProperties.get().getTemplateDirectory()).orElseThrow();
					WatchService watchService = FileSystems.getDefault().newWatchService();
					Paths.get(systemProperties.get().getTemplateDirectory()).register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
							StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

					WatchKey key;
					try {
						while ((key = watchService.take()) != null) {
							key.pollEvents().get(0);
							c.accept(tabTemplatePageEvent);
							key.reset();
						}
					} catch (InterruptedException e) {
						log.severe(e.getMessage());
					}
				}
			} catch (IOException e) {
				log.severe(e.getMessage());
			}
		}
	}

}
