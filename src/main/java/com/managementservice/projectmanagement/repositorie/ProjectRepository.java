package com.managementservice.projectmanagement.repositorie;

import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Set<Project> findAllByAdmin_Username(String string);

    Optional<Project> findById(Long Id);

    List<User> findAllByUsers_(Long id);


}
