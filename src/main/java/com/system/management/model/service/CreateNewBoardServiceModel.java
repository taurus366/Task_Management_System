package com.system.management.model.service;

import com.system.management.model.object.Members;

import java.util.List;

public class CreateNewBoardServiceModel {

    private String name;


    private List<Members> members;

    public CreateNewBoardServiceModel() {
    }

    public String getName() {
        return name;
    }

    public CreateNewBoardServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public List<Members> getMembers() {
        return members;
    }

    public CreateNewBoardServiceModel setMembers(List<Members> members) {
        this.members = members;
        return this;
    }
}
