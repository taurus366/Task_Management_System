package com.system.management.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailRegexValidator.class)
public @interface EmailRegex {

    String message() default "Email isn't valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
