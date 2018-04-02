package com.lau.lab1.domain;

public class User {
    private Integer id;
    private String name;
    private transient String password;
    private int idOffice;

    public User(Integer id, String name, String password, int idOffice) {
        this.name = name;
        this.password = password;
        this.id = id;
        this.idOffice = idOffice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idOffice=" + idOffice +
                '}';
    }

    public int getIdOffice() {
        return idOffice;
    }

    public void setIdOffice(int idOffice) {
        this.idOffice = idOffice;
    }
}
