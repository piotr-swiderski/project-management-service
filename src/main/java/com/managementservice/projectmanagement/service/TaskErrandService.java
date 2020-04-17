package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.TaskErrand;

import java.util.List;

public interface TaskErrandService {

    TaskErrand saveErrand(TaskErrand taskErrand);

    TaskErrand createTaskErrand(String text, long taskId);

    List<TaskErrand> getTaskErrandsByTaskId(long taskId);

    TaskErrand setErrandFinished(long errandId, boolean checked);

}
