package com.system.management.model.entity;

import com.system.management.model.entity.enums.PriorityEnum;
import com.system.management.model.entity.enums.StatusEnum;
import com.system.management.model.entity.enums.TypeEnum;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class TaskEntity extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @ManyToOne()
    private ProjectEntity project;

    @ManyToOne(targetEntity = BoardEntity.class)
    private BoardEntity board;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 50)
    private TypeEnum type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 50)
    private StatusEnum status;

    @Column(columnDefinition = "TEXT",nullable = true)
    private String description;

    @ManyToOne(optional = false)
    private AccountEntity reportedBy;

    @ManyToOne(optional = true)
    private AccountEntity assignedTo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 50)
    private PriorityEnum priority;

    @Column()
    private int storyPoints;

    public TaskEntity() {
    }

    public String getTitle() {
        return title;
    }

    public TaskEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public TaskEntity setProject(ProjectEntity project) {
        this.project = project;
        return this;
    }

    public BoardEntity getBoard() {
        return board;
    }

    public TaskEntity setBoard(BoardEntity board) {
        this.board = board;
        return this;
    }

    public TypeEnum getType() {
        return type;
    }

    public TaskEntity setType(TypeEnum type) {
        this.type = type;
        return this;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public TaskEntity setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public AccountEntity getReportedBy() {
        return reportedBy;
    }

    public TaskEntity setReportedBy(AccountEntity reportedBy) {
        this.reportedBy = reportedBy;
        return this;
    }

    public AccountEntity getAssignedTo() {
        return assignedTo;
    }

    public TaskEntity setAssignedTo(AccountEntity assignedTo) {
        this.assignedTo = assignedTo;
        return this;
    }

    public PriorityEnum getPriority() {
        return priority;
    }

    public TaskEntity setPriority(PriorityEnum priority) {
        this.priority = priority;
        return this;
    }

    public int getStoryPoints() {
        return storyPoints;
    }

    public TaskEntity setStoryPoints(int storyPoints) {
        this.storyPoints = storyPoints;
        return this;
    }
}
