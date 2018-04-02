package com.lau.lab1.repository;

import com.lau.lab1.DatabaseConnection.DataSource;
import com.lau.lab1.DatabaseConnection.DatabaseException;
import com.lau.lab1.domain.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class BaseUserRepository extends BaseRepository<User, Integer> {
    protected PreparedStatement countLoginStatement;

    protected BaseUserRepository(DataSource dataSource) {
        super(dataSource);

    }

    public boolean login (User user) {
        initializeStatements();
        try {
            return executeLogin(user);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    protected abstract boolean executeLogin(User user) throws SQLException;
}
