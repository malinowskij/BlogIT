package com.malinowski.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Created by Jakub on 04.12.2016.
 */
@Configuration
public class DBConfig {

    @Bean
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/blogdb");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("12345678");
        return driverManagerDataSource;
    }
}
