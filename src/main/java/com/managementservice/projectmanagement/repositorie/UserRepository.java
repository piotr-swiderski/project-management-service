package com.managementservice.projectmanagement.repositorie;

import com.managementservice.projectmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    @Query("SELECT u From User u where u.username = ?1 or u.email = ?1")
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);

    Optional<User> findByEmail(String email);
}
