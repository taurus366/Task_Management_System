package com.system.management.config;

import com.system.management.model.user.CurrentUser;
import com.system.management.service.AccountService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class ManagementSystemMethodSecurityExpressHandler extends DefaultMethodSecurityExpressionHandler {

    private final CurrentUser currentUser;

    public ManagementSystemMethodSecurityExpressHandler(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
        OwnerSecurityExpressionRoot ownerSecurityExpressionRoot = new OwnerSecurityExpressionRoot(authentication);

        ownerSecurityExpressionRoot.setPermissionEvaluator(getPermissionEvaluator());
        ownerSecurityExpressionRoot.setTrustResolver(new AuthenticationTrustResolverImpl());
        ownerSecurityExpressionRoot.setRoleHierarchy(getRoleHierarchy());
        ownerSecurityExpressionRoot.setUser(currentUser);

        return ownerSecurityExpressionRoot;
    }

}
