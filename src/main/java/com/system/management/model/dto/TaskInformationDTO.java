package com.system.management.model.dto;

public class TaskInformationDTO {

    private Integer id;
    private String taskTitle;
    private String assignedTo;
    private String projectName;
    private String reportedBy;
    private String status;
    private String priority;

    public TaskInformationDTO() {
    }

    public Integer getId() {
        return id;
    }

    public TaskInformationDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public TaskInformationDTO setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
        return this;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public TaskInformationDTO setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public TaskInformationDTO setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public TaskInformationDTO setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TaskInformationDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getPriority() {
        return priority;
    }

    public TaskInformationDTO setPriority(String priority) {
        this.priority = priority;
        return this;
    }
}
