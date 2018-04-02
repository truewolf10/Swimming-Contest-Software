package com.lau.lab1.domain;

import java.util.Objects;

public class Proba {
    private Integer id;
    private Style style;
    private Distance distance;

    public Proba(Integer id, Distance distance, Style style) {
        this.id = id;
        this.distance = distance;
        this.style = style;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return style + "-" + distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proba proba = (Proba) o;
        return
                style == proba.style &&
                distance == proba.distance;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, style, distance);
    }
}
