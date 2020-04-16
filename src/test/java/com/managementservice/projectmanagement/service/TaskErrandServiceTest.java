package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Task;
import com.managementservice.projectmanagement.entity.TaskErrand;
import com.managementservice.projectmanagement.repositorie.TaskErrandRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static com.managementservice.projectmanagement.utils.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.floatThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TaskErrandServiceTest {

    @Mock
    TaskErrandRepository taskErrandRepository;
    @Mock
    TaskService taskService;
    @InjectMocks
    TaskErrandService taskErrandService;

    @Test
    public void saveErrand_shouldSaveErrand() {
        //given
        TaskErrand taskErrand = getTaskErrand();
        given(taskErrandRepository.save(taskErrand)).willReturn(taskErrand);
        //when
        TaskErrand savedErrand = taskErrandService.saveErrand(taskErrand);
        //then
        assertThat(savedErrand).isEqualTo(taskErrand);
    }

    @Test
    public void createTaskErrand_shouldCreateTaskErrandAndSaved() {
        //given
        Task task = getTask();
        given(taskService.findTaskById(TASK_ID)).willReturn(Optional.of(task));
        //when
        TaskErrand savedErrand = taskErrandService.createTaskErrand(TASK_ERRAND_TEXT, TASK_ERRAND_ID);
        //then
        assertThat(savedErrand.getText()).isEqualTo(TASK_ERRAND_TEXT);
    }

    @Test
    public void getTaskErrandsByTaskId_shouldReturnListOfErrandsByTaskId() {
        //given
        List<TaskErrand> fourErrands = getListOfFourErrands();
        Task task = getTask();
        given(taskErrandRepository.findAllByTask(task)).willReturn(fourErrands);
        given(taskService.findTaskById(TASK_ID)).willReturn(Optional.of(task));
        //when
        List<TaskErrand> errandsByTaskId = taskErrandService.getTaskErrandsByTaskId(TASK_ID);
        //then
        assertThat(errandsByTaskId.size()).isEqualTo(4);
    }

    @Test
    public void setErrandFinished_shouldChangeErrandStatusToFalse() {
        //given
        List<TaskErrand> listOfFourErrands = getListOfFourErrands();
        TaskErrand taskErrand = getTaskErrand();
        Task task = getTask();
        given(taskErrandRepository.findById(TASK_ERRAND_ID)).willReturn(Optional.of(taskErrand));
        given(taskErrandRepository.save(taskErrand)).willReturn(taskErrand);
        //when
        TaskErrand changedTask = taskErrandService.setErrandFinished(TASK_ERRAND_ID, false);
        //then
        assertThat(changedTask.isFinished()).isFalse();
    }

    @Test
    public void setErrandFinished_shouldChangeErrandStatusToTrue() {
        //given
        List<TaskErrand> listOfFourErrands = getListOfFourErrands();
        TaskErrand taskErrand = getTaskErrand();
        Task task = getTask();
        given(taskErrandRepository.findById(TASK_ERRAND_ID)).willReturn(Optional.of(taskErrand));
        given(taskErrandRepository.save(taskErrand)).willReturn(taskErrand);
        //when
        TaskErrand changedTask = taskErrandService.setErrandFinished(TASK_ERRAND_ID, true);
        //then
        assertThat(changedTask.isFinished()).isTrue();
    }

}
