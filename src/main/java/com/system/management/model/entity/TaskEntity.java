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

    @ManyToOne
    private ProjectEntity project;

    @ManyToOne
    private BoardEntity board;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 50)
    private TypeEnum type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 50)
    private StatusEnum status;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne()
    private AccountEntity reportedBy;

    @OneToOne()
    private AccountEntity assignedTo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 50)
    private PriorityEnum priority;

    @Column()
    private int storyPoints;
}
