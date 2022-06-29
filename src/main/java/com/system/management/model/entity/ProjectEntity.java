package com.system.management.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "projects")
public class ProjectEntity extends BaseEntity {

    @Column(name = "`key`",nullable = false,length = 50,unique = true)
    private String key;

    @Column(nullable = false)
    private String title;

    @ManyToOne()
    private AccountEntity owner;

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
}
