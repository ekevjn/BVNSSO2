package com.baoviet.login.repository;

import com.baoviet.login.domain.BamsOutlet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BamsOutlet entity.
 */
@SuppressWarnings("unused")
public interface BamsOutletRepository extends JpaRepository<BamsOutlet,Long> {

    /*@Query("select bamsOutlet from BamsOutlet bamsOutlet where UPPER(bamsOutlet.tendaily) LIKE UPPER(:bamoutlet) AND ROWNUM <= 10")
    List<BamsOutlet> find(String bamoutlet);*/

    BamsOutlet findBydailyId(String dailyId);

}
