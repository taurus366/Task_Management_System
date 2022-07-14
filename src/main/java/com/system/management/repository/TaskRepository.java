package com.system.management.repository;

import com.system.management.model.entity.AccountEntity;
import com.system.management.model.entity.TaskEntity;
import com.system.management.model.entity.enums.TypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    List<TaskEntity> findAllByReportedBy(AccountEntity reportedBy);


    @Query("SELECT t FROM TaskEntity t WHERE (:title is null or t.title = :title)" +
            "and (:type is null or t.type = :type)" +
            "and (:assignedTo is null or t.assignedTo.firstName = :assignedTo)")
    List<TaskEntity> searchForTasks(@Param("title") String title,
                                    @Param("type") TypeEnum type,
                                    @Param("assignedTo") String assignedTo);
}
