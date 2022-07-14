package com.system.management.model.service;

import com.system.management.model.entity.enums.PriorityEnum;
import com.system.management.model.entity.enums.StatusEnum;
import com.system.management.model.entity.enums.TypeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateNewTaskServiceModel {

    private String title;

    private Integer project;

    private Integer board;

    private TypeEnum type;

    private StatusEnum status;

    private String description;

    private Integer assignedTo;

    private PriorityEnum priority;

    private int storyPoints;

    public CreateNewTaskServiceModel() {
    }

    public String getTitle() {
        return title;
    }

    public CreateNewTaskServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getProject() {
        return project;
    }

    public CreateNewTaskServiceModel setProject(Integer project) {
        this.project = project;
        return this;
    }

    public Integer getBoard() {
        return board;
    }

    public CreateNewTaskServiceModel setBoard(Integer board) {
        this.board = board;
        return this;
    }

    public TypeEnum getType() {
        return type;
    }

    public CreateNewTaskServiceModel setType(TypeEnum type) {
        this.type = type;
        return this;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public CreateNewTaskServiceModel setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CreateNewTaskServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public CreateNewTaskServiceModel setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
        return this;
    }

    public PriorityEnum getPriority() {
        return priority;
    }

    public CreateNewTaskServiceModel setPriority(PriorityEnum priority) {
        this.priority = priority;
        return this;
    }

    public int getStoryPoints() {
        return storyPoints;
    }

    public CreateNewTaskServiceModel setStoryPoints(int storyPoints) {
        this.storyPoints = storyPoints;
        return this;
    }
}
