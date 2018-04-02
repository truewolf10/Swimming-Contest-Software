package com.lau.lab1.repository;

import com.lau.lab1.DatabaseConnection.DataSource;
import com.lau.lab1.DatabaseConnection.DatabaseException;
import com.lau.lab1.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserRepository extends BaseUserRepository {

    public UserRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void executeInitialize() {
        countStatement = dataSource.getPreparedStatement("SELECT COUNT(*) FROM User WHERE id = ?");
        addStatement = dataSource.getPreparedStatement("INSERT INTO User VALUES (NULL, ?, PASSWORD(?), ?)",
                Statement.RETURN_GENERATED_KEYS);
        updateStatement = dataSource.getPreparedStatement("UPDATE User SET name = ?, password = PASSWORD(?), " +
                "idOficiu = ? WHERE User.id = ?");
        deleteStatement = dataSource.getPreparedStatement("DELETE FROM User WHERE User.id = ?");
        selectStatement = dataSource.getPreparedStatement("SELECT * FROM User");
        countLoginStatement = dataSource.getPreparedStatement("SELECT Count(*) FROM User Where User.name = ? AND" +
                " User.password = PASSWORD(?)");
    }

    @Override
    protected void update(User elem) throws SQLException {
        updateStatement.setString(1, elem.getName());
        updateStatement.setString(2, elem.getPassword());
        updateStatement.setInt(3, elem.getIdOffice());
        updateStatement.setInt(4, elem.getId());
        updateStatement.executeUpdate();
    }

    @Override
    protected void add(User elem) throws SQLException {
        addStatement.setString(1, elem.getName());
        addStatement.setString(2, elem.getPassword());
        addStatement.setInt(3, elem.getIdOffice());
        addStatement.executeUpdate();

        ResultSet rs = addStatement.getGeneratedKeys();
        if (!rs.next())
            throw new DatabaseException("No result from insert!");

        elem.setId(rs.getInt(1));
    }
    @SuppressWarnings("Duplicates")
    @Override
    protected int getCount(User elem) throws SQLException {
        if (elem.getId() == null)
            return 0;
        countStatement.setInt(1, elem.getId());
        ResultSet set = countStatement.executeQuery();
        if (!set.next())
            throw new DatabaseException("No result from count!");
        return set.getInt(1);
    }

    @Override
    protected void executeDelete(Integer key) throws SQLException {
        deleteStatement.setInt(1, key);
        deleteStatement.executeUpdate();
    }

    @Override
    protected Iterable<User> populate(ResultSet set) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        while (set.next())
            users.add(new User(set.getInt("id"), set.getString("name"), null, set.getInt("idOficiu")));
        return users;
    }

    @Override
    protected boolean executeLogin(User user) throws SQLException {
        countLoginStatement.setString(1, user.getName());
        countLoginStatement.setString(2, user.getPassword());
        ResultSet set = countLoginStatement.executeQuery();
        if (!set.next())
            throw new DatabaseException("No result from count!");
        return set.getInt(1) == 1;
    }
}
