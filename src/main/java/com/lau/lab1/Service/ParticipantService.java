package com.lau.lab1.Service;

import com.lau.lab1.domain.Distance;
import com.lau.lab1.domain.Participant;
import com.lau.lab1.domain.Proba;
import com.lau.lab1.domain.Style;
import com.lau.lab1.repository.BaseParticipantProbaRepository;

import java.util.ArrayList;
import java.util.List;

public class ParticipantService extends BaseService<Participant, Integer> {
    @SuppressWarnings("unchecked")
    @Override
    public void add(Object... objects) {
        if (objects.length!=3 || !(objects[0] instanceof String) || !(objects[1] instanceof Integer) || (!(objects[2] instanceof List)))
            throw new IllegalArgumentException();

        List<Proba> probaList = (List) objects[2];
        Participant participant = new Participant(null, (String)objects[0],(Integer) objects[1],1, getProbeWithIds(probaList));
        updateParticipantProbe(participant);
    }

    private List<Proba> getProbeWithIds(List<Proba> probaList) {

        Iterable<Proba> allProbe = context.getModel().getProbaRepository().getAll();
        ArrayList<Proba> probaArrayList = new ArrayList<>();


        for(Proba existingProba : probaList) {
            for (Proba proba1 : allProbe) {
                if (existingProba.equals(proba1)) {
                    probaArrayList.add(proba1);
                    break;
                }
            }
        }
        return probaArrayList;
    }

    @Override
    public void remove(Integer key) {
        context.getModel().getParticipantRepository().remove(key);
    }

    @Override
    public void edit(Participant participant) {
        updateParticipantProbe(participant);
    }

    private void updateParticipantProbe(Participant participant){
        context.getModel().getParticipantRepository().put(participant);
        BaseParticipantProbaRepository repository = context.getModel().getParticipantProbaRepositoryFactory().create(participant);
        repository.remove(null);
        for (Proba proba : participant.getProbe()){
            repository.put(proba.getId());
        }
    }

    @Override
    public Iterable<Participant> getAll() {
        Iterable<Proba> probe = context.getModel().getProbaRepository().getAll();
        Iterable<Participant> participants = context.getModel().getParticipantRepository().getAll();
        for (Participant participant : participants) {
            BaseParticipantProbaRepository repository = context.getModel().
                    getParticipantProbaRepositoryFactory().
                    create(participant);
            for (Integer probaId : repository)
                for (Proba proba : probe)
                    if (proba.getId().equals(probaId)) {
                        participant.getProbe().add(proba);
                        break;
                    }
        }
        return participants;
    }

}
