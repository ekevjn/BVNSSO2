package com.baoviet.login.repository;

import com.baoviet.login.domain.BamsCompany;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BamsCompany entity.
 */
@SuppressWarnings("unused")
public interface BamsCompanyRepository extends JpaRepository<BamsCompany,Long> {

    BamsCompany findBymatinh(String matinh);
}
