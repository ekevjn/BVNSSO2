package com.baoviet.login.repository;

import com.baoviet.login.domain.BamsGenerCode;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the BamsGenerCode entity.
 */
@SuppressWarnings("unused")
public interface BamsGenerCodeRepository extends JpaRepository<BamsGenerCode,Long> {

    @Query("select distinct bamsGenerCode from BamsGenerCode bamsGenerCode left join fetch bamsGenerCode.bamsUsers left join fetch bamsGenerCode.bamsAuthorities")
    List<BamsGenerCode> findAllWithEagerRelationships();

    @Query("select bamsGenerCode from BamsGenerCode bamsGenerCode left join fetch bamsGenerCode.bamsUsers left join fetch bamsGenerCode.bamsAuthorities where bamsGenerCode.id =:id")
    BamsGenerCode findOneWithEagerRelationships(@Param("id") Long id);

}
