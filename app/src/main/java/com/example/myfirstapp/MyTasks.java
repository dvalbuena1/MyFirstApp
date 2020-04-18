package com.example.myfirstapp;

public class MyTasks {

    String titleTask;
    Boolean check;

    public MyTasks() {
    }

    public MyTasks(String titleTask, Boolean check) {
        this.titleTask = titleTask;
        this.check = check;
    }

    public String getTitleTask() {
        return titleTask;
    }

    public void setTitleTask(String titleTask) {
        this.titleTask = titleTask;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
