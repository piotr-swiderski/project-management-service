package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.*;
import com.managementservice.projectmanagement.repositorie.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private SprintService sprintService;
    private UserService userService;

    @Autowired
    public TaskService(TaskRepository taskRepository, SprintService sprintService, UserService userService) {
        this.taskRepository = taskRepository;
        this.sprintService = sprintService;
        this.userService = userService;
    }

    public void createTask(String name, String description, Long sprintId, int taskValidation, String progress, Authentication authentication) {

        Sprint sprint = sprintService.getSprintById(sprintId);
        Progres progres = Progres.valueOf(progress);
        User user = userService.getUserByAuthentication(authentication);

        Task task = Task.TaskBuilder.aTask()
                .withName(name)
                .withDescription(description)
                .withSprint(sprint)
                .withProgres(progres)
                .withTaskValidity(taskValidation)
                .withUser(user)
                .build();
        sprint.addTask(task);

        taskRepository.save(task);
        sprintService.saveSprint(sprint);
    }

    public List<Task> findAllTaskBySprintId(Long sprintId) {
        return taskRepository.findAllBySprint(sprintId);
    }


    public void changeTaskProgress(String taskId, String tableName) {

        long parseTaskId = Long.parseLong(taskId);
        Progres progress = Progres.valueOf(tableName);

        Task task = taskRepository.findById(parseTaskId).orElseThrow(NoResultException::new);
        task.setProgres(progress);
        taskRepository.save(task);
    }

    public Task findTaskById(long id) {
        return taskRepository.findById(id).orElseThrow(NoResultException::new);
    }

    public Set<Task> findTasksBySprint(Sprint sprint){
        return taskRepository.findAllBySprint(sprint);
    }


    public void update(Task task) {
        taskRepository.save(task);
    }
}
