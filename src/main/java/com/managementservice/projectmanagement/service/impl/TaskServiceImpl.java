package com.managementservice.projectmanagement.service.impl;

import com.managementservice.projectmanagement.entity.Progres;
import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.Task;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.TaskRepository;
import com.managementservice.projectmanagement.service.SprintService;
import com.managementservice.projectmanagement.service.TaskService;
import com.managementservice.projectmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private SprintService sprintService;
    private UserService userService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, SprintServiceImpl sprintService, UserService userService) {
        this.taskRepository = taskRepository;
        this.sprintService = sprintService;
        this.userService = userService;
    }


    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }


    @Override
    public Task createTask(String name, String description, Long sprintId, int taskValidation, String progress, Authentication authentication) {
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

        saveTask(task);
        sprintService.saveSprint(sprint);
        return task;
    }


    @Override
    public List<Task> findAllTaskBySprintId(Long sprintId) {
        return taskRepository.findAllBySprint(sprintId);
    }


    @Override
    public Task changeTaskProgress(long taskId, String tableName) {
        Task task = findTaskById(taskId).orElseThrow(NoResultException::new);
        Progres progress = Progres.valueOf(tableName);
        task.setProgres(progress);
        saveTask(task);
        return task;
    }


    @Override
    public Optional<Task> findTaskById(long id) {
        return taskRepository.findById(id);
    }


    @Override
    public Set<Task> findTasksBySprint(Sprint sprint) {
        return taskRepository.findAllBySprint(sprint);
    }


    @Override
    public Task update(Task task) {
        return taskRepository.save(task);
    }


    @Override
    public Task setTaskDescription(long taskId, String description) {
        Task task = findTaskById(taskId).orElseThrow(NoResultException::new);
        task.setDescription(description);
        return update(task);
    }


    @Override
    public Task setTaskName(long taskId, String taskName) {
        Task task = findTaskById(taskId).orElseThrow(NoResultException::new);
        task.setName(taskName);
        return update(task);
    }
}
