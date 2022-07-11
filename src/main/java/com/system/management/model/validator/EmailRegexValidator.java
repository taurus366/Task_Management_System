package com.system.management.model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailRegexValidator implements ConstraintValidator<EmailRegex,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String patternString = "^[a-zA-Z0-9_\\\\.-]{3,}@[a-zA-Z0-9]{2,}\\.[a-z]{2,}(\\.[a-z]{2,})?$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(value);

        return matcher.matches();
    }
}
