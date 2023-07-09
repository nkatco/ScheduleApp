package com.example.scheduleapp.model;

import java.io.Serializable;

public class Teacher implements Serializable, EntityModel {

    private int id;
    private String name;
    private boolean favorite;

    public Teacher(int id, String name, boolean favorite) {
        this.id = id;
        this.name = name;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String getPrimaryName() {
        return getName();
    }

    @Override
    public String getSearchId() {
        return String.valueOf(getId());
    }
}
