package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.Task;
import com.managementservice.projectmanagement.service.SprintService;
import com.managementservice.projectmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

import static com.managementservice.projectmanagement.utils.ControllerUtil.*;

@Controller
public class SprintController {

    private SprintService sprintService;
    private TaskService taskService;
    private final String SPRINT_PAGE = "sprintPage";
    private final String ERROR_PAGE = "errorPage";

    @Autowired
    public SprintController(SprintService sprintService, TaskService taskService) {
        this.sprintService = sprintService;
        this.taskService = taskService;
    }

    @GetMapping("/sprint/{sprintId}")
    public String getSprintPage(Model model, Authentication authentication, @PathVariable String sprintId) {


        Long id = Long.parseLong(sprintId);
        Sprint sprintById = sprintService.getSprintById(id);
        Set<Task> task = sprintById.getTask();

        boolean userHaveAccess = sprintService.isUserHaveAccess(authentication, id);

        if (!userHaveAccess) {
            model.addAttribute(ERROR_HANDLER, ERROR_MSG_ACCESS);
            model.addAttribute(ERROR_HELP_HANDLER, ERROR_MSG_HELP_ACCESS);

            return ERROR_PAGE;
        }

        model.addAttribute(TASK_LIST, task);
        return SPRINT_PAGE;
    }

    @PostMapping("/sprint/{sprintId}")
    public String createTask(Model model,
                             @PathVariable String sprintId,
                             @RequestParam String taskName,
                             @RequestParam String taskDescription,
                             @RequestParam String taskProgress,
                             @RequestParam String taskValidity,
                             Authentication authentication) {


        long parseSprintId = Long.parseLong(sprintId);
        int parseTaskValidity = Integer.parseInt(taskValidity);
        Sprint sprint = sprintService.getSprintById(parseSprintId);

        taskService.createTask(taskName, taskDescription, parseSprintId, parseTaskValidity, taskProgress, authentication);

        model.addAttribute(TASK_LIST, sprint.getTask());

        return SPRINT_PAGE;
    }


}
