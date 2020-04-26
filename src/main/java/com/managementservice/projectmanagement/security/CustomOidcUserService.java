package com.managementservice.projectmanagement.security;

import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.service.UserService;
import com.managementservice.projectmanagement.utils.AccountTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOidcUserService extends OidcUserService {

    private UserService userService;

    @Autowired
    public CustomOidcUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        try {
            return processOidcUser(userRequest, oidcUser);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OidcUser processOidcUser(OidcUserRequest userRequest, OidcUser oidcUser) {

        Optional<User> optionalUser = userService.getUserByUsernameOrEmail(oidcUser.getEmail());
        if (!optionalUser.isPresent()) {
            userService.registerUser(
                    oidcUser.getEmail(),
                    oidcUser.getAccessTokenHash(),
                    oidcUser.getEmail(),
                    AccountTypeEnum.valueOf(userRequest.getClientRegistration().getClientName()));
        }
        return oidcUser;
    }
}
