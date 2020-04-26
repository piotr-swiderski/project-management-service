package com.managementservice.projectmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.managementservice.projectmanagement.entity.Task;
import com.managementservice.projectmanagement.entity.TaskErrand;
import com.managementservice.projectmanagement.service.impl.TaskErrandServiceImpl;
import com.managementservice.projectmanagement.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.List;

import static com.managementservice.projectmanagement.utils.TestUtil.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "admin", password = "pass", authorities = {"USER", "ADMIN"})
@WithUserDetails("admin")
class SprintRestControllerTest extends AbstractControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TaskServiceImpl taskService;

    @MockBean
    TaskErrandServiceImpl taskErrandService;

    @Test
    void changeTaskTable_shouldChangeTaskTableToDone() throws Exception {
        mMockMvc.perform(get("/sprint/tableChange")
                .with(csrf())
                .param("taskId", TASK_ID.toString())
                .param("tableName", TASK_PROGRESS_DONE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();
    }

    @Test
    void createTaskErrand_shouldCreatedErrandAndReturnedErrandList() throws Exception {
        List<TaskErrand> listOfFourErrands = getListOfFourErrands();
        TaskErrand taskErrand = getTaskErrand();
        given(taskErrandService.createTaskErrand(TASK_ERRAND_TEXT, TASK_ID)).willReturn(taskErrand);
        given(taskErrandService.getTaskErrandsByTaskId(TASK_ID)).willReturn(listOfFourErrands);

        mMockMvc.perform(get("/sprint/addTaskErrand")
                .with(csrf())
                .param("taskErrandText", TASK_ERRAND_TEXT)
                .param("taskId", TASK_ID.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().string(objectMapper.writeValueAsString(listOfFourErrands)))
                .andReturn();

    }

    @Test
    void getTaskErrands_shouldReturnedListOfFourErrands() throws Exception {
        List<TaskErrand> listOfFourErrands = getListOfFourErrands();
        given(taskErrandService.getTaskErrandsByTaskId(TASK_ID)).willReturn(listOfFourErrands);

        mMockMvc.perform(get("/sprint/getTaskErrands")
                .with(csrf())
                .param("taskId", TASK_ID.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(listOfFourErrands)))
                .andExpect(jsonPath("$", hasSize(4)))
                .andReturn();
    }

    @Test
    void setErrandChecked_shouldReturnedStatusAccepted() throws Exception {
        TaskErrand taskErrand = getTaskErrand();
        given(taskErrandService.setErrandFinished(TASK_ERRAND_ID, true)).willReturn(taskErrand);

        mMockMvc.perform(get("/sprint/setErrandChecked")
                .with(csrf())
                .param("errandId", TASK_ERRAND_ID.toString())
                .param("checked", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();
    }

    @Test
    void setTaskDescription_shouldModifyDescriptionAndThenReturnedIt() throws Exception {
        Task task = getTask();
        given(taskService.setTaskDescription(TASK_ID, TASK_DESCRIPTION)).willReturn(task);

        mMockMvc.perform(get("/sprint/setTaskDescription")
                .with(csrf())
                .param("taskId", TASK_ID.toString())
                .param("taskDescription", TASK_DESCRIPTION)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().string(objectMapper.writeValueAsString(task)))
                .andReturn();
    }

    @Test
    void setTaskName_shouldModifyTaskNameAndThenReturnedIt() throws Exception {
        Task task = getTask();
        given(taskService.setTaskName(TASK_ID, TASK_NAME)).willReturn(task);

        mMockMvc.perform(get("/sprint/setTaskName")
                .with(csrf())
                .param("taskId", TASK_ID.toString())
                .param("taskName", TASK_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().string(objectMapper.writeValueAsString(task)))
                .andReturn();
    }
}
