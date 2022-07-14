package com.system.management.model.user;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CurrentUser {

    private Integer id;
    private boolean loggedIn;
    private String firstName;
    private String lastName;
    private String email;

    public Integer getId() {
        return id;
    }

    public CurrentUser setId(Integer id) {
        this.id = id;
        return this;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public CurrentUser setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public CurrentUser setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public CurrentUser setLastName(String lastNAme) {
        this.lastName = lastNAme;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CurrentUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public void clean() {
        setLoggedIn(false)
                .setId(null)
                .setFirstName(null)
                .setLastName(null)
                .setEmail(null);
    }
}
