package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.service.SprintService;
import com.managementservice.projectmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.managementservice.projectmanagement.utils.ControllerUtil.TASK_LIST;

@Controller
public class SprintController {

    private SprintService sprintService;
    private TaskService taskService;
    private final String SPRINT_PAGE = "sprintPage";

    @Autowired
    public SprintController(SprintService sprintService, TaskService taskService) {
        this.sprintService = sprintService;
        this.taskService = taskService;
    }

    @GetMapping("/sprint/{sprintId}")
    public String getSprintPage(Model model, @PathVariable String sprintId) {


        Long id = Long.parseLong(sprintId);
        Sprint sprintById = sprintService.getSprintById(id);

        model.addAttribute(TASK_LIST, sprintById.getTask());

        return SPRINT_PAGE;
    }

    @PostMapping("/sprint/{sprintId}")
    public String createTask(Model model,
                             @PathVariable String sprintId,
                             @RequestParam String taskName,
                             @RequestParam String taskDescription,
                             @RequestParam String taskProgress,
                             @RequestParam String taskValidity,
                             Principal principal) {


        long parseSprintId = Long.parseLong(sprintId);
        int parseTaskValidity = Integer.parseInt(taskValidity);
        Sprint sprint = sprintService.getSprintById(parseSprintId);
        String username = principal.getName();

        taskService.createTask(taskName, taskDescription, parseSprintId, parseTaskValidity, taskProgress, username);

        model.addAttribute(TASK_LIST, sprint.getTask());

        return SPRINT_PAGE;
    }


}
