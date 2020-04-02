package com.managementservice.projectmanagement.repositorie;

import com.managementservice.projectmanagement.entity.Task;
import com.managementservice.projectmanagement.entity.TaskErrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskErrandRepository extends JpaRepository<TaskErrand, Long> {

    List<TaskErrand> findAllByTask(Task task);
}
