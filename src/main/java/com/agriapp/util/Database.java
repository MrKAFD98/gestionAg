package com.agriapp.util;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

public class Database {
    private static Connection connection;

    public static Connection getConnection() throws Exception {
        if (connection != null && !connection.isClosed()) return connection;
        Properties props = new Properties();
        try (InputStream in = Database.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) throw new RuntimeException("db.properties not found");
            props.load(in);
        }
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");
        connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public static void closeQuietly(AutoCloseable c) {
        if (c != null) try { c.close(); } catch (Exception ignored) {}
    }
}