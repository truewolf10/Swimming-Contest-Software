package com.lau.lab1.repository;

import com.lau.lab1.DatabaseConnection.DataSource;
import com.lau.lab1.domain.Participant;

public class ParticipantProbaRepositoryFactory implements IBaseParticipantProbaRepositoryFactory {
    private final DataSource dataSource;

    public ParticipantProbaRepositoryFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public BaseParticipantProbaRepository create(Participant participant) {
        return new ParticipantProbaRepository(dataSource, participant);
    }
}
