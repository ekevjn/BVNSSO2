package com.baoviet.login.repository;

import com.baoviet.login.domain.BamsPermission;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BamsPermission entity.
 */
@SuppressWarnings("unused")
public interface BamsPermissionRepository extends JpaRepository<BamsPermission,Long> {

}
