package com.system.management.model.service;

public class RegisterNewUserServiceModel {

    private String firstName;
    private String lastName;
    private String email;

    public RegisterNewUserServiceModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public RegisterNewUserServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public RegisterNewUserServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterNewUserServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }
}
