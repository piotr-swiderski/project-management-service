package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;
import java.util.Set;

import static com.managementservice.projectmanagement.utils.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;


    @Test
    public void registerUser_shouldRegisterOneUser() {
        //given
        User user = getUser();
        //when
        given(userRepository.save(any(User.class))).willReturn(user);
        given(bCryptPasswordEncoder.encode(USER_PASSWORD)).willReturn(USER_PASSWORD);
        //then
        User registerUser = userService.registerUser(USER_USERNAME, USER_PASSWORD, USER_EMAIL, USER_ACCOUNT_TYPE);
        assertThat(registerUser).isEqualToIgnoringGivenFields(user, "password", "roles", "permissions");
    }

    @Test
    public void getUserByUsernameOrEmail_shouldReturnUserByUsername() {
        //given
        User user = getUser();
        //when
        given(userRepository.findByUsernameOrEmail(USER_USERNAME)).willReturn(Optional.of(user));
        //then
        Optional<User> userOptional = userService.getUserByUsernameOrEmail(USER_USERNAME);
        assertThat(userOptional.isPresent()).isTrue();
    }

    @Test
    public void getUserByUsernameOrEmail_shouldReturnUserByEmail() {
        //given
        User user = getUser();
        //when
        given(userRepository.findByUsernameOrEmail(USER_EMAIL)).willReturn(Optional.of(user));
        //then
        Optional<User> userOptional = userService.getUserByUsernameOrEmail(USER_EMAIL);
        assertThat(userOptional.isPresent()).isTrue();
    }

    @Test
    public void save_shouldSaveNewUser() {
        //given
        User user = getUser();
        given(userRepository.save(user)).willReturn(user);
        //when
        User savedUser = userService.save(user);
        //then
        assertThat(savedUser).isSameAs(user);
    }

    @Test
    public void getUserByAuthentication_shouldReturnUser() {
        //given
        User user = getUser();
        SecurityContextHolder.setContext(securityContext);
        given(authentication.getPrincipal()).willReturn(user);
        given(userRepository.findByUsernameOrEmail(USER_USERNAME)).willReturn(Optional.of(user));
        //when
        User returnedUser = userService.getUserByAuthentication(authentication);
        //then
        assertThat(returnedUser).isSameAs(user);
    }

    @Test
    public void getAllProjectByUser_shouldReturn4Projects() {
        //given
        User user = getUser();
        //when
        Set<Project> returnedProjects = userService.getAllProjectByUser(user);
        //then
        assertThat(returnedProjects).isSameAs(user.getProjects());
    }

    @Test
    public void getUserByEmail_shouldReturnUser(){
        //given
        User user = getUser();
        given(userRepository.findByEmail(USER_EMAIL)).willReturn(Optional.of(user));
        //when
        User returnedUser = userService.getUserByEmail(USER_EMAIL);
        //then
        assertThat(returnedUser).isSameAs(returnedUser);
    }

    @Test
    public void getUserFromContext_shouldReturnUser(){
        //given
        User user = getUser();
        SecurityContextHolder.setContext(securityContext);
        given(SecurityContextHolder.getContext().getAuthentication()).willReturn(authentication);
        given(authentication.getPrincipal()).willReturn(user);
        given(userRepository.findByUsernameOrEmail(USER_USERNAME)).willReturn(Optional.of(user));
        //when
        User returnedUser = userService.getUserFromContext();
        //then
        assertThat(returnedUser).isSameAs(user);
    }

    @Test
    public void getUserById_shouldReturnUserById(){
        //given
        User user = getUser();
        given(userRepository.findById(USER_ID)).willReturn(Optional.of(user));
        //when
        User returnedUser = userService.getUserById(USER_ID);
        //then
        assertThat(returnedUser).isSameAs(user);
    }





}
