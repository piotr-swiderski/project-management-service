package com.managementservice.projectmanagement.entity;

import com.managementservice.projectmanagement.utils.AccountTypeEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private boolean active;

    private String roles = "";

    private String permissions = "";

    @Enumerated(EnumType.STRING)
    private AccountTypeEnum accountType;


    @ManyToMany(mappedBy = "users")
    private Set<Project> projects = new HashSet<>();


    public User(String username, String password, String email, String roles, String permissions) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.active = true;
        this.roles = roles;
        this.permissions = permissions;
    }

    public User() {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] arrayPermissions = permissions.split(", ");
        Set<SimpleGrantedAuthority> grantedAuthorities = Arrays.stream(arrayPermissions).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + roles));
        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
    }

    public Set<Project> getProjects() {
        return projects;
    }


    public void addProject(Project project) {
        this.projects.add(project);
        project.getUsers().add(this);
    }

    public void removeProject(Project project) {
        this.projects.remove(project);
        project.getUsers().remove(this);
    }

    public static final class UserBuilder {
        private long id;
        private String username;
        private String password;
        private String email;
        private boolean active;
        private String roles = "";
        private String permissions = "";
        private AccountTypeEnum accountType = AccountTypeEnum.NONE;
        private Set<Project> projects = new HashSet<>();

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public UserBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withActive(boolean active) {
            this.active = active;
            return this;
        }

        public UserBuilder withRoles(String roles) {
            this.roles = roles;
            return this;
        }

        public UserBuilder withPermissions(String permissions) {
            this.permissions = permissions;
            return this;
        }

        public UserBuilder withProjects(Set<Project> projects) {
            this.projects = projects;
            return this;
        }

        public UserBuilder withAccountType(AccountTypeEnum accountType) {
            this.accountType = accountType;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setActive(active);
            user.setRoles(roles);
            user.setPermissions(permissions);
            user.setAccountType(accountType);
            user.projects = this.projects;
            return user;
        }
    }
}
