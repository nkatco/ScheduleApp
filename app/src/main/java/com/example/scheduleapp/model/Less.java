package com.example.scheduleapp.model;

import java.io.Serializable;

public class Less implements Serializable {

    private String title;
    private int num;
    private String teacherName;
    private String nameGroup;
    private String cab;

    public Less(String title, int num, String teacherName, String nameGroup, String cab) {
        this.title = title;
        this.num = num;
        this.teacherName = teacherName;
        this.nameGroup = nameGroup;
        this.cab = cab;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public String getCab() {
        return cab;
    }

    public void setCab(String cab) {
        this.cab = cab;
    }

}
