package com.managementservice.projectmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    @ManyToOne
    @JsonIgnore
    private Sprint sprint;

    private int taskValidity;

    private Progres progres;

    @ManyToOne
    private User user;

    public Task() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
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


    public static final class TaskBuilder {
        private long id;
        private String name;
        private String description;
        private Sprint sprint;
        private int taskValidity;
        private Progres progres;
        private User user;

        private TaskBuilder() {
        }

        public static TaskBuilder aTask() {
            return new TaskBuilder();
        }

        public TaskBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public TaskBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public TaskBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public TaskBuilder withSprint(Sprint sprint) {
            this.sprint = sprint;
            return this;
        }

        public TaskBuilder withTaskValidity(int taskValidity) {
            this.taskValidity = taskValidity;
            return this;
        }

        public TaskBuilder withProgres(Progres progres) {
            this.progres = progres;
            return this;
        }

        public TaskBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public Task build() {
            Task task = new Task();
            task.setId(id);
            task.setName(name);
            task.setDescription(description);
            task.setSprint(sprint);
            task.setTaskValidity(taskValidity);
            task.setProgres(progres);
            task.setUser(user);
            return task;
        }
    }
}
