package com.managementservice.projectmanagement.security;

import com.managementservice.projectmanagement.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserServiceImpl userService;

    public UserDetailsServiceImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userService.getUserByUsernameOrEmail(username).orElseThrow(NoResultException::new);
    }
}
