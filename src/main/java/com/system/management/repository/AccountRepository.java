package com.system.management.repository;

import com.system.management.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Integer> {

    boolean existsAccountEntityByEmail(String email);
    Optional<AccountEntity> getAccountEntityByEmail(String email);

    @Query("SELECT  ac FROM AccountEntity ac WHERE ac.id IN (:ids)")
    List<AccountEntity> getAllByIds(@Param("ids") List<Integer> id);
}
