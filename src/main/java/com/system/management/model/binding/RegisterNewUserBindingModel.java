package com.system.management.model.binding;

import com.system.management.model.validator.EmailRegex;
import com.system.management.model.validator.UniqueEmail;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterNewUserBindingModel {

    @NotNull
    @NotBlank
    @Size(min = 3,max = 255)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min = 3,max = 255)
    private String lastName;

    @EmailRegex
    @UniqueEmail
    private String email;

    public RegisterNewUserBindingModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public RegisterNewUserBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public RegisterNewUserBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterNewUserBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }
}
