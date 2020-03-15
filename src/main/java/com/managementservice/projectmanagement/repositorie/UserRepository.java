package com.managementservice.projectmanagement.repositorie;

import com.managementservice.projectmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);


    Optional<User> findById(Long id);

    Optional<User> findByUsernameOrEmail(String username, String email);
}
