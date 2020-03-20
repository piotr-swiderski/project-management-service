package com.managementservice.projectmanagement.repositorie;

import com.managementservice.projectmanagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllBySprint(Long sprintId);



}
