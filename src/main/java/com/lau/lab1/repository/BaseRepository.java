package com.lau.lab1.repository;

import com.lau.lab1.DatabaseConnection.DataSource;
import com.lau.lab1.DatabaseConnection.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public abstract class BaseRepository<T,K> implements IRepository<T,K> {
    protected final DataSource dataSource;
    protected PreparedStatement selectStatement;
    protected PreparedStatement deleteStatement;
    protected PreparedStatement countStatement;
    protected PreparedStatement addStatement;
    protected PreparedStatement updateStatement;

    protected BaseRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected void initializeStatements(){
        if (selectStatement != null)
            return;
        executeInitialize();
    }

    protected abstract void executeInitialize();


    @Override
    public void put(T elem) {
        initializeStatements();
        try {
            if (getCount(elem) == 0)
                add(elem);
            else
                update(elem);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    protected abstract void update(T elem) throws SQLException;

    protected abstract void add(T elem) throws SQLException;

    protected abstract int getCount(T elem) throws SQLException;

    @Override
    public void remove(K key) {
        initializeStatements();
        try {
            executeDelete(key);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    protected abstract void executeDelete(K key) throws SQLException;

    @Override
    public Iterable<T> getAll() {
        initializeStatements();
        try {
            ResultSet set = selectStatement.executeQuery();
            return populate(set);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    protected abstract Iterable<T> populate(ResultSet set) throws SQLException;

    @Override
    public Iterator<T> iterator() {
        return getAll().iterator();
    }

    @Override
    public void forEach(Consumer<? super T> consumer) {
        getAll().forEach(consumer);
    }

    @Override
    public Spliterator<T> spliterator() {
        return getAll().spliterator();
    }
}
