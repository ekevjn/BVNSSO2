package com.baoviet.login.service;

import com.baoviet.login.domain.BamsPermission;
import com.baoviet.login.repository.BamsPermissionRepository;
import com.baoviet.login.repository.search.BamsPermissionSearchRepository;
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
 * Service Implementation for managing BamsPermission.
 */
@Service
@Transactional
public class BamsPermissionService {

    private final Logger log = LoggerFactory.getLogger(BamsPermissionService.class);
    
    private final BamsPermissionRepository bamsPermissionRepository;

    private final BamsPermissionSearchRepository bamsPermissionSearchRepository;

    public BamsPermissionService(BamsPermissionRepository bamsPermissionRepository, BamsPermissionSearchRepository bamsPermissionSearchRepository) {
        this.bamsPermissionRepository = bamsPermissionRepository;
        this.bamsPermissionSearchRepository = bamsPermissionSearchRepository;
    }

    /**
     * Save a bamsPermission.
     *
     * @param bamsPermission the entity to save
     * @return the persisted entity
     */
    public BamsPermission save(BamsPermission bamsPermission) {
        log.debug("Request to save BamsPermission : {}", bamsPermission);
        BamsPermission result = bamsPermissionRepository.save(bamsPermission);
        bamsPermissionSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the bamsPermissions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BamsPermission> findAll(Pageable pageable) {
        log.debug("Request to get all BamsPermissions");
        Page<BamsPermission> result = bamsPermissionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one bamsPermission by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BamsPermission findOne(Long id) {
        log.debug("Request to get BamsPermission : {}", id);
        BamsPermission bamsPermission = bamsPermissionRepository.findOne(id);
        return bamsPermission;
    }

    /**
     *  Delete the  bamsPermission by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BamsPermission : {}", id);
        bamsPermissionRepository.delete(id);
        bamsPermissionSearchRepository.delete(id);
    }

    /**
     * Search for the bamsPermission corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BamsPermission> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BamsPermissions for query {}", query);
        Page<BamsPermission> result = bamsPermissionSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
