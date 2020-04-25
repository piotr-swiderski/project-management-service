package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.service.UserService;
import com.managementservice.projectmanagement.utils.AccountTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import static com.managementservice.projectmanagement.utils.ControllerUtil.*;
import static com.managementservice.projectmanagement.utils.TestUtil.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LoginControllerTest extends AbstractControllerTest {

    @MockBean
    UserService userService;

    @Test
    void getLoginPage_shouldReturnLoginPage() throws Exception {
        mMockMvc
                .perform(
                        get("/login")
                )
                .andExpect(view().name("login"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void loginError_shouldReturnedLoginError() throws Exception {
        mMockMvc
                .perform(get("/login-error"))
                .andExpect(model().attribute(ERROR_LOGIN_BAD_CREDENSIALS, is(ERROR_LOGIN_BAD_CREDENSIALS_MESSAGE)))
                .andExpect(view().name("login"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void loginErrorOauth2_shouldReturnedLoginErrorOAuth2() throws Exception {
        mMockMvc
                .perform(get("/login-error-oauth2"))
                .andExpect(model().attribute(ERROR_LOGIN_BAD_CREDENSIALS, is(ERROR_LOGIN_OAUTH2_BAD_CREDENSIALS_MESSAGE)))
                .andExpect(view().name("login"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void singUp_shouldSingUpAndReturnedLoginPage() throws Exception {

        given(userService.registerUser(USER_USERNAME, USER_PASSWORD, USER_EMAIL, AccountTypeEnum.NONE)).willReturn(getUser());

        mMockMvc
                .perform(
                        post("/signup")
                                .param("username", USER_USERNAME)
                                .param("email", USER_EMAIL)
                                .param("password", USER_PASSWORD)
                                .with(csrf().asHeader())
                )
                .andExpect(view().name("login"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
