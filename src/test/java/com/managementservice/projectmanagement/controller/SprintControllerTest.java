package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.Task;
import com.managementservice.projectmanagement.service.impl.SprintServiceImpl;
import com.managementservice.projectmanagement.service.impl.TaskServiceImpl;
import com.managementservice.projectmanagement.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

import static com.managementservice.projectmanagement.utils.ControllerUtil.*;
import static com.managementservice.projectmanagement.utils.TestUtil.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringRunner.class)
//@WebMvcTest(SprintController.class)
@WithMockUser(username = "admin", password = "pass", authorities = {"USER", "ADMIN"})
@WithUserDetails("admin")
class SprintControllerTest extends AbstractControllerTest {

    @MockBean
    SprintServiceImpl sprintService;

    @MockBean
    TaskServiceImpl taskService;

    @MockBean
    UserServiceImpl userService;

    @Test
    public void listStudents_shouldReturnedASprintPage() throws Exception {
        Sprint sprint = getSprint();
        Set<Task> listOfFourTasks = getListOfFourTasks();

        given(sprintService.getSprintById(SPRINT_ID)).willReturn(sprint);
        given(sprintService.isUserHaveAccess(any(Authentication.class), anyLong())).willReturn(true);
        given(taskService.findTasksBySprint(sprint)).willReturn(listOfFourTasks);

        mMockMvc
                .perform(get("/sprint/{sprintId}", SPRINT_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute(TASK_LIST, hasSize(4)))
                .andExpect(model().attribute(SPRINT_HANDLER, is(sprint)))
                .andExpect(view().name("sprintPage"))
                .andReturn();
    }

    @Test
    public void listStudents_shouldReturnedErrorPageBecauseUserNotAccess() throws Exception {
        Sprint sprint = getSprint();
        Set<Task> listOfFourTasks = getListOfFourTasks();

        given(sprintService.getSprintById(SPRINT_ID)).willReturn(sprint);
        given(sprintService.isUserHaveAccess(any(Authentication.class), anyLong())).willReturn(false);
        given(taskService.findTasksBySprint(sprint)).willReturn(listOfFourTasks);

        mMockMvc
                .perform(get("/sprint/{sprintId}", SPRINT_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute(ERROR_HANDLER, ERROR_MSG_ACCESS))
                .andExpect(model().attribute(ERROR_HELP_HANDLER, ERROR_MSG_HELP_ACCESS))
                .andReturn();
    }

    @Test
    public void listStudents_shouldReturnedErrorPageBecauseSprintNotFound() throws Exception {
        Sprint sprint = getSprint();

        given(sprintService.getSprintById(SPRINT_ID)).willThrow(EntityNotFoundException.class);

        mMockMvc
                .perform(get("/sprint/{sprintId}", SPRINT_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("error-404"))
                .andReturn();
    }

    @Test
    public void createTask_shouldCreatedTaskAndReturnedSprintPage() throws Exception {
        Sprint sprint = getSprint();
        Set<Task> listOfFourTasks = getListOfFourTasks();
        Task task = getTask();

        given(taskService
                .createTask(eq(TASK_NAME),
                        eq(TASK_DESCRIPTION),
                        eq(SPRINT_ID),
                        eq(TASK_VALIDATION),
                        eq(TASK_PROGRESS_DONE),
                        any(Authentication.class)
                )).willReturn(task);
        given(sprintService.getSprintById(SPRINT_ID)).willReturn(sprint);
        given(taskService.findTasksBySprint(sprint)).willReturn(listOfFourTasks);

        mMockMvc
                .perform(
                        post("/sprint/{sprintId}", SPRINT_ID)
                                .param("taskName", TASK_NAME)
                                .param("taskDescription", TASK_DESCRIPTION)
                                .param("taskProgress", TASK_PROGRESS_DONE)
                                .param("taskValidity", TASK_VALIDATION.toString())
                                .with(csrf().asHeader())
                )
                .andExpect(model().attribute(TASK_LIST, hasSize(4)))
                .andExpect(model().attribute(SPRINT_HANDLER, is(sprint)))
                .andExpect(view().name("sprintPage"))
                .andExpect(status().isOk())
                .andReturn();
    }

}
