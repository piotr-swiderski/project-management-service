package com.managementservice.projectmanagement.entity;

import javax.persistence.*;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String description;

    @OneToOne
    private Sprint sprint;

    private int taskValidity;

    private Progres progres;

    @OneToOne
    private User user;

    public Task() {
    }

    public Task(String name, String description, Sprint sprint, int taskValidity, Progres progres) {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public int getTaskValidity() {
        return taskValidity;
    }

    public void setTaskValidity(int taskValidity) {
        this.taskValidity = taskValidity;
    }

    public Progres getProgres() {
        return progres;
    }

    public void setProgres(Progres progres) {
        this.progres = progres;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
