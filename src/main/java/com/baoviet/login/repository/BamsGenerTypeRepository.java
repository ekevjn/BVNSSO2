package com.baoviet.login.repository;

import com.baoviet.login.domain.BamsGenerType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BamsGenerType entity.
 */
@SuppressWarnings("unused")
public interface BamsGenerTypeRepository extends JpaRepository<BamsGenerType,Long> {

}
