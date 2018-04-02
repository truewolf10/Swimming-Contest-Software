package com.lau.lab1.Service;

import com.lau.lab1.domain.Distance;
import com.lau.lab1.domain.Proba;
import com.lau.lab1.domain.Style;

public class ProbeService extends BaseService<Proba, Integer> {
    @Override
    public void add(Object... objects) {
        if (objects.length!=2 || !(objects[0] instanceof Distance) || !(objects[1] instanceof Style))
            throw new IllegalArgumentException();

        Proba proba = new Proba(null, (Distance) objects[0], (Style) objects[1]);
        context.getModel().getProbaRepository().put(proba);
    }

    @Override
    public void remove(Integer key) {
        context.getModel().getProbaRepository().remove(key);
    }

    @Override
    public void edit(Proba proba) {
        context.getModel().getProbaRepository().put(proba);
    }

    @Override
    public Iterable<Proba> getAll() {
        return context.getModel().getProbaRepository().getAll();
    }
}
