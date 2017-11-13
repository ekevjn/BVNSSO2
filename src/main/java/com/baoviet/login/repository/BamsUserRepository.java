package com.baoviet.login.repository;

import com.baoviet.login.domain.BamsUser;

import com.baoviet.login.domain.User;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the BamsUser entity.
 */
@SuppressWarnings("unused")
public interface BamsUserRepository extends JpaRepository<BamsUser,Long> {
    @EntityGraph(attributePaths = "bamsGenerCodes")
    Optional<BamsUser> findOneWithBamsGenerCodesByUname(String Uname);
}
