package com.managementservice.projectmanagement.repositorie;

import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.Task;
import com.managementservice.projectmanagement.entity.TaskErrand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllBySprint(Long sprintId);

    Set<Task> findAllBySprint(Sprint sprint);


}
