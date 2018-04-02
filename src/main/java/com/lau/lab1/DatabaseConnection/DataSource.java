package com.lau.lab1.DatabaseConnection;

import com.lau.lab1.ApplicationConfiguration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataSource {
    private Connection connection;
    public DataSource() {
        connection = null;
    }

    private void create() {
        try {
            connection = DriverManager.getConnection(ApplicationConfiguration.getInstace().getDatabaseServer(),
                    ApplicationConfiguration.getInstace().getDatabaseUser(),
                    ApplicationConfiguration.getInstace().getDatabasePassword());
        } catch (IOException ex) {
            throw new DatabaseException("Configuration not set");
        } catch (SQLException ex) {
            throw new DatabaseException("Could not connect to the database");
        }
    }

    public Connection getConnection() {
        if (connection == null)
            create();
        return connection;
    }

    public PreparedStatement getPreparedStatement(String statement) {
        try {
            return getConnection().prepareStatement(statement);
        } catch (SQLException e) {
            throw new DatabaseException("Could not prepare statement:" + statement);
        }
    }

    public PreparedStatement getPreparedStatement(String statement, Integer values) {
        try {
            return getConnection().prepareStatement(statement, values);
        } catch (SQLException e) {
            throw new DatabaseException("Could not prepare statement:" + statement);
        }
    }
}
