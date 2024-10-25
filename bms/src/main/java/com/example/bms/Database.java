package com.example.bms;

import java.sql.*;

public class Database {

    private static final String url = "jdbc:mysql://localhost:3305/book";
    private static final String user = "root";
    private static final String password = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace(); // or use a logging framework
            return null; // or throw a custom exception
        }
    }
}