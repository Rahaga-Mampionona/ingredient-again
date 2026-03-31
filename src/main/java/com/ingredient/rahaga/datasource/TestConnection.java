

package com.ingredient.rahaga.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/ingredient_db";
        String user = "hei_user";
        String password = "1234";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connexion PostgreSQL réussie !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}