package com.baoviet.login.service;

import com.baoviet.login.domain.BamsAuthority;
import com.baoviet.login.repository.BamsAuthorityRepository;
import com.baoviet.login.repository.search.BamsAuthoritySearchRepository;
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
 * Service Implementation for managing BamsAuthority.
 */
@Service
@Transactional
public class BamsAuthorityService {

    private final Logger log = LoggerFactory.getLogger(BamsAuthorityService.class);
    
    private final BamsAuthorityRepository bamsAuthorityRepository;

    private final BamsAuthoritySearchRepository bamsAuthoritySearchRepository;

    public BamsAuthorityService(BamsAuthorityRepository bamsAuthorityRepository, BamsAuthoritySearchRepository bamsAuthoritySearchRepository) {
        this.bamsAuthorityRepository = bamsAuthorityRepository;
        this.bamsAuthoritySearchRepository = bamsAuthoritySearchRepository;
    }

    /**
     * Save a bamsAuthority.
     *
     * @param bamsAuthority the entity to save
     * @return the persisted entity
     */
    public BamsAuthority save(BamsAuthority bamsAuthority) {
        log.debug("Request to save BamsAuthority : {}", bamsAuthority);
        BamsAuthority result = bamsAuthorityRepository.save(bamsAuthority);
        bamsAuthoritySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the bamsAuthorities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BamsAuthority> findAll(Pageable pageable) {
        log.debug("Request to get all BamsAuthorities");
        Page<BamsAuthority> result = bamsAuthorityRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one bamsAuthority by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BamsAuthority findOne(Long id) {
        log.debug("Request to get BamsAuthority : {}", id);
        BamsAuthority bamsAuthority = bamsAuthorityRepository.findOneWithEagerRelationships(id);
        return bamsAuthority;
    }

    /**
     *  Delete the  bamsAuthority by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BamsAuthority : {}", id);
        bamsAuthorityRepository.delete(id);
        bamsAuthoritySearchRepository.delete(id);
    }

    /**
     * Search for the bamsAuthority corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BamsAuthority> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BamsAuthorities for query {}", query);
        Page<BamsAuthority> result = bamsAuthoritySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
