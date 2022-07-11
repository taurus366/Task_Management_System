package com.system.management.config;

import com.system.management.model.user.CurrentUser;
import com.system.management.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    private  ManagementSystemMethodSecurityExpressHandler managementSystemMethodSecurityExpressHandler;



    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return managementSystemMethodSecurityExpressHandler;
    }

    @Bean
    public ManagementSystemMethodSecurityExpressHandler createExpressionHandler( CurrentUser currentUser) {
        return new ManagementSystemMethodSecurityExpressHandler(currentUser);
    }
}
