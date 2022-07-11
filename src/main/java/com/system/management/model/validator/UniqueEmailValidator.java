package com.system.management.model.validator;

import com.system.management.service.AccountService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final AccountService accountService;

    public UniqueEmailValidator(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean emailExists = accountService
                .isEmailExists(value);

        return !emailExists;
    }
}
