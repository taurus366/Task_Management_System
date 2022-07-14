package com.system.management.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = "project-task-board",
        attributeNodes = {
                @NamedAttributeNode("taskList"),
                @NamedAttributeNode(value = "taskList",subgraph = "board"),
                @NamedAttributeNode("owner")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "board",
                        attributeNodes = {
                                @NamedAttributeNode("board")
                        }
                )
        }
)


@Entity
@Table(name = "projects")
public class ProjectEntity extends BaseEntity {

    @Column(name = "`key`",nullable = false,length = 50,unique = true)
    private String key;

    @Column(nullable = false)
    private String title;

    @ManyToOne()
    private AccountEntity owner;

    @OneToMany(mappedBy = "project",cascade = CascadeType.REMOVE)
    private List<TaskEntity> taskList = new ArrayList<>();

    public ProjectEntity() {
    }

    public String getKey() {
        return key;
    }

    public ProjectEntity setKey(String key) {
        this.key = key;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProjectEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public AccountEntity getOwner() {
        return owner;
    }

    public ProjectEntity setOwner(AccountEntity owner) {
        this.owner = owner;
        return this;
    }

    public List<TaskEntity> getTaskList() {
        return taskList;
    }

    public ProjectEntity setTaskList(List<TaskEntity> taskList) {
        this.taskList = taskList;
        return this;
    }
}
