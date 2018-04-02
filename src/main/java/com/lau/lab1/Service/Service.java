package com.lau.lab1.Service;

import com.lau.lab1.domain.Participant;
import com.lau.lab1.domain.Proba;
import com.lau.lab1.domain.User;

import javax.security.auth.login.LoginException;

public class Service {

    private Model model;
    private ObservableBaseService<User, Integer> userService;
    private ObservableBaseService<Participant, Integer> participantService;
    private ObservableBaseService<Proba, Integer> probaService;

    public Service(Model model, ObservableBaseService<User, Integer> userService,
                   ObservableBaseService<Participant, Integer> partipantService,
                   ObservableBaseService<Proba, Integer> probaService) {
        this.model = model;
        userService.setContext(this);
        this.userService = userService;
        this.participantService = partipantService;
        partipantService.setContext(this);
        this.probaService = probaService;
        probaService.setContext(this);
    }


    Model getModel() {
        return model;
    }

    public ObservableBaseService getUserService() {
        return userService;
    }

    public ObservableBaseService<Participant, Integer> getParticipantService() {
        return participantService;
    }

    public ObservableBaseService<Proba, Integer> getProbaService() {
        return probaService;
    }

    public void login(String username, String password) throws LoginException {
        ((UserService)userService.getTarget()).login(username, password);
    }
}
