package com.lau.lab1.domain;

public enum Distance {
    M50,
    M200,
    M800,
    M1500;

    @Override
    public String toString() {
        return super.toString().substring(1) + "m";
    }
}
