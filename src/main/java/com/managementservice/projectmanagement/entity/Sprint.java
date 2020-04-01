package com.managementservice.projectmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Sprint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    private String name;

    private LocalDate dateTo;

    private LocalDate dateFrom;

    private int storyPoints;

    @OneToMany
    @JsonIgnore
    private Set<Task> task = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    private Project project;

    public Sprint(LocalDate dateTo, LocalDate dateFrom, int storyPoints, String stringName, Project project) {
        this.dateTo = dateTo;
        this.dateFrom = dateFrom;
        this.storyPoints = storyPoints;
        this.name = stringName;
        this.project = project;
    }

    public Sprint() {
    }

    public long getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate
                                  dateTo) {
        this.dateTo = dateTo;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public int getStoryPoints() {
        return storyPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStoryPoints(int storyPoints) {
        this.storyPoints = storyPoints;
    }

    public Set<Task> getTask() {
        return task;
    }

    public void setTask(Set<Task> task) {
        this.task = task;
    }

    public void addTask(Task task) {
        this.task.add(task);
    }

    public void setId(long id) {
        Id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
