package com.lau.lab1.repository;

import com.lau.lab1.DatabaseConnection.DataSource;
import com.lau.lab1.domain.Proba;

public abstract class BaseProbaRepository extends BaseRepository<Proba, Integer> {
    protected BaseProbaRepository(DataSource dataSource) {
        super(dataSource);
    }
}
