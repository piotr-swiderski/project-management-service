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

import javax.persistence.EntityNotFoundException;
import java.util.Set;

import static com.managementservice.projectmanagement.utils.ControllerUtil.*;

@Controller
public class SprintController {

    private SprintService sprintService;
    private TaskService taskService;
    private final String SPRINT_PAGE = "sprintPage";
    private final String ERROR_PAGE = "errorPage";
    private final String PAGE_404 = "error-404";


    @Autowired
    public SprintController(SprintService sprintService, TaskService taskService) {
        this.sprintService = sprintService;
        this.taskService = taskService;
    }

    @GetMapping("/sprint/{sprintId}")
    public String getSprintPage(Model model, Authentication authentication, @PathVariable long sprintId) {
        Sprint sprintById;
        try {
            sprintById = sprintService.getSprintById(sprintId);
        } catch (EntityNotFoundException e) {
            return PAGE_404;
        }

        boolean userHaveAccess = sprintService.isUserHaveAccess(authentication, sprintId);

        if (!userHaveAccess) {
            model.addAttribute(ERROR_HANDLER, ERROR_MSG_ACCESS);
            model.addAttribute(ERROR_HELP_HANDLER, ERROR_MSG_HELP_ACCESS);
            return ERROR_PAGE;
        }

        Set<Task> tasks = taskService.findTasksBySprint(sprintById);

        model.addAttribute(TASK_LIST, tasks);
        model.addAttribute(SPRINT_HANDLER, sprintById);
        return SPRINT_PAGE;
    }

    @PostMapping("/sprint/{sprintId}")
    public String createTask(Model model,
                             @PathVariable long sprintId,
                             @RequestParam String taskName,
                             @RequestParam String taskDescription,
                             @RequestParam String taskProgress,
                             @RequestParam String taskValidity,
                             Authentication authentication) {


        int parseTaskValidity = Integer.parseInt(taskValidity);
        Sprint sprintById = sprintService.getSprintById(sprintId);

        taskService.createTask(taskName, taskDescription, sprintId, parseTaskValidity, taskProgress, authentication);

        Set<Task> tasks = taskService.findTasksBySprint(sprintById);

        model.addAttribute(TASK_LIST, tasks);
        model.addAttribute(SPRINT_HANDLER, sprintById);

        return SPRINT_PAGE;
    }


}
