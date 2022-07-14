package com.system.management.repository;

import com.system.management.model.entity.AccountEntity;
import com.system.management.model.entity.ProjectEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity,Integer> {

    @Query("select pr FROM ProjectEntity pr where pr.owner.id = (:id)")
    List<ProjectEntity> getAllProjectsByOwnerId(@Param("id") Integer ownerId);

    @EntityGraph(value = "project-task-board")
    @Query("SELECT pr FROM ProjectEntity pr where pr.id = :id")
    ProjectEntity getProjectListBoardGraph(@Param("id") Integer id);

    List<ProjectEntity> findAllByOwner(AccountEntity owner);
}
