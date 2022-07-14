package com.system.management.model.binding;
import com.system.management.model.entity.enums.PriorityEnum;
import com.system.management.model.entity.enums.StatusEnum;
import com.system.management.model.entity.enums.TypeEnum;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateNewTaskBindingModel {

    @NotNull
    @NotBlank
    @Size(min = 3,max = 255)
    private String title;

    @NotNull
    private Integer project;

    @NotNull
    private Integer board;

    @NotNull
    private TypeEnum type;

    @NotNull
    private StatusEnum status;

    private String description;

    private Integer assignedTo;

    @NotNull
    private PriorityEnum priority;

    private int storyPoints;

    public CreateNewTaskBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public CreateNewTaskBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getProject() {
        return project;
    }

    public CreateNewTaskBindingModel setProject(Integer project) {
        this.project = project;
        return this;
    }

    public Integer getBoard() {
        return board;
    }

    public CreateNewTaskBindingModel setBoard(Integer board) {
        this.board = board;
        return this;
    }

    public TypeEnum getType() {
        return type;
    }

    public CreateNewTaskBindingModel setType(TypeEnum type) {
        this.type = type;
        return this;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public CreateNewTaskBindingModel setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CreateNewTaskBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public CreateNewTaskBindingModel setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
        return this;
    }

    public PriorityEnum getPriority() {
        return priority;
    }

    public CreateNewTaskBindingModel setPriority(PriorityEnum priority) {
        this.priority = priority;
        return this;
    }

    public int getStoryPoints() {
        return storyPoints;
    }

    public CreateNewTaskBindingModel setStoryPoints(int storyPoints) {
        this.storyPoints = storyPoints;
        return this;
    }
}
