package com.baoviet.login.service;

import com.baoviet.login.domain.BamsCompany;
import com.baoviet.login.repository.BamsCompanyRepository;
import com.baoviet.login.repository.search.BamsCompanySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BamsCompany.
 */
@Service
@Transactional
public class BamsCompanyService {

    private final Logger log = LoggerFactory.getLogger(BamsCompanyService.class);

    private final BamsCompanyRepository bamsCompanyRepository;

    private final BamsCompanySearchRepository bamsCompanySearchRepository;

    public BamsCompanyService(BamsCompanyRepository bamsCompanyRepository, BamsCompanySearchRepository bamsCompanySearchRepository) {
        this.bamsCompanyRepository = bamsCompanyRepository;
        this.bamsCompanySearchRepository = bamsCompanySearchRepository;
    }

    /**
     * Save a bamsCompany.
     *
     * @param bamsCompany the entity to save
     * @return the persisted entity
     */
    public BamsCompany save(BamsCompany bamsCompany) {
        log.debug("Request to save BamsCompany : {}", bamsCompany);
        BamsCompany result = bamsCompanyRepository.save(bamsCompany);
        bamsCompanySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the bamsCompanies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BamsCompany> findAll(Pageable pageable) {
        log.debug("Request to get all BamsCompanies");
        Page<BamsCompany> result = bamsCompanyRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one bamsCompany by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BamsCompany findOne(Long id) {
        log.debug("Request to get BamsCompany : {}", id);
        BamsCompany bamsCompany = bamsCompanyRepository.findOne(id);
        return bamsCompany;
    }

    /**
     *  Delete the  bamsCompany by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BamsCompany : {}", id);
        bamsCompanyRepository.delete(id);
        bamsCompanySearchRepository.delete(id);
    }

    /**
     * Search for the bamsCompany corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BamsCompany> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BamsCompanies for query {}", query);
        Page<BamsCompany> result = bamsCompanySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

    public BamsCompany findBymatinh(String matinh) {
        BamsCompany bamsCompany = bamsCompanyRepository.findBymatinh(matinh);
        return bamsCompany;
    }
}
