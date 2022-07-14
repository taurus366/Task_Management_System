package com.system.management.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NamedEntityGraph(
        name = "account-board-task-project-member",
        attributeNodes = {
                @NamedAttributeNode("boardLists"),
                @NamedAttributeNode(value = "boardLists",subgraph = "tasks")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "tasks",
                        attributeNodes = {
                                @NamedAttributeNode("taskList")

                        }
                )
        }
)


@Entity
@Table(name = "accounts")
public class AccountEntity extends BaseEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false,unique = true)
    private String email;

    @ManyToMany()
    private List<BoardEntity> boardLists = new ArrayList<>();

    public AccountEntity() {
    }

    public String getFirstName() {
        return firstName;
    }

    public AccountEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AccountEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AccountEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<BoardEntity> getBoardLists() {
        return boardLists;
    }

    public AccountEntity setBoardLists(List<BoardEntity> boardLists) {
        this.boardLists = boardLists;
        return this;
    }
}
