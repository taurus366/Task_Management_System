package com.system.management.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "boards")
public class BoardEntity extends BaseEntity {

    @Column(nullable = false,unique = true)
    private String name;

    @OneToOne()
    private AccountEntity owner;

    @ManyToMany()
    private List<AccountEntity> boardMember;

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
}
