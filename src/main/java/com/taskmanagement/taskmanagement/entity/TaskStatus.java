package com.taskmanagement.taskmanagement.entity;

public enum TaskStatus {

    PENDING("PENDING"),
    DONE("DONE"),
    IN_PROGRESS("IN_PROGRESS");

    TaskStatus(String name) {
        this.name=name;
    }

    private  String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
