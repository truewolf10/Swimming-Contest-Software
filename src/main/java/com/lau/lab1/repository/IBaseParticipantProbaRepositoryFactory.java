package com.lau.lab1.repository;

import com.lau.lab1.domain.Participant;

public interface IBaseParticipantProbaRepositoryFactory {
    BaseParticipantProbaRepository create(Participant participant);
}
