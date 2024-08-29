package com.task_userservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;

@Configuration
@Order(2)
public class ConnectionConfig {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource oracleConnection() {
        if (url.contains("postgresql")) {
            return DataSourceBuilder.create()
                    .url(url)
                    .username(userName)
                    .password(password)
                    .driverClassName("org.postgresql.Driver")
                    .build();
        } else {
            return DataSourceBuilder.create()
                    .url(url)
                    .username(userName)
                    .password(password)
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .build();
        }
    }
}
