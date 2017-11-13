package com.baoviet.login.service;

import com.baoviet.login.domain.BamsOutletPro;
import com.baoviet.login.repository.BamsOutletProRepository;
import com.baoviet.login.repository.search.BamsOutletProSearchRepository;
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
 * Service Implementation for managing BamsOutletPro.
 */
@Service
@Transactional
public class BamsOutletProService {

    private final Logger log = LoggerFactory.getLogger(BamsOutletProService.class);

    private final BamsOutletProRepository bamsOutletProRepository;

    private final BamsOutletProSearchRepository bamsOutletProSearchRepository;

    public BamsOutletProService(BamsOutletProRepository bamsOutletProRepository, BamsOutletProSearchRepository bamsOutletProSearchRepository) {
        this.bamsOutletProRepository = bamsOutletProRepository;
        this.bamsOutletProSearchRepository = bamsOutletProSearchRepository;
    }

    /**
     * Save a bamsOutletPro.
     *
     * @param bamsOutletPro the entity to save
     * @return the persisted entity
     */
    public BamsOutletPro save(BamsOutletPro bamsOutletPro) {
        log.debug("Request to save BamsOutletPro : {}", bamsOutletPro);
        BamsOutletPro result = bamsOutletProRepository.save(bamsOutletPro);
        bamsOutletProSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the bamsOutletPros.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BamsOutletPro> findAll(Pageable pageable) {
        log.debug("Request to get all BamsOutletPros");
        Page<BamsOutletPro> result = bamsOutletProRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one bamsOutletPro by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BamsOutletPro findOne(Long id) {
        log.debug("Request to get BamsOutletPro : {}", id);
        BamsOutletPro bamsOutletPro = bamsOutletProRepository.findOne(id);
        return bamsOutletPro;
    }

    /**
     *  Delete the  bamsOutletPro by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BamsOutletPro : {}", id);
        bamsOutletProRepository.delete(id);
        bamsOutletProSearchRepository.delete(id);
    }

    /**
     * Search for the bamsOutletPro corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BamsOutletPro> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BamsOutletPros for query {}", query);
        Page<BamsOutletPro> result = bamsOutletProSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }


    public BamsOutletPro findByproductcode(String productcode) {
        BamsOutletPro bamsOutletPro = bamsOutletProRepository.findByproductcode(productcode);
        return bamsOutletPro;
    }
}
