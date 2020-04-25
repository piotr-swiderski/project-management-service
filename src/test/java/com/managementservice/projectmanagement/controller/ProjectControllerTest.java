package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.service.NotificationService;
import com.managementservice.projectmanagement.service.ProjectService;
import com.managementservice.projectmanagement.service.UserService;
import com.managementservice.projectmanagement.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.managementservice.projectmanagement.utils.ControllerUtil.*;
import static com.managementservice.projectmanagement.utils.TestUtil.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "admin", password = "pass", authorities = {"USER", "ADMIN"})
@WithUserDetails("admin")
class ProjectControllerTest extends AbstractControllerTest {

    @MockBean
    ProjectService projectService;
    @MockBean
    NotificationService notificationService;
    @MockBean
    UserService userService;

    @Test
    void myProjectList_shouldReturnedProjectList() throws Exception {
        final Set<Project> projectSet = new HashSet<>(getListOfFourProjects());
        final User user = getUser();
        given(projectService.getAListOfAllUserNameProjects(USER_USERNAME)).willReturn(projectSet);
        given(userService.getUserFromContext()).willReturn(user);

        mMockMvc
                .perform(get("/myProjectList"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("projects", is(projectSet)))
                .andExpect(view().name("myProjectList"))
                .andReturn();
    }

    @Test
    void addUserToProject_shouldReturnedAdditionUsersToProjectPage() throws Exception {
        final Project project = getProject();
        given(projectService.getProjectById(PROJECT_ID)).willReturn(project);

        mMockMvc
                .perform(get("/addUsersToProject")
                        .param("projectId", PROJECT_ID.toString())
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute(PROJECT_HANDLER, is(project)))
                .andExpect(view().name("addUsersToProject"))
                .andReturn();
    }

    @Test
    void addUserToProjectPost_shouldAddedUserToProject() throws Exception {
        final Project project = getProject();
        final User user = getUser();
        given(projectService.getProjectById(PROJECT_ID)).willReturn(project);
        given(userService.getUserByEmail(USER_EMAIL)).willReturn(user);
        given(userService.getUserFromContext()).willReturn(user);

        mMockMvc
                .perform(post("/addUsersToProject")
                        .param("projectId", PROJECT_ID.toString())
                        .param("email", USER_EMAIL)
                        .with(csrf().asHeader())
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute(SUCCSES_ADDING_NOTIFICATION, is(SUCCSES_ADDING_NOTIFICATION_MESSAGE)))
                .andExpect(view().name("addUsersToProject"))
                .andReturn();
    }

    @Test
    void addUserToProjectPost_shouldNotAddUserToProjectBecauseUserIsNotInDb() throws Exception {
        final Project project = getProject();
        given(projectService.getProjectById(PROJECT_ID)).willReturn(project);
        given(userService.getUserByEmail(USER_EMAIL)).willThrow(NoResultException.class);

        mMockMvc
                .perform(post("/addUsersToProject")
                        .param("projectId", PROJECT_ID.toString())
                        .param("email", USER_EMAIL)
                        .with(csrf().asHeader())
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute(ERROR_ADDING_NOTIFICATION_USERS, is(ERROR_ADDING_NOTIFICATION_USERS_MESSAGE)))
                .andExpect(view().name("addUsersToProject"))
                .andReturn();
    }

    @Test
    void addUserToProjectPost_shouldNotAddUserToProjectBecauseProjectIsNotExist() throws Exception {
            //todo
    }

    @Test
    void viewUsersToProject_shouldReturnedUsersInProjectPage() throws Exception {
        final Project project = getProject();
        final List<User> usersInProject = project.getUsers();
        given(projectService.getProjectById(PROJECT_ID)).willReturn(project);

        mMockMvc
                .perform(get("/viewUsersToProject")
                        .param("projectId", PROJECT_ID.toString())
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", is(usersInProject)))
                .andExpect(model().attribute(PROJECT_HANDLER, is(project)))
                .andExpect(view().name("viewUsersToProject"))
                .andReturn();
    }

    @Test
    void deleteUser_shouldDeletedUserFromProject() throws Exception {
        Project project = getProject();
        final int usersInProjectSizeBefore = getProject().getUsers().size();
        final User user = getUser2();
        given(projectService.getProjectById(PROJECT_ID)).willReturn(project);
        given(userService.getUserById(USER_ID)).willReturn(user);

        mMockMvc
                .perform(get("/deleteUser")
                        .param("projectId", PROJECT_ID.toString())
                        .param("userId", USER_ID.toString())
                        .with(csrf().asHeader())
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute(SUCCSES_DELETE_USER, is(SUCCSES_DELETE_USER_MESSAGE)))
                .andExpect(view().name("viewUsersToProject"))
                .andReturn();

        //assertEquals(project.getUsers().size(), usersInProjectSizeBefore - 1); //todo
    }

    @Test
    void getProjectPanel_shouldReturnedProjectPanelPage() throws Exception {

        final Project project = getProject();

        given(projectService.isUserHaveAccess(
                ArgumentMatchers.any(Authentication.class),
                eq(PROJECT_ID)
        )).willReturn(true);
        given(projectService.getProjectById(PROJECT_ID)).willReturn(project);

        mMockMvc
                .perform(get("/projectPage")
                        .param("projectId", PROJECT_ID.toString())
                        .with(csrf().asHeader())
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute(PROJECT_HANDLER, is(project)))
                .andExpect(view().name("projectPage"))
                .andReturn();
    }

    @Test
    void getProjectPanel_shouldReturnedErrorPageBecauseUserDoesNotHaveAccess() throws Exception {

        given(projectService.isUserHaveAccess(
                ArgumentMatchers.any(Authentication.class),
                eq(PROJECT_ID)
        )).willReturn(false);

        mMockMvc
                .perform(get("/projectPage")
                        .param("projectId", PROJECT_ID.toString())
                        .with(csrf().asHeader())
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute(ERROR_HANDLER, is(ERROR_MSG_ACCESS)))
                .andExpect(model().attribute(ERROR_HELP_HANDLER, is(ERROR_MSG_HELP_ACCESS)))
                .andExpect(view().name("errorPage"))
                .andReturn();
    }

    @Test
    void addSprint_shouldAddSprintToProject() throws Exception {

        Project project = TestUtil.getProject();
        LocalDate dateFrom = LocalDate.of(2019, 1, 8);
        LocalDate dateTo = LocalDate.of(2019, 1, 22);
        final int storyPoint = 100;
        given(projectService.addSprintToProject(PROJECT_ID, SPRINT_NAME, dateFrom, dateTo, storyPoint)).willReturn(true);
        given(projectService.getProjectById(PROJECT_ID)).willReturn(project);

        mMockMvc
                .perform(post("/projectPage")
                        .param("projectId", PROJECT_ID.toString())
                        .param("name", SPRINT_NAME)
                        .param("dateFrom", dateFrom.toString())
                        .param("dateTo", dateTo.toString())
                        .param("storyPoints", String.valueOf(storyPoint))
                        .with(csrf().asHeader())
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute(PROJECT_HANDLER, is(project)))
                .andExpect(view().name("projectPage"))
                .andReturn();
    }

    @Test
    void addSprint_shouldReturnedErrorPageWrongSprintDate() throws Exception {

        Project project = TestUtil.getProject();
        LocalDate dateFrom = LocalDate.of(2019, 1, 22);
        LocalDate dateTo = LocalDate.of(2019, 1, 8);
        final int storyPoint = 100;
        given(projectService.addSprintToProject(PROJECT_ID, SPRINT_NAME, dateFrom, dateTo, storyPoint)).willReturn(false);
        given(projectService.getProjectById(PROJECT_ID)).willReturn(project);

        mMockMvc
                .perform(post("/projectPage")
                        .param("projectId", PROJECT_ID.toString())
                        .param("name", SPRINT_NAME)
                        .param("dateFrom", dateFrom.toString())
                        .param("dateTo", dateTo.toString())
                        .param("storyPoints", String.valueOf(storyPoint))
                        .with(csrf().asHeader())
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute(PROJECT_HANDLER, is(project)))
                .andExpect(model().attribute(ERROR_ADDING_SPRINT_HANDLER, is(ERROR_ADDING_SPRINT_MESSAGE)))
                .andExpect(view().name("projectPage"))
                .andReturn();
    }
}
