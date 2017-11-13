package com.baoviet.login.service;

import com.baoviet.login.domain.BamsGenerType;
import com.baoviet.login.repository.BamsGenerTypeRepository;
import com.baoviet.login.repository.search.BamsGenerTypeSearchRepository;
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
 * Service Implementation for managing BamsGenerType.
 */
@Service
@Transactional
public class BamsGenerTypeService {

    private final Logger log = LoggerFactory.getLogger(BamsGenerTypeService.class);
    
    private final BamsGenerTypeRepository bamsGenerTypeRepository;

    private final BamsGenerTypeSearchRepository bamsGenerTypeSearchRepository;

    public BamsGenerTypeService(BamsGenerTypeRepository bamsGenerTypeRepository, BamsGenerTypeSearchRepository bamsGenerTypeSearchRepository) {
        this.bamsGenerTypeRepository = bamsGenerTypeRepository;
        this.bamsGenerTypeSearchRepository = bamsGenerTypeSearchRepository;
    }

    /**
     * Save a bamsGenerType.
     *
     * @param bamsGenerType the entity to save
     * @return the persisted entity
     */
    public BamsGenerType save(BamsGenerType bamsGenerType) {
        log.debug("Request to save BamsGenerType : {}", bamsGenerType);
        BamsGenerType result = bamsGenerTypeRepository.save(bamsGenerType);
        bamsGenerTypeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the bamsGenerTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BamsGenerType> findAll(Pageable pageable) {
        log.debug("Request to get all BamsGenerTypes");
        Page<BamsGenerType> result = bamsGenerTypeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one bamsGenerType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BamsGenerType findOne(Long id) {
        log.debug("Request to get BamsGenerType : {}", id);
        BamsGenerType bamsGenerType = bamsGenerTypeRepository.findOne(id);
        return bamsGenerType;
    }

    /**
     *  Delete the  bamsGenerType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BamsGenerType : {}", id);
        bamsGenerTypeRepository.delete(id);
        bamsGenerTypeSearchRepository.delete(id);
    }

    /**
     * Search for the bamsGenerType corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BamsGenerType> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BamsGenerTypes for query {}", query);
        Page<BamsGenerType> result = bamsGenerTypeSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
