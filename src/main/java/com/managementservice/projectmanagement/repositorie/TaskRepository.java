package com.managementservice.projectmanagement.repositorie;

import com.managementservice.projectmanagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {



}
