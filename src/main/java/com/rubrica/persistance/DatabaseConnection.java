package com.rubrica.persistance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            Properties props = new Properties();

            try (InputStream input = new FileInputStream("credenziali_database.properties")) {
                props.load(input);
                System.out.println("Properties file loaded successfully.");

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.username");
                String password = props.getProperty("db.password");

                System.out.println("Database URL: " + url);
                System.out.println("Database User: " + user);
                System.out.println("Database Password Length: " + (password != null ? password.length() : "null"));

                System.out.println("Attempting database connection...");
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Database connection successful!");

            } catch (FileNotFoundException e) {
                System.out.println("ERROR: File 'credenziali_database.properties' not found!");
            } catch (SQLException e) {
                System.out.println("ERROR: Database connection failed!");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("ERROR: Problem loading properties file!");
                e.printStackTrace();
            }
        }

        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
