package com.managementservice.projectmanagement.utils;

import com.managementservice.projectmanagement.entity.Progres;
import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.Task;
import com.managementservice.projectmanagement.entity.User;
import org.springframework.security.core.Authentication;

public class TestUtil {

    public static final Long TASK_ID = 0L;
    public static final String TASK_NAME = "Test";
    public static final String TASK_DESCRIPTION = "Test description";
    public static final Integer TASK_VALIDATION = 2;
    public static final String TASK_PROGRESS_IN_PROGRESS = "InProgress";
    public static final String TASK_PROGRESS_DONE = "Done";
    public static final String TASK_EDITED_DESC = "Test description edited";
    public static final String TASK_EDITED_NAME = "TestEdited";

    public static final Progres PROGRESS_TASK = Progres.InProgress;

    public static final Long SPRINT_ID = 0L;
    public static final String SPRINT_NAME = "Test";

    public static final Long USER_ID = 0L;
    public static final String USER_USERNAME = "Username";
    public static final String USER_PASSWORD = "Password";
    public static final String USER_EMAIL = "email@email.com";
    public static final AccountTypeEnum USER_ACCOUNT_TYPE = AccountTypeEnum.NONE;


    public static Sprint getSprint() {
        return Sprint.SprintBuilder.aSprint()
                .withId(SPRINT_ID)
                .withName(SPRINT_NAME)
                .build();
    }

    public static User getUser() {
        return User.UserBuilder.anUser()
                .withId(USER_ID)
                .withUsername(USER_USERNAME)
                .withPassword(USER_PASSWORD)
                .withEmail(USER_EMAIL)
                .withAccountType(USER_ACCOUNT_TYPE)
                .build();
    }

    public static Task getTask() {
        return Task.TaskBuilder.aTask()
                .withName(TASK_NAME)
                .withDescription(TASK_DESCRIPTION)
                .withProgres(PROGRESS_TASK)
                .withId(SPRINT_ID)
                .withTaskValidity(TASK_VALIDATION)
                .withSprint(getSprint())
                .withUser(getUser())
                .build();
    }
}
