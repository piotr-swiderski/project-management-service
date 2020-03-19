package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.service.SprintService;
import com.managementservice.projectmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/sprint")
    public String getSprintPage(Model model, @RequestParam String sprintId) {


        Long id = Long.parseLong(sprintId);
        Sprint sprintById = sprintService.getSprintById(id);

        model.addAttribute(TASK_LIST, sprintById.getTask());
        model.addAttribute("sprintId", sprintId);

        return SPRINT_PAGE;
    }

    @PostMapping("/sprint")
    public String createTask(Model model,
                             @RequestParam String taskName,
                             @RequestParam String taskDescription,
                             @RequestParam String taskProgress,
                             @RequestParam String sprintId,
                             @RequestParam String taskValidity) {


        long parseSprintId = Long.parseLong(sprintId);
        int parseTaskValidity = Integer.parseInt(taskValidity);
        Sprint sprint = sprintService.getSprintById(parseSprintId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        taskService.createTask(taskName, taskDescription, parseSprintId, parseTaskValidity, taskProgress, user);

        model.addAttribute(TASK_LIST, sprint.getTask());
        model.addAttribute("sprintId", sprintId);
        return SPRINT_PAGE;
    }


}
