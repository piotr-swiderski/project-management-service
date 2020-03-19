package com.managementservice.projectmanagement.entity;

public enum Progres {

    Backlog("BackLog"),
    ToDo("ToDo"),
    InProgress("InProgress"),
    QA("QA"),
    Done("Done");

    private String progress;

    Progres(String progress) {
        this.progress = progress;
    }

    public String getProgress() {
        return progress;
    }
}
