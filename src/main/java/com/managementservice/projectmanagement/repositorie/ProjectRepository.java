package com.managementservice.projectmanagement.repositorie;

import com.managementservice.projectmanagement.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {


}
