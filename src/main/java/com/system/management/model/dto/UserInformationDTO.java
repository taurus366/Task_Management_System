package com.system.management.model.dto;

public class UserInformationDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    public UserInformationDTO() {
    }

    public Integer getId() {
        return id;
    }

    public UserInformationDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserInformationDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserInformationDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserInformationDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
