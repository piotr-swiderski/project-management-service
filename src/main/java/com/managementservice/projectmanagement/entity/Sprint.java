package com.managementservice.projectmanagement.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    private Date dateTo;

    private Date dateFrom;

    private int storyPoints;

    @OneToMany
    private Set<Task> task;

    public Sprint(Date dateTo, Date dateFrom, int storyPoints) {
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

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public int getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(int storyPoints) {
        this.storyPoints = storyPoints;
    }
}
