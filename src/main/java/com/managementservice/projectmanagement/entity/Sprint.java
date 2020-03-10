package com.managementservice.projectmanagement.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    private LocalDate dateTo;

    private LocalDate dateFrom;

    private int storyPoints;

    @OneToMany
    private Set<Task> task;

    @ManyToOne
    private Project project;

    public Sprint(LocalDate dateTo, LocalDate dateFrom, int storyPoints) {
        this.dateTo = dateTo;
        this.dateFrom = dateFrom;
        this.storyPoints = storyPoints;
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

    public void setStoryPoints(int storyPoints) {
        this.storyPoints = storyPoints;
    }
}
