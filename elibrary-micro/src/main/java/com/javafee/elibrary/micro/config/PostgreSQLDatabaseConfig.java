package com.javafee.elibrary.micro.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "com.javafee.elibrary.micro.model")
public class PostgreSQLDatabaseConfig {
}
