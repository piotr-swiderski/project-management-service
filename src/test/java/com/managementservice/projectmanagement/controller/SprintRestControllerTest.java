package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.service.TaskErrandService;
import com.managementservice.projectmanagement.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import static com.managementservice.projectmanagement.utils.ControllerUtil.SPRINT_HANDLER;
import static com.managementservice.projectmanagement.utils.ControllerUtil.TASK_LIST;
import static com.managementservice.projectmanagement.utils.TestUtil.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "admin", password = "pass", authorities = {"USER", "ADMIN"})
@WithUserDetails("admin")
class SprintRestControllerTest extends AbstractControllerTest{

    @MockBean
    TaskService taskService;
    @Mock
    TaskErrandService taskErrandService;

    @Test
    void changeTaskTable_shouldChangeTaskTableToDone() throws Exception{

    }

    @Test
    void createTaskErrand() {
    }

    @Test
    void getTaskErrands() {
    }

    @Test
    void setErrandChecked() {
    }

    @Test
    void setTaskDescription() {
    }

    @Test
    void setTaskName() {
    }
}
