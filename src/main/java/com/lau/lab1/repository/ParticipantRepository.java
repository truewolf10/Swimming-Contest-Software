package com.lau.lab1.repository;

import com.lau.lab1.DatabaseConnection.DataSource;
import com.lau.lab1.DatabaseConnection.DatabaseException;
import com.lau.lab1.domain.Participant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ParticipantRepository extends BaseParticipantRepository {

    public ParticipantRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void executeInitialize() {
        updateStatement = dataSource.getPreparedStatement("UPDATE Participant SET name = ?, varsta = ?,idOficiu = ?");
        selectStatement = dataSource.getPreparedStatement("SELECT * FROM Participant");
        addStatement = dataSource.getPreparedStatement("INSERT INTO Participant VALUES(NULL,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
        countStatement = dataSource.getPreparedStatement("SELECT COUNT(*) FROM Participant WHERE id = ?");
        deleteStatement = dataSource.getPreparedStatement("DELETE FROM Participant WHERE id = ?");
    }

    @Override
    protected void update(Participant elem) throws SQLException {
        updateStatement.setString(1,elem.getNume());
        updateStatement.setInt(2,elem.getVarsta());
        updateStatement.setInt(3,elem.getIdOficiu());
        updateStatement.executeUpdate();
    }

    @Override
    protected void add(Participant elem) throws SQLException {
        addStatement.setString(1,elem.getNume());
        addStatement.setInt(2,elem.getVarsta());
        addStatement.setInt(3,elem.getIdOficiu());
        addStatement.executeUpdate();

        ResultSet rs = addStatement.getGeneratedKeys();
        if (!rs.next())
            throw new DatabaseException("No result from insert!");

        elem.setId(rs.getInt(1));
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected int getCount(Participant elem) throws SQLException {
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
        deleteStatement.setInt(1,key);
        deleteStatement.executeUpdate();
    }

    @Override
    protected Iterable<Participant> populate(ResultSet set) throws SQLException {
        ArrayList<Participant> participants = new ArrayList<>();
        while (set.next()){
            participants.add(new Participant(set.getInt("id"),
                    set.getString("name"),
                    set.getInt("varsta"),
                    set.getInt("idOficiu"), new ArrayList<>()));
        }
        return participants;
    }
}
