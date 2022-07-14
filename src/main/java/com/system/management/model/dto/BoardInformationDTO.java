package com.system.management.model.dto;

import java.util.List;

public class BoardInformationDTO {

    private Integer id;
    private String name;
    private Integer taskSize;
    private Integer projectSize;
    private Integer memberSize;
    private List<UserInformationDTO> members;
    private boolean isOwner = false;

    public BoardInformationDTO() {
    }

    public Integer getId() {
        return id;
    }

    public BoardInformationDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BoardInformationDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getTaskSize() {
        return taskSize;
    }

    public BoardInformationDTO setTaskSize(Integer taskSize) {
        this.taskSize = taskSize;
        return this;
    }

    public Integer getProjectSize() {
        return projectSize;
    }

    public BoardInformationDTO setProjectSize(Integer projectSize) {
        this.projectSize = projectSize;
        return this;
    }

    public Integer getMemberSize() {
        return memberSize;
    }

    public BoardInformationDTO setMemberSize(Integer memberSize) {
        this.memberSize = memberSize;
        return this;
    }

    public List<UserInformationDTO> getMembers() {
        return members;
    }

    public BoardInformationDTO setMembers(List<UserInformationDTO> members) {
        this.members = members;
        return this;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public BoardInformationDTO setOwner(boolean owner) {
        isOwner = owner;
        return this;
    }
}
