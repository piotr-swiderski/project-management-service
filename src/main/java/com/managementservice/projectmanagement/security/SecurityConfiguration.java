package com.managementservice.projectmanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserDetailsService userDetailsService;
    private CustomOidcUserService customOidcUserService;

    @Autowired
    public SecurityConfiguration(BCryptPasswordEncoder bCryptPasswordEncoder,
                                 UserDetailsService userDetailsService,
                                 CustomOidcUserService customOidcUserService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
        this.customOidcUserService = customOidcUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/").authenticated()
                .antMatchers("/index").authenticated()
                .antMatchers("/newProject").authenticated()
                .antMatchers("/myProjectList").authenticated()
                .antMatchers("/projectPage/**").authenticated()
                .antMatchers("/sprint/**").authenticated()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .loginProcessingUrl("/appLogin")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .failureUrl("/login-error-oauth2")
                .userInfoEndpoint()
                .oidcUserService(customOidcUserService);

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

        //auth
        //      .authenticationProvider()
    }


}
