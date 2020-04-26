package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.Task;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TaskService {
    Task saveTask(Task task);

    Task createTask(String name, String description, Long sprintId, int taskValidation, String progress, Authentication authentication);

    List<Task> findAllTaskBySprintId(Long sprintId);

    Task changeTaskProgress(long taskId, String tableName);

    Optional<Task> findTaskById(long id);

    Set<Task> findTasksBySprint(Sprint sprint);

    Task update(Task task);

    Task setTaskDescription(long taskId, String description);

    Task setTaskName(long taskId, String taskName);

}
