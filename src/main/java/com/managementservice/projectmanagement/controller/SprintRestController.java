package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.entity.TaskErrand;
import com.managementservice.projectmanagement.service.TaskErrandService;
import com.managementservice.projectmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SprintRestController {

    private TaskService taskService;
    private TaskErrandService taskErrandService;

    @Autowired
    public SprintRestController(TaskService taskService, TaskErrandService taskErrandService) {
        this.taskService = taskService;
        this.taskErrandService = taskErrandService;
    }

    @RequestMapping(value = "/sprint/pageChange", method = RequestMethod.GET, produces = "application/json")
    public void changeTaskTable(@RequestParam String taskId, @RequestParam String tableName) {
        taskService.changeTaskProgress(taskId, tableName);
    }

    @RequestMapping(value = "/sprint/addTaskErrand", method = RequestMethod.GET, produces = "application/json")
    public void createTaskErrand(@RequestParam String taskErrandText, @RequestParam String taskId) {
        taskErrandService.createTaskErrand(taskErrandText, taskId);
    }

    @RequestMapping(value = "/sprint/getTaskErrands", method = RequestMethod.GET, produces = "application/json")
    public List<TaskErrand> getTaskErrands(@RequestParam String taskId) {
        return taskErrandService.getTaskErrandsByTaskId(taskId);
    }

    @RequestMapping(value = "/sprint/setErrandChecked", method = RequestMethod.GET, produces = "application/json")
    public List<TaskErrand>  setErrandChecked(@RequestParam String errandId, @RequestParam boolean checked) {
        return taskErrandService.setErrandFinished(errandId, checked);
    }
}
