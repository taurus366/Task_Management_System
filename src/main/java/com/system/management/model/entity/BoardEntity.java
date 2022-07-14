package com.system.management.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = "board-tasks",
        attributeNodes = {
                @NamedAttributeNode("taskList"),
                @NamedAttributeNode(value = "taskList",subgraph = "project")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "project",
                        attributeNodes = {
                                @NamedAttributeNode("project")
                        }
                )
        }
)


@Entity
@Table(name = "boards")
public class BoardEntity extends BaseEntity {

    @Column(nullable = false,unique = true)
    private String name;

    @OneToOne()
    private AccountEntity owner;

    @ManyToMany(mappedBy = "boardLists")
    private List<AccountEntity> boardMember;

    @OneToMany(mappedBy = "board",cascade = {CascadeType.ALL})
    private List<TaskEntity> taskList =  new ArrayList<>();

    public BoardEntity() {
    }

    public String getName() {
        return name;
    }

    public BoardEntity setName(String name) {
        this.name = name;
        return this;
    }

    public AccountEntity getOwner() {
        return owner;
    }

    public BoardEntity setOwner(AccountEntity owner) {
        this.owner = owner;
        return this;
    }

    public List<AccountEntity> getBoardMember() {
        return boardMember;
    }

    public BoardEntity setBoardMember(List<AccountEntity> boardMember) {
        this.boardMember = boardMember;
        return this;
    }

    public List<TaskEntity> getTaskList() {
        return taskList;
    }

    public BoardEntity setTaskList(List<TaskEntity> taskList) {
        this.taskList = taskList;
        return this;
    }
}
