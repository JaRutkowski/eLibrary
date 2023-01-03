package com.javafee.elibrary.micro.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommandRunnerConfig implements CommandLineRunner {
	private final DataSource dataSource;

	@Value("${spring.datasource.url}")
	private String connectionString;

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Override
	public void run(String... args) throws Exception {
		logDBConnectionInfo();
	}

	private void logDBConnectionInfo() throws Exception {
		log.info("elibrary-micro startup, connected to " + connectionString + " as "
				+ dataSource.getConnection().getMetaData().getUserName()
				+ ", profile: " + activeProfile);
	}
}
