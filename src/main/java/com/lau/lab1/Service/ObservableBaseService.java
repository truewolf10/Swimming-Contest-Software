package com.lau.lab1.Service;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;


public class ObservableBaseService<T,K> extends BaseService<T,K>{
    private final BaseService<T,K> target;
    private List<EventHandler<ActionEvent>> eventHandlerList = new ArrayList<>();

    public ObservableBaseService(BaseService<T,K> target) {
        this.target = target;
    }

    @Override
    public void add(Object... objects) {
        target.add(objects);
        triggerEvent();
    }


    @Override
    public void remove(K key) {
        target.remove(key);
        triggerEvent();
    }

    @Override
    public void edit(T proba) {
        target.edit(proba);
        triggerEvent();
    }

    @Override
    public Iterable<T> getAll() {
        return target.getAll();
    }

    @Override
    public void setContext(Service context) {
        target.setContext(context);
    }

    private void triggerEvent() {
        ActionEvent event = new ActionEvent();
        for (EventHandler<ActionEvent> eventHandler : eventHandlerList)
            eventHandler.handle(event);
    }

    public void addEventHandler(EventHandler<ActionEvent> handler){
        eventHandlerList.add(handler);
    }
    public void removeEventHandler(EventHandler<ActionEvent> handler){
        eventHandlerList.remove(handler);
    }

    public BaseService<T, K> getTarget() {
        return target;
    }
}
