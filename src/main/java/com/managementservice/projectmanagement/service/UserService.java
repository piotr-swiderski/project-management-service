package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.UserRepository;
import com.managementservice.projectmanagement.utils.AccountTypeEnum;
import com.managementservice.projectmanagement.utils.RoleEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void registerUser(String username, String password, String email, AccountTypeEnum accountType) {
        User user = User.UserBuilder.anUser()
                .withUsername(username)
                .withPassword(bCryptPasswordEncoder.encode(password))
                .withEmail(email)
                .withRoles(RoleEnum.USER.name())
                .withPermissions("write")
                .withAccountType(accountType)
                .build();

        userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    public User getUserByUsernameOrEmail(String value) {
        return userRepository.findByUsernameOrEmail(value, value).orElseThrow(NoResultException::new);
    }

    public Optional<User> getOptionalUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(NoResultException::new);
    }

    public User getUserAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        return userRepository.findByUsername(userName).get();
    }

    public String getUserAuthenticationUserName() {
        return getUserAuthentication().getUsername();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(NoResultException::new);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Set<Project> getAllProjectToUser(User user) {
        return user.getProjects();
    }

    public User getUserByAuthentication(Authentication authentication) {
        String username = getUserNameFromPrincipal(authentication);
        return getUserByUsernameOrEmail(username);
    }

    private String getUserNameFromPrincipal(Authentication authentication) {

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            User user = (User) principal;
            return user.getUsername();
        }

        if (principal instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
            Map<String, Object> attributes = oidcUser.getAttributes();
            return (String) attributes.get("email");
        }

        return "";

    }


}

