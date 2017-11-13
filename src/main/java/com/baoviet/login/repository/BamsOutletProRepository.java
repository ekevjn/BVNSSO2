package com.baoviet.login.repository;

import com.baoviet.login.domain.BamsOutletPro;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BamsOutletPro entity.
 */
@SuppressWarnings("unused")
public interface BamsOutletProRepository extends JpaRepository<BamsOutletPro,Long> {

    BamsOutletPro findByproductcode(String productcode);
}
