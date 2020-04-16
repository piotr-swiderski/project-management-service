package com.managementservice.projectmanagement.utils;

import com.managementservice.projectmanagement.entity.*;
import org.springframework.security.core.Authentication;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
    public static final LocalDate SPRINT_DATE_FROM = LocalDate.of(2020,1, 10);
    public static final LocalDate SPRINT_DATE_TO = LocalDate.of(2020,1, 20);


    public static final Long USER_ID = 0L;
    public static final String USER_USERNAME = "Username";
    public static final String USER_PASSWORD = "Password";
    public static final String USER_EMAIL = "email@email.com";
    public static final AccountTypeEnum USER_ACCOUNT_TYPE = AccountTypeEnum.NONE;

    public static final Long PROJECT_ID = 0L;
    public static final String PROJECT_NAME = "Project Name";
    public static final String PROJECT_DESCRIPTION = "DESC";
    public static final Long TASK_ERRAND_ID = 0L;
    public static final String TASK_ERRAND_TEXT = "Task errand text";


    public static Sprint getSprint() {
        return Sprint.SprintBuilder.aSprint()
                .withId(SPRINT_ID)
                .withName(SPRINT_NAME)
                .withDateTo(SPRINT_DATE_TO)
                .withDateFrom(SPRINT_DATE_FROM)
                .withProject(getProject())
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


    public static Project getProject() {
        Project project = new Project();
        project.setId(PROJECT_ID);
        project.setAdmin(getUser());
        project.setDescription(PROJECT_DESCRIPTION);
        project.setName(PROJECT_NAME);
        project.setUsers(getListOfFourUsers());
        project.addSprints(new Sprint(LocalDate.of(2019,1,10), LocalDate.of(2019,1,20), 100, "name", project));
        project.addSprints(new Sprint(LocalDate.of(2019,2,10), LocalDate.of(2019,2,20), 100, "name", project));
        project.addSprints(new Sprint(LocalDate.of(2019,3,10), LocalDate.of(2019,3,20), 100, "name", project));
        project.addSprints(new Sprint(LocalDate.of(2019,4,10), LocalDate.of(2019,4,20), 100, "name", project));
        return project;
    }

    public static TaskErrand getTaskErrand() {
        TaskErrand taskErrand = new TaskErrand();
        taskErrand.setId(TASK_ERRAND_ID);
        taskErrand.setText(TASK_ERRAND_TEXT);
        taskErrand.setFinished(false);
        return taskErrand;
    }

    public static User getEmptyUser() {
        return User.UserBuilder.anUser()
                .withId(1L)
                .withUsername("user")
                .withPassword("pass")
                .build();
    }

    public static List<User> getListOfFourUsers() {
        return Arrays.asList(getUser(), getUser(), getUser(), getUser());
    }

    public static List<Project> getListOfFourProjects() {
        return Arrays.asList(getProject(), getProject(), getProject(), getProject());
    }

    public static List<TaskErrand> getListOfFourErrands() {
        return Arrays.asList(getTaskErrand(), getTaskErrand(), getTaskErrand(), getTaskErrand());
    }

    public static List<Sprint> getListOfFourSprints() {
        return Arrays.asList(getSprint(), getSprint(), getSprint(), getSprint());
    }

}
