package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.entity.Task;
import com.managementservice.projectmanagement.entity.TaskErrand;
import com.managementservice.projectmanagement.service.TaskErrandService;
import com.managementservice.projectmanagement.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class SprintRestController {

    private TaskService taskService;
    private TaskErrandService taskErrandService;

    @Autowired
    public SprintRestController(TaskService taskService, TaskErrandService taskErrandService) {
        this.taskService = taskService;
        this.taskErrandService = taskErrandService;
    }

    @GetMapping(value = "/sprint/tableChange", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changeTaskTable(@RequestParam long taskId, @RequestParam String tableName) {
        taskService.changeTaskProgress(taskId, tableName);
    }

    @GetMapping(value = "/sprint/addTaskErrand", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TaskErrand> createTaskErrand(@RequestParam String taskErrandText, @RequestParam long taskId) {
        log.info("Add errand {taskId : " + taskId + ", text : " + taskErrandText + " }");
        taskErrandService.createTaskErrand(taskErrandText, taskId);
        return taskErrandService.getTaskErrandsByTaskId(taskId);
    }

    @GetMapping(value = "/sprint/getTaskErrands", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskErrand> getTaskErrands(@RequestParam long taskId) {
        log.info("Get errands {taskId : " + taskId + " }");
        return taskErrandService.getTaskErrandsByTaskId(taskId);
    }

    @GetMapping(value = "/sprint/setErrandChecked", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void setErrandChecked(@RequestParam long errandId, @RequestParam boolean checked) {
        log.info("Set value of progress errands {id : " + errandId + ", checked : " + checked + " }");
        taskErrandService.setErrandFinished(errandId, checked);
    }

    @GetMapping(value = "/sprint/setTaskDescription", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Task setTaskDescription(@RequestParam long taskId, @RequestParam String taskDescription) {
        return taskService.setTaskDescription(taskId, taskDescription);
    }

    @GetMapping(value = "/sprint/setTaskName", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Task setTaskName(@RequestParam long taskId, @RequestParam String taskName) {
        return taskService.setTaskName(taskId, taskName);
    }
}
