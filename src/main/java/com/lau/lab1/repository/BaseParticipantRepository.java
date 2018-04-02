package com.lau.lab1.repository;

import com.lau.lab1.DatabaseConnection.DataSource;
import com.lau.lab1.domain.Participant;

public abstract class BaseParticipantRepository extends BaseRepository<Participant, Integer> {
    protected BaseParticipantRepository(DataSource dataSource) {
        super(dataSource);
    }
}
