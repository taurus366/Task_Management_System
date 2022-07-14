package com.system.management.model.dto;

public class ProjectInformationDTO {

    private Integer id;
    private String key;
    private String title;
    private Integer taskSize;
    private boolean isOwner = false;

    public ProjectInformationDTO() {
    }

    public Integer getId() {
        return id;
    }

    public ProjectInformationDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getKey() {
        return key;
    }

    public ProjectInformationDTO setKey(String key) {
        this.key = key;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProjectInformationDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getTaskSize() {
        return taskSize;
    }

    public ProjectInformationDTO setTaskSize(Integer taskSize) {
        this.taskSize = taskSize;
        return this;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public ProjectInformationDTO setOwner(boolean owner) {
        isOwner = owner;
        return this;
    }
}
