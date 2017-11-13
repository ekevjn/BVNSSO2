package com.baoviet.login.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.baoviet.login.domain.BamsUser;
import com.baoviet.login.service.BamsUserService;
import com.baoviet.login.web.rest.util.HeaderUtil;
import com.baoviet.login.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing BamsUser.
 */
@RestController
@RequestMapping("/api")
public class BamsUserResource {

    private final Logger log = LoggerFactory.getLogger(BamsUserResource.class);

    private static final String ENTITY_NAME = "bamsUser";
        
    private final BamsUserService bamsUserService;

    public BamsUserResource(BamsUserService bamsUserService) {
        this.bamsUserService = bamsUserService;
    }

    /**
     * POST  /bams-users : Create a new bamsUser.
     *
     * @param bamsUser the bamsUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bamsUser, or with status 400 (Bad Request) if the bamsUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bams-users")
    @Timed
    public ResponseEntity<BamsUser> createBamsUser(@Valid @RequestBody BamsUser bamsUser) throws URISyntaxException {
        log.debug("REST request to save BamsUser : {}", bamsUser);
        if (bamsUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bamsUser cannot already have an ID")).body(null);
        }
        BamsUser result = bamsUserService.save(bamsUser);
        return ResponseEntity.created(new URI("/api/bams-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bams-users : Updates an existing bamsUser.
     *
     * @param bamsUser the bamsUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bamsUser,
     * or with status 400 (Bad Request) if the bamsUser is not valid,
     * or with status 500 (Internal Server Error) if the bamsUser couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bams-users")
    @Timed
    public ResponseEntity<BamsUser> updateBamsUser(@Valid @RequestBody BamsUser bamsUser) throws URISyntaxException {
        log.debug("REST request to update BamsUser : {}", bamsUser);
        if (bamsUser.getId() == null) {
            return createBamsUser(bamsUser);
        }
        BamsUser result = bamsUserService.save(bamsUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bamsUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bams-users : get all the bamsUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bamsUsers in body
     */
    @GetMapping("/bams-users")
    @Timed
    public ResponseEntity<List<BamsUser>> getAllBamsUsers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BamsUsers");
        Page<BamsUser> page = bamsUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bams-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bams-users/:id : get the "id" bamsUser.
     *
     * @param id the id of the bamsUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bamsUser, or with status 404 (Not Found)
     */
    @GetMapping("/bams-users/{id}")
    @Timed
    public ResponseEntity<BamsUser> getBamsUser(@PathVariable Long id) {
        log.debug("REST request to get BamsUser : {}", id);
        BamsUser bamsUser = bamsUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bamsUser));
    }

    /**
     * DELETE  /bams-users/:id : delete the "id" bamsUser.
     *
     * @param id the id of the bamsUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bams-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteBamsUser(@PathVariable Long id) {
        log.debug("REST request to delete BamsUser : {}", id);
        bamsUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bams-users?query=:query : search for the bamsUser corresponding
     * to the query.
     *
     * @param query the query of the bamsUser search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/bams-users")
    @Timed
    public ResponseEntity<List<BamsUser>> searchBamsUsers(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of BamsUsers for query {}", query);
        Page<BamsUser> page = bamsUserService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/bams-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
