package com.lau.lab1.repository;

import com.lau.lab1.DatabaseConnection.DataSource;
import com.lau.lab1.DatabaseConnection.DatabaseException;
import com.lau.lab1.domain.Participant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParticipantProbaRepository extends BaseParticipantProbaRepository {
    protected ParticipantProbaRepository(DataSource dataSource, Participant participant) {
        super(dataSource, participant);
    }

    @Override
    protected void executeFullDelete() throws SQLException {
        fullDeleteStatement.setInt(1,participant.getId());
        fullDeleteStatement.executeUpdate();
    }

    @Override
    protected void executeInitialize() {
        selectStatement = dataSource.getPreparedStatement("SELECT idProb FROM ParticipantProba WHERE idPart = ?");
        deleteStatement = dataSource.getPreparedStatement("DELETE FROM ParticipantProba WHERE idPart = ? AND idProb = ?");
        fullDeleteStatement = dataSource.getPreparedStatement("DELETE FROM ParticipantProba WHERE idPart = ?");
        addStatement = dataSource.getPreparedStatement("INSERT INTO ParticipantProba VALUES(?,?)");
        countStatement = dataSource.getPreparedStatement("SELECT COUNT(*) FROM ParticipantProba WHERE idPart = ? AND idProb = ?");
    }

    @Override
    protected void update(Integer elem) throws SQLException {
        // Does nothing!
    }

    @Override
    public Iterable<Integer> getAll() {
        initializeStatements();
        try {
            selectStatement.setInt(1, participant.getId());
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return super.getAll();
    }

    @Override
    protected void add(Integer elem) throws SQLException {
        addStatement.setInt(1,participant.getId());
        addStatement.setInt(2,elem);
        addStatement.executeUpdate();
    }

    @Override
    protected int getCount(Integer elem) throws SQLException {
        countStatement.setInt(1,participant.getId());
        countStatement.setInt(2,elem);
        ResultSet set = countStatement.executeQuery();
        if (!set.next())
            throw new DatabaseException("No result from count!");
        return set.getInt(1);
    }

    @Override
    protected void executeDelete(Integer key) throws SQLException {
        if (key == null) {
            executeFullDelete();
            return;
        }
        deleteStatement.setInt(1,participant.getId());
        deleteStatement.setInt(2,key);
        deleteStatement.executeUpdate();
    }

    @Override
    protected Iterable<Integer> populate(ResultSet set) throws SQLException {
        ArrayList<Integer> probeIds = new ArrayList<>();
        while(set.next()){
            probeIds.add(set.getInt(1));
        }
        return probeIds;
    }
}
