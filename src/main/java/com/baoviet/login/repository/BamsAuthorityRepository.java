package com.baoviet.login.repository;

import com.baoviet.login.domain.BamsAuthority;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the BamsAuthority entity.
 */
@SuppressWarnings("unused")
public interface BamsAuthorityRepository extends JpaRepository<BamsAuthority,Long> {

    @Query("select distinct bamsAuthority from BamsAuthority bamsAuthority left join fetch bamsAuthority.bamsPermissions")
    List<BamsAuthority> findAllWithEagerRelationships();

    @Query("select bamsAuthority from BamsAuthority bamsAuthority left join fetch bamsAuthority.bamsPermissions where bamsAuthority.id =:id")
    BamsAuthority findOneWithEagerRelationships(@Param("id") Long id);

}
