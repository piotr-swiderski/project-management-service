package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.entity.TaskErrand;
import com.managementservice.projectmanagement.service.TaskErrandService;
import com.managementservice.projectmanagement.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/sprint/pageChange", produces = "application/json")
    public void changeTaskTable(@RequestParam String taskId, @RequestParam String tableName) {
        taskService.changeTaskProgress(taskId, tableName);
    }

    @GetMapping( value = "/sprint/addTaskErrand", produces = "application/json")
    public List<TaskErrand>  createTaskErrand(@RequestParam String taskErrandText, @RequestParam String taskId) {
        log.info("Add errand {taskId : " + taskId + ", text : " + taskErrandText + " }");
        return taskErrandService.createTaskErrand(taskErrandText, taskId);
    }

    @GetMapping(value = "/sprint/getTaskErrands", produces = "application/json")
    public List<TaskErrand> getTaskErrands(@RequestParam String taskId) {
        log.info("Get errands {taskId : " + taskId + " }");
        return taskErrandService.getTaskErrandsByTaskId(taskId);
    }

    @GetMapping(value = "/sprint/setErrandChecked", produces = "application/json")
    public List<TaskErrand>  setErrandChecked(@RequestParam String errandId, @RequestParam boolean checked) {
        log.info("Set value of progress errands {id : " + errandId + ", checked : " + checked + " }");
        return taskErrandService.setErrandFinished(errandId, checked);
    }
}
