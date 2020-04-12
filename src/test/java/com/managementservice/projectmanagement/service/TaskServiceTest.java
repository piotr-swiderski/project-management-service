package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.Task;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.TaskRepository;
import com.managementservice.projectmanagement.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import javax.persistence.NoResultException;
import java.util.*;
import java.util.stream.Collectors;

import static com.managementservice.projectmanagement.utils.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @InjectMocks
    TaskService taskService;

    @Mock
    TaskRepository taskRepository;

    @Mock
    SprintService sprintService;

    @Mock
    UserService userService;

    @Mock
    Authentication authentication;



    @Test
    public void should_save_task_and_return_saved_task() {
        //given
        Task task = getTask();
        //when
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        //then
        Task savedTask = taskService.saveTask(task);
        assertThat(savedTask.getName()).isSameAs(task.getName());

    }

    @Test
    public void should_create_and_returned_task() {
        //given
        Task task = getTask();
        User user = getUser();
        Sprint sprint = getSprint();
        //when
        when(sprintService.getSprintById(any())).thenReturn(sprint);
        when(userService.getUserByAuthentication(any())).thenReturn(user);
        //then
        Task returnedTask = taskService.createTask(TASK_NAME, TASK_DESCRIPTION, SPRINT_ID, TASK_VALIDATION, TASK_PROGRESS_IN_PROGRESS, authentication);
        assertThat(returnedTask).isEqualToComparingOnlyGivenFields(task);
    }

    @Test
    public void should_return_four_task_by_id() {
        //given
        Task task = getTask();
        List<Task> taskList = Arrays.asList(task, task, task, task);
        //when
        when(taskRepository.findAllBySprint(SPRINT_ID)).thenReturn(taskList);
        //then
        List<Task> allTaskBySprintId = taskService.findAllTaskBySprintId(SPRINT_ID);
        assertThat(allTaskBySprintId.size()).isEqualTo(taskList.size());
    }

    @Test
    public void should_change_progress_task() {
        //given
        Task task = getTask();
        //when
        when(taskService.findTaskById(TASK_ID)).thenReturn(Optional.of(task));
        //then
        Task returnedTask = taskService.changeTaskProgress(TASK_ID, TASK_PROGRESS_DONE);
        assertThat(returnedTask.getProgres().name()).isSameAs(TASK_PROGRESS_DONE);

    }

    @Test
    public void should_return_task_by_id() {
        //given
        Task task = getTask();
        //when
        when(taskService.findTaskById(TASK_ID)).thenReturn(Optional.of(task));
        //then
        Optional<Task> taskById = taskService.findTaskById(TASK_ID);
        assertThat(taskById.get()).isSameAs(task);
    }

    @Test
    public void should_return_task_by_sprint() {
        //given
        Task task = getTask();
        Sprint sprint = getSprint();
        Set<Task> taskSet = new HashSet<>(Arrays.asList(task, task));
        //when
        when(taskRepository.findAllBySprint(sprint)).thenReturn(taskSet);
        //then
        Set<Task> tasksBySprint = taskService.findTasksBySprint(sprint);
        assertThat(tasksBySprint.size()).isEqualTo(taskSet.size());
    }

    @Test
    public void should_update_task() {
        //given
        Task task = getTask();
        //when
        when(taskRepository.save(task)).thenReturn(task);
        //then
        Task updateTask = taskService.update(task);
        assertThat(updateTask).isEqualToComparingFieldByField(task);
    }

    @Test
    public void should_set_description_of_task() {
        //given
        Task task = getTask();
        //when
        when(taskService.findTaskById(TASK_ID)).thenReturn(Optional.of(task));
        when(taskService.update(any())).thenReturn(task);
        //then
        Task editedTaskDesc = taskService.setTaskDescription(TASK_ID, TASK_EDITED_DESC);
        assertThat(editedTaskDesc.getDescription()).isEqualTo(TASK_EDITED_DESC);
    }

    @Test
    public void should_set_name_of_task() {
        //given
        Task task = getTask();
        //when
        when(taskService.findTaskById(TASK_ID)).thenReturn(Optional.of(task));
        when(taskService.update(any())).thenReturn(task);
        //then
        Task editedTaskName = taskService.setTaskName(TASK_ID, TASK_EDITED_NAME);
        assertThat(editedTaskName.getName()).isEqualTo(TASK_EDITED_NAME);
    }
}
