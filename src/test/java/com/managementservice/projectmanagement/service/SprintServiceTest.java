package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.SprintRepository;
import com.managementservice.projectmanagement.service.impl.SprintServiceImpl;
import com.managementservice.projectmanagement.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.Optional;

import static com.managementservice.projectmanagement.utils.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class SprintServiceTest {

    @InjectMocks
    SprintServiceImpl sprintService;

    @Mock
    SprintRepository sprintRepository;

    @Mock
    UserServiceImpl userService;

    @Mock
    Authentication authentication;

    @Test
    public void saveSprint_shouldSaveSprint() {
        //given
        Sprint sprint = getSprint();
        given(sprintRepository.save(sprint)).willReturn(sprint);
        //when
        Sprint saveSprint = sprintService.saveSprint(sprint);
        //then
        assertThat(saveSprint).isEqualToComparingFieldByField(sprint);
    }

    @Test
    public void getSprintById_shouldReturnSprintById() {
        //given
        Sprint sprint = getSprint();
        given(sprintRepository.findById(SPRINT_ID)).willReturn(Optional.of(sprint));
        //when
        Sprint sprintById = sprintService.getSprintById(SPRINT_ID);
        //then
        assertThat(sprintById).isEqualToComparingFieldByField(sprint);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getSprintById_shouldReturnNoResultException() {
        //given
        Sprint sprint = getSprint();
        given(sprintRepository.findById(SPRINT_ID)).willReturn(Optional.empty());
        //when
        Sprint sprintById = sprintService.getSprintById(SPRINT_ID);
        //then
        assertThat(sprintById).isEqualToComparingFieldByField(sprint);
    }

    @Test
    public void isUserHaveAccess_shouldCheckIsUserHaveAccessAndReturnTrue() throws NoResultException {
        //given
        User user = getUser();
        Sprint sprint = getSprint();
        given(sprintRepository.findById(SPRINT_ID)).willReturn(Optional.of(sprint));
        given(userService.getUserByAuthentication(any())).willReturn(user);
        //when
        boolean userHaveAccess = sprintService.isUserHaveAccess(authentication, SPRINT_ID);
        //then
        assertThat(userHaveAccess).isTrue();
    }

    @Test
    public void isUserHaveAccess_shouldCheckIsUserHaveAccessAndReturnFalse() throws NoResultException {
        //given
        User user = getEmptyUser();
        Sprint sprint = getSprint();
        given(sprintRepository.findById(SPRINT_ID)).willReturn(Optional.of(sprint));
        given(userService.getUserByAuthentication(any())).willReturn(user);
        //when
        boolean userHaveAccess = sprintService.isUserHaveAccess(authentication, SPRINT_ID);
        //then
        assertThat(userHaveAccess).isFalse();
    }



}
