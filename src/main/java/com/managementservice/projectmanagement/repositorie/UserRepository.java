package com.managementservice.projectmanagement.repositorie;

import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String email);

}
