package com.ingredient.rahaga.datasource;

public class DataSource {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String JDBC_URL = dotenv.get("JDBC_URL");
    private static final String USERNAME = dotenv.get("USERNAME");
    private static final String PASSWORD = dotenv.get("PASSWORD");
    private static final String JDBC_URL  = "jdbc:postgresql://localhost:5432/mini_dish_db";
    private static final String USERNAME  = "mini_dish_db_manager";
    private static final String PASSWORD  = "123456";

    public Connection getConnection() throws SQLException {
        if (JDBC_URL == null || USERNAME == null || PASSWORD == null) {
            throw new RuntimeException("""
                JDBC_URL, USERNAME et PASSWORD sont obligatoires.
                """);
        }
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}
