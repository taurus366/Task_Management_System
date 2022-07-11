package com.system.management.model.service;

public class CreateNewProjectServiceModel {

    private String title;
    private String key;

    public CreateNewProjectServiceModel() {
    }

    public String getTitle() {
        return title;
    }

    public CreateNewProjectServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getKey() {
        return key;
    }

    public CreateNewProjectServiceModel setKey(String key) {
        this.key = key;
        return this;
    }
}
