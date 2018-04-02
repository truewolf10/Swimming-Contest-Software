package com.lau.lab1.domain;

import java.util.List;

public class Participant {
    private Integer id;
    private String nume;
    private int varsta;
    private int idOficiu;
    private List<Proba> probe;

    public Participant(Integer id, String nume, int varsta, int idOficiu, List<Proba> probe) {
        this.id = id;
        this.nume = nume;
        this.varsta = varsta;
        this.idOficiu = idOficiu;
        this.probe = probe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public List<Proba> getProbe() {
        return probe;
    }

    public void setProbe(List<Proba> probe) {
        this.probe = probe;
    }

    public int getIdOficiu() {
        return idOficiu;
    }

    public void setIdOficiu(int idOficiu) {
        this.idOficiu = idOficiu;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", varsta=" + varsta +
                ", idOficiu=" + idOficiu +
                ", probe=" + probe +
                '}';
    }
}
