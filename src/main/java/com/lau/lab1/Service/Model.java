package com.lau.lab1.Service;

import com.lau.lab1.domain.Participant;
import com.lau.lab1.domain.Proba;
import com.lau.lab1.domain.User;
import com.lau.lab1.repository.*;

public class Model {
    private final BaseUserRepository userRepository;
    private final BaseParticipantRepository participantRepository;
    private final IBaseParticipantProbaRepositoryFactory participantProbaRepositoryFactory;
    private final BaseProbaRepository probaRepository;

    public BaseUserRepository getUserRepository() {
        return userRepository;
    }

    public BaseParticipantRepository getParticipantRepository() {
        return participantRepository;
    }

    public IBaseParticipantProbaRepositoryFactory getParticipantProbaRepositoryFactory() {
        return participantProbaRepositoryFactory;
    }

    public BaseProbaRepository getProbaRepository() {
        return probaRepository;
    }

    public Model(BaseUserRepository userRepository, BaseParticipantRepository participantRepository, IBaseParticipantProbaRepositoryFactory participantProbaRepositoryFactory, BaseProbaRepository probaRepository) {
        this.userRepository = userRepository;
        this.participantRepository = participantRepository;
        this.participantProbaRepositoryFactory = participantProbaRepositoryFactory;
        this.probaRepository = probaRepository;
    }
}
