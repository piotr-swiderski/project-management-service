package com.managementservice.projectmanagement.entity;


import javax.persistence.*;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;

    private String subject;
    private String text;
    private Long projectId;
    private boolean status = true;
    private boolean viewed = false;

    @OneToOne
    private User user;

    public Notification() {
    }


    public Notification(String subject, String text, User user, Long projectId) {
        this.subject = subject;
        this.text = text;
        this.user = user;
        this.projectId = projectId;
    }


    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subcject) {
        this.subject = subcject;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
