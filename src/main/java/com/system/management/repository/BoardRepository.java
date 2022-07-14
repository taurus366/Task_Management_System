package com.system.management.repository;

import com.system.management.model.entity.AccountEntity;
import com.system.management.model.entity.BoardEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity,Integer> {

    @EntityGraph(value = "board-tasks")
    @Query("select br FROM BoardEntity br where br.id = :id")
    BoardEntity getBoardByIdTasksGraph(@Param("id")Integer id);

    List<BoardEntity> findAllByBoardMember(AccountEntity boardMember);
    List<BoardEntity> findAllByOwner(AccountEntity account);
}
