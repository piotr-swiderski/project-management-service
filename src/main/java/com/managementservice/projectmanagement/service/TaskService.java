package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Progres;
import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.Task;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void createTask(String name, String description, Long sprintId, int taskValidation, String progress, String username){

        Sprint sprint = sprintService.getSprintById(sprintId);
        Progres progres = Progres.valueOf(progress);
        User user = userService.getUserByUsernameOrEmail(username);

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

    public List<Task> findAllTaskBySprintId(Long sprintId){
        return taskRepository.findAllBySprint(sprintId);
    }



}
