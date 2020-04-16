package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Task;
import com.managementservice.projectmanagement.entity.TaskErrand;
import com.managementservice.projectmanagement.repositorie.TaskErrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
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

    public TaskErrand saveErrand(TaskErrand taskErrand) {
        return taskErrandRepository.save(taskErrand);
    }

    public TaskErrand createTaskErrand(String text, long taskId) {
        Task taskById = getTaskById(taskId);
        TaskErrand taskErrand = new TaskErrand(text, taskById);
        taskById.addTaskErrand(taskErrand);

        saveErrand(taskErrand);
        taskService.update(taskById);
        return taskErrand;
    }

    public List<TaskErrand> getTaskErrandsByTaskId(long taskId) {
        Task taskById = taskService.findTaskById(taskId).orElse(null);
        return taskErrandRepository.findAllByTask(taskById);
    }

    public TaskErrand setErrandFinished(long errandId, boolean checked) {
        TaskErrand taskErrand = getTaskErrandById(errandId);
        taskErrand.setFinished(checked);
        return saveErrand(taskErrand);
    }

    private TaskErrand getTaskErrandById(long errandId) {
        return taskErrandRepository.findById(errandId).orElseThrow(NoResultException::new);
    }

    private Task getTaskById(long taskId) {
        return taskService.findTaskById(taskId).orElseThrow(NoResultException::new);
    }
}
