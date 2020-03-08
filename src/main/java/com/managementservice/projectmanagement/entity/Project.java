package com.managementservice.projectmanagement.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;


    @OneToOne
    private User admin;


    @ManyToMany(mappedBy = "projects")
    private Set<User> users = new HashSet<>();

    public Project() {
    }

    public Project(String name, String description, User admin) {
        this.name = name;
        this.description = description;
        this.admin = admin;
    }

    public Project(String name, String description, User admin,  Set<User> users) {
        this.name = name;
        this.description = description;
        this.admin = admin;
        this.users = users;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public long getId() {
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


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
