package com.example.scheduleapp.model;

import java.io.Serializable;

public class Cab implements Serializable, EntityModel {

    private String cabNum;
    private String name;
    private boolean favorite;

    public Cab(String cabNum, String name, boolean favorite) {
        this.cabNum = cabNum;
        this.name = name;
        this.favorite = favorite;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCabNum() {
        return cabNum;
    }

    public void setCabNum(String cabNum) {
        this.cabNum = cabNum;
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
        return getCabNum();
    }
}
