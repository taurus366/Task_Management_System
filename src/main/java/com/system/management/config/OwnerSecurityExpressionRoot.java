package com.system.management.config;

import com.system.management.model.user.CurrentUser;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class OwnerSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
    private Object returnObject;
    private Object filterObject;
    private CurrentUser currentUser;

    public boolean isLogged() {

        return currentUser.isLoggedIn();
    }

    public OwnerSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public void setUser(CurrentUser user) {
        this.currentUser = user;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
            this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}
