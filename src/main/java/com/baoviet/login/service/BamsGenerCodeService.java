package com.baoviet.login.service;

import com.baoviet.login.domain.BamsGenerCode;
import com.baoviet.login.repository.BamsGenerCodeRepository;
import com.baoviet.login.repository.search.BamsGenerCodeSearchRepository;
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
 * Service Implementation for managing BamsGenerCode.
 */
@Service
@Transactional
public class BamsGenerCodeService {

    private final Logger log = LoggerFactory.getLogger(BamsGenerCodeService.class);
    
    private final BamsGenerCodeRepository bamsGenerCodeRepository;

    private final BamsGenerCodeSearchRepository bamsGenerCodeSearchRepository;

    public BamsGenerCodeService(BamsGenerCodeRepository bamsGenerCodeRepository, BamsGenerCodeSearchRepository bamsGenerCodeSearchRepository) {
        this.bamsGenerCodeRepository = bamsGenerCodeRepository;
        this.bamsGenerCodeSearchRepository = bamsGenerCodeSearchRepository;
    }

    /**
     * Save a bamsGenerCode.
     *
     * @param bamsGenerCode the entity to save
     * @return the persisted entity
     */
    public BamsGenerCode save(BamsGenerCode bamsGenerCode) {
        log.debug("Request to save BamsGenerCode : {}", bamsGenerCode);
        BamsGenerCode result = bamsGenerCodeRepository.save(bamsGenerCode);
        bamsGenerCodeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the bamsGenerCodes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BamsGenerCode> findAll(Pageable pageable) {
        log.debug("Request to get all BamsGenerCodes");
        Page<BamsGenerCode> result = bamsGenerCodeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one bamsGenerCode by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BamsGenerCode findOne(Long id) {
        log.debug("Request to get BamsGenerCode : {}", id);
        BamsGenerCode bamsGenerCode = bamsGenerCodeRepository.findOneWithEagerRelationships(id);
        return bamsGenerCode;
    }

    /**
     *  Delete the  bamsGenerCode by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BamsGenerCode : {}", id);
        bamsGenerCodeRepository.delete(id);
        bamsGenerCodeSearchRepository.delete(id);
    }

    /**
     * Search for the bamsGenerCode corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BamsGenerCode> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BamsGenerCodes for query {}", query);
        Page<BamsGenerCode> result = bamsGenerCodeSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
