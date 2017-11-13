package com.baoviet.login.service;

import com.baoviet.login.domain.BamsUser;
import com.baoviet.login.repository.BamsUserRepository;
import com.baoviet.login.repository.search.BamsUserSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BamsUser.
 */
@Service
@Transactional
public class BamsUserService {

    private final Logger log = LoggerFactory.getLogger(BamsUserService.class);

    private final BamsUserRepository bamsUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final BamsUserSearchRepository bamsUserSearchRepository;

    public BamsUserService(BamsUserRepository bamsUserRepository, PasswordEncoder passwordEncoder, BamsUserSearchRepository bamsUserSearchRepository) {
        this.bamsUserRepository = bamsUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.bamsUserSearchRepository = bamsUserSearchRepository;
    }

    /**
     * Save a bamsUser.
     *
     * @param bamsUser the entity to save
     * @return the persisted entity
     */
    public BamsUser save(BamsUser bamsUser) {
        log.debug("Request to save BamsUser : {}", bamsUser);
        String encryptedPassword = passwordEncoder.encode(bamsUser.getPasswordhash());
        bamsUser.setPasswordhash(encryptedPassword);
        BamsUser result = bamsUserRepository.save(bamsUser);
        bamsUserSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the bamsUsers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BamsUser> findAll(Pageable pageable) {
        log.debug("Request to get all BamsUsers");
        Page<BamsUser> result = bamsUserRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one bamsUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BamsUser findOne(Long id) {
        log.debug("Request to get BamsUser : {}", id);
        BamsUser bamsUser = bamsUserRepository.findOne(id);
        return bamsUser;
    }

    /**
     *  Delete the  bamsUser by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BamsUser : {}", id);
        bamsUserRepository.delete(id);
        bamsUserSearchRepository.delete(id);
    }

    /**
     * Search for the bamsUser corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BamsUser> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BamsUsers for query {}", query);
        Page<BamsUser> result = bamsUserSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
