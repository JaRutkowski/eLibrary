package com.javafee.common.watchservice;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Optional;
import java.util.function.Consumer;

import com.javafee.common.Constans;
import com.javafee.emailform.TabTemplatePageEvent;
import com.javafee.hibernate.dto.common.SystemProperties;
import com.javafee.startform.LogInEvent;

import lombok.Setter;

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
				Optional<SystemProperties> systemProperties = com.javafee.hibernate.dao.common.Common
						.findSystemPropertiesByUserDataId(
								LogInEvent.getWorker() != null ? LogInEvent.getWorker().getIdUserData()
										: Constans.DATA_BASE_ADMIN_ID);
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
