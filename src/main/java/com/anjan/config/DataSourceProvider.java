package com.anjan.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

public class DataSourceProvider {
    private static HikariDataSource dataSource;
    static{
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://localhost:3306/employee_management");
        config.setUsername("root");
        config.setPassword("anjanGemini24");
        config.setMaximumPoolSize(5);

        dataSource = new HikariDataSource(config);
    }

    public static HikariDataSource getDataSource(){
        return dataSource;
    }

    public static void closePool() {
        dataSource.close();
    }
}
