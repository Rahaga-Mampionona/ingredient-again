package com.ingredient.rahaga.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DataSource {

    private static final String JDBC_URL  = "jdbc:postgresql://localhost:5432/ingredient_db";
    private static final String USERNAME  = "hei_user";
    private static final String PASSWORD  = "1234";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}