package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.utils.AccountTypeEnum;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.Set;

public interface UserService {

    User registerUser(String username, String password, String email, AccountTypeEnum accountType);

    Optional<User> getUserByUsernameOrEmail(String value);

    User getUserById(long id);

    User getUserFromContext();

    String getUsernameFromAuthentication();

    User getUserByEmail(String email);

    User save(User user);

    Set<Project> getAllProjectByUser(User user);

    User getUserByAuthentication(Authentication authentication);

}
