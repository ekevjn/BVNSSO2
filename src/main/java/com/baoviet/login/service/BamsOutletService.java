package com.baoviet.login.service;

import com.baoviet.login.domain.BamsOutlet;
import com.baoviet.login.repository.BamsOutletRepository;
import com.baoviet.login.repository.search.BamsOutletSearchRepository;
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
 * Service Implementation for managing BamsOutlet.
 */
@Service
@Transactional
public class BamsOutletService {

    private final Logger log = LoggerFactory.getLogger(BamsOutletService.class);

    private final BamsOutletRepository bamsOutletRepository;

    private final BamsOutletSearchRepository bamsOutletSearchRepository;

    public BamsOutletService(BamsOutletRepository bamsOutletRepository, BamsOutletSearchRepository bamsOutletSearchRepository) {
        this.bamsOutletRepository = bamsOutletRepository;
        this.bamsOutletSearchRepository = bamsOutletSearchRepository;
    }

    /**
     * Save a bamsOutlet.
     *
     * @param bamsOutlet the entity to save
     * @return the persisted entity
     */
    public BamsOutlet save(BamsOutlet bamsOutlet) {
        log.debug("Request to save BamsOutlet : {}", bamsOutlet);
        BamsOutlet result = bamsOutletRepository.save(bamsOutlet);
        bamsOutletSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the bamsOutlets.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BamsOutlet> findAll(Pageable pageable) {
        log.debug("Request to get all BamsOutlets");
        Page<BamsOutlet> result = bamsOutletRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one bamsOutlet by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BamsOutlet findOne(Long id) {
        log.debug("Request to get BamsOutlet : {}", id);
        BamsOutlet bamsOutlet = bamsOutletRepository.findOne(id);
        return bamsOutlet;
    }

    /**
     *  Delete the  bamsOutlet by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BamsOutlet : {}", id);
        bamsOutletRepository.delete(id);
        bamsOutletSearchRepository.delete(id);
    }

    /**
     * Search for the bamsOutlet corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BamsOutlet> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BamsOutlets for query {}", query);
        Page<BamsOutlet> result = bamsOutletSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

    /*public List<BamsOutlet> find(String bamoutlet) {
        List<BamsOutlet> list = bamsOutletRepository.find(bamoutlet);
        return list;
    }*/


    public BamsOutlet findBydailyId(String dailyId) {
        BamsOutlet bamsOutlet = bamsOutletRepository.findBydailyId(dailyId);
        return bamsOutlet;
    }
}
