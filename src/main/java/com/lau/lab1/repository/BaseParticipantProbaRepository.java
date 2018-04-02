package com.lau.lab1.repository;

import com.lau.lab1.DatabaseConnection.DataSource;
import com.lau.lab1.domain.Participant;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class BaseParticipantProbaRepository extends BaseRepository<Integer,Integer> {
    protected final Participant participant;
    protected PreparedStatement fullDeleteStatement;

    protected BaseParticipantProbaRepository(DataSource dataSource, Participant participant) {
        super(dataSource);
        this.participant = participant;
    }

    protected abstract void executeFullDelete() throws SQLException;
}
