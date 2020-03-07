package com.managementservice.projectmanagement.entity;

import javax.persistence.*;

@Entity
public class ParticipationInTheProject {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    private Project project;

    @OneToOne
    private User user;


    public ParticipationInTheProject() {
    }

    public ParticipationInTheProject (Project project, User user){
        this.project = project;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
