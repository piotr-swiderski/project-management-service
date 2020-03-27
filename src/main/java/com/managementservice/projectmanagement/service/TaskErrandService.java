package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Task;
import com.managementservice.projectmanagement.entity.TaskErrand;
import com.managementservice.projectmanagement.repositorie.TaskErrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskErrandService {

    private TaskErrandRepository taskErrandRepository;
    private TaskService taskService;

    @Autowired
    public TaskErrandService(TaskErrandRepository taskErrandRepository, TaskService taskService) {
        this.taskErrandRepository = taskErrandRepository;
        this.taskService = taskService;
    }

    public void createTaskErrand(String text, String taskId) {
        long parseTaskId = Long.parseLong(taskId);
        Task taskById = taskService.findTaskById(parseTaskId);
        TaskErrand taskErrand = new TaskErrand(text, taskById);
        taskById.addTaskErrand(taskErrand);
        taskErrandRepository.save(taskErrand);
        taskService.update(taskById);
    }

    public List<TaskErrand> getTaskErrandsByTaskId(String taskId) {
        long parseTaskId = Long.parseLong(taskId);
        Task taskById = taskService.findTaskById(parseTaskId);
        return taskErrandRepository.findAllByTask(taskById);
    }
}
