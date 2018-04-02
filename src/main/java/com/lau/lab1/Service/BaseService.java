package com.lau.lab1.Service;

public abstract class BaseService<T,K>{

    protected Service context;

    public void setContext(Service context) {
        this.context = context;
    }

    public abstract void add(Object ... objects);
    public abstract void remove(K key);
    public abstract void edit(T proba);
    public abstract Iterable<T> getAll();

}
