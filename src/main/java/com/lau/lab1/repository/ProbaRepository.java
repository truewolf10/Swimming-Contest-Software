package com.lau.lab1.repository;

import com.lau.lab1.DatabaseConnection.DataSource;
import com.lau.lab1.DatabaseConnection.DatabaseException;
import com.lau.lab1.domain.Distance;
import com.lau.lab1.domain.Proba;
import com.lau.lab1.domain.Style;
import com.lau.lab1.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProbaRepository extends BaseProbaRepository {

    public ProbaRepository(DataSource dataSource) {
        super(dataSource);
         }

    @Override
    protected void executeInitialize() {
        updateStatement = dataSource.getPreparedStatement("UPDATE Proba SET distanta = ?, stilul = ?");
        addStatement = dataSource.getPreparedStatement("INSERT INTO Proba VALUE (NULL ,?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        countStatement = dataSource.getPreparedStatement("SELECT COUNT(*) FROM Proba WHERE id = ?");
        deleteStatement = dataSource.getPreparedStatement("DELETE FROM Proba WHERE id = ?");
        selectStatement = dataSource.getPreparedStatement("SELECT * FROM Proba");
    }

    @Override
    protected void update(Proba elem) throws SQLException {
        updateStatement.setInt(1,elem.getDistance().ordinal());
        updateStatement.setInt(2,elem.getStyle().ordinal());
        updateStatement.executeUpdate();
    }

    @Override
    protected void add(Proba elem) throws SQLException {
        addStatement.setInt(1, elem.getDistance().ordinal());
        addStatement.setInt(2, elem.getStyle().ordinal());
        addStatement.executeUpdate();

        ResultSet rs = addStatement.getGeneratedKeys();
        if (!rs.next())
            throw new DatabaseException("No result from insert!");

        elem.setId(rs.getInt(1));
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected int getCount(Proba elem) throws SQLException {
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
    protected Iterable<Proba> populate(ResultSet set) throws SQLException {
        ArrayList<Proba> users = new ArrayList<>();
        while (set.next())
            users.add(new Proba(set.getInt("id"),
                    Distance.values()[set.getInt("distanta")],
                    Style.values()[set.getInt("stilul")]));
        return users;
    }
}
