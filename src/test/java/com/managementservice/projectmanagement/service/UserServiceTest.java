package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.NoResultException;
import java.util.Optional;

import static com.managementservice.projectmanagement.utils.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void should_register_user() {
        //given
        User user = getUser();
        //when
        when(userRepository.save(any(User.class))).thenReturn(user);
        //then
        User registerUser = userService.registerUser(USER_USERNAME, USER_PASSWORD, USER_EMAIL, USER_ACCOUNT_TYPE);
        assertThat(registerUser).isEqualToIgnoringGivenFields(user, "password", "roles", "permissions");
    }

    @Test
    public void should_get_user_by_username_or_email() {
        //given
        User user = getUser();
        //when
        when(userRepository.findByUsernameOrEmail(USER_USERNAME)).thenReturn(Optional.of(user));
        //then
        Optional<User> userOptional = userService.getUserByUsernameOrEmail(USER_USERNAME);
        assertThat(userOptional.isPresent()).isTrue();
    }

}
