package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SprintRestController {

    private TaskService taskService;

    @Autowired
    public SprintRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "/sprint/pageChange", method = RequestMethod.GET, produces = "application/json")
    public void changeTaskTable(@RequestParam String taskId, @RequestParam String tableName) {
        taskService.changeTaskProgress(taskId, tableName);
    }

}
