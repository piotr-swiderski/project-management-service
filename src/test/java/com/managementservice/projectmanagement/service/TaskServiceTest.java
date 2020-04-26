package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.Task;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.TaskRepository;
import com.managementservice.projectmanagement.service.impl.SprintServiceImpl;
import com.managementservice.projectmanagement.service.impl.TaskServiceImpl;
import com.managementservice.projectmanagement.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import java.util.*;

import static com.managementservice.projectmanagement.utils.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @InjectMocks
    TaskServiceImpl taskService;

    @Mock
    TaskRepository taskRepository;

    @Mock
    SprintServiceImpl sprintService;

    @Mock
    UserServiceImpl userService;

    @Mock
    Authentication authentication;


    @Test
    public void saveTask_shouldSaveTaskAndReturnIt() {
        //given
        Task task = getTask();
        given(taskRepository.save(any(Task.class))).willReturn(task);
        //when
        Task savedTask = taskService.saveTask(task);
        //then
        assertThat(savedTask.getName()).isSameAs(task.getName());

    }

    @Test
    public void createTask_shouldCreateAndReturnedTask() {
        //given
        Task task = getTask();
        User user = getUser();
        Sprint sprint = getSprint();
        given(sprintService.getSprintById(any())).willReturn(sprint);
        given(userService.getUserByAuthentication(any())).willReturn(user);
        //when
        Task returnedTask = taskService.createTask(TASK_NAME, TASK_DESCRIPTION, SPRINT_ID, TASK_VALIDATION, TASK_PROGRESS_IN_PROGRESS, authentication);
        //then
        assertThat(returnedTask).isEqualToComparingOnlyGivenFields(task);
    }

    @Test
    public void findAllTaskBySprintId_shouldReturnFourTask() {
        //given
        Task task = getTask();
        List<Task> taskList = Arrays.asList(task, task, task, task);
        given(taskRepository.findAllBySprint(SPRINT_ID)).willReturn(taskList);
        //when
        List<Task> allTaskBySprintId = taskService.findAllTaskBySprintId(SPRINT_ID);
        //then
        assertThat(allTaskBySprintId.size()).isEqualTo(taskList.size());
    }

    @Test
    public void changeTaskProgress_shouldChangeProgressTaskToDone() {
        //given
        Task task = getTask();
        given(taskService.findTaskById(TASK_ID)).willReturn(Optional.of(task));
        //when
        Task returnedTask = taskService.changeTaskProgress(TASK_ID, TASK_PROGRESS_DONE);
        //then
        assertThat(returnedTask.getProgres().name()).isSameAs(TASK_PROGRESS_DONE);

    }

    @Test
    public void findTaskById_shouldReturnTask() {
        //given
        Task task = getTask();
        given(taskService.findTaskById(TASK_ID)).willReturn(Optional.of(task));
        //when
        Optional<Task> taskById = taskService.findTaskById(TASK_ID);
        //then
        assertThat(taskById.get()).isSameAs(task);
    }

    @Test
    public void findTasksBySprint_shouldReturnTaskBySprintObject() {
        //given
        Task task = getTask();
        Sprint sprint = getSprint();
        Set<Task> taskSet = new HashSet<>(Arrays.asList(task, task));
        given(taskRepository.findAllBySprint(sprint)).willReturn(taskSet);
        //when
        Set<Task> tasksBySprint = taskService.findTasksBySprint(sprint);
        //then
        assertThat(tasksBySprint.size()).isEqualTo(taskSet.size());
    }

    @Test
    public void update_shouldUpdateTask() {
        //given
        Task task = getTask();
        given(taskRepository.save(task)).willReturn(task);
        //when
        Task updateTask = taskService.update(task);
        //then
        assertThat(updateTask).isEqualToComparingFieldByField(task);
    }

    @Test
    public void setTaskDescription_shouldSetDescriptionOfTask() {
        //given
        Task task = getTask();
        given(taskService.findTaskById(TASK_ID)).willReturn(Optional.of(task));
        given(taskService.update(any())).willReturn(task);
        //when
        Task editedTaskDesc = taskService.setTaskDescription(TASK_ID, TASK_EDITED_DESC);
        //then
        assertThat(editedTaskDesc.getDescription()).isEqualTo(TASK_EDITED_DESC);
    }

    @Test
    public void setTaskName_shouldSetNameOfTask() {
        //given
        Task task = getTask();
        given(taskService.findTaskById(TASK_ID)).willReturn(Optional.of(task));
        given(taskService.update(any())).willReturn(task);
        //when
        Task editedTaskName = taskService.setTaskName(TASK_ID, TASK_EDITED_NAME);
        //then
        assertThat(editedTaskName.getName()).isEqualTo(TASK_EDITED_NAME);
    }
}
