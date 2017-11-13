package com.baoviet.login.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.baoviet.login.domain.BamsAuthority;
import com.baoviet.login.service.BamsAuthorityService;
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
 * REST controller for managing BamsAuthority.
 */
@RestController
@RequestMapping("/api")
public class BamsAuthorityResource {

    private final Logger log = LoggerFactory.getLogger(BamsAuthorityResource.class);

    private static final String ENTITY_NAME = "bamsAuthority";
        
    private final BamsAuthorityService bamsAuthorityService;

    public BamsAuthorityResource(BamsAuthorityService bamsAuthorityService) {
        this.bamsAuthorityService = bamsAuthorityService;
    }

    /**
     * POST  /bams-authorities : Create a new bamsAuthority.
     *
     * @param bamsAuthority the bamsAuthority to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bamsAuthority, or with status 400 (Bad Request) if the bamsAuthority has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bams-authorities")
    @Timed
    public ResponseEntity<BamsAuthority> createBamsAuthority(@Valid @RequestBody BamsAuthority bamsAuthority) throws URISyntaxException {
        log.debug("REST request to save BamsAuthority : {}", bamsAuthority);
        if (bamsAuthority.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bamsAuthority cannot already have an ID")).body(null);
        }
        BamsAuthority result = bamsAuthorityService.save(bamsAuthority);
        return ResponseEntity.created(new URI("/api/bams-authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bams-authorities : Updates an existing bamsAuthority.
     *
     * @param bamsAuthority the bamsAuthority to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bamsAuthority,
     * or with status 400 (Bad Request) if the bamsAuthority is not valid,
     * or with status 500 (Internal Server Error) if the bamsAuthority couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bams-authorities")
    @Timed
    public ResponseEntity<BamsAuthority> updateBamsAuthority(@Valid @RequestBody BamsAuthority bamsAuthority) throws URISyntaxException {
        log.debug("REST request to update BamsAuthority : {}", bamsAuthority);
        if (bamsAuthority.getId() == null) {
            return createBamsAuthority(bamsAuthority);
        }
        BamsAuthority result = bamsAuthorityService.save(bamsAuthority);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bamsAuthority.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bams-authorities : get all the bamsAuthorities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bamsAuthorities in body
     */
    @GetMapping("/bams-authorities")
    @Timed
    public ResponseEntity<List<BamsAuthority>> getAllBamsAuthorities(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BamsAuthorities");
        Page<BamsAuthority> page = bamsAuthorityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bams-authorities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bams-authorities/:id : get the "id" bamsAuthority.
     *
     * @param id the id of the bamsAuthority to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bamsAuthority, or with status 404 (Not Found)
     */
    @GetMapping("/bams-authorities/{id}")
    @Timed
    public ResponseEntity<BamsAuthority> getBamsAuthority(@PathVariable Long id) {
        log.debug("REST request to get BamsAuthority : {}", id);
        BamsAuthority bamsAuthority = bamsAuthorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bamsAuthority));
    }

    /**
     * DELETE  /bams-authorities/:id : delete the "id" bamsAuthority.
     *
     * @param id the id of the bamsAuthority to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bams-authorities/{id}")
    @Timed
    public ResponseEntity<Void> deleteBamsAuthority(@PathVariable Long id) {
        log.debug("REST request to delete BamsAuthority : {}", id);
        bamsAuthorityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bams-authorities?query=:query : search for the bamsAuthority corresponding
     * to the query.
     *
     * @param query the query of the bamsAuthority search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/bams-authorities")
    @Timed
    public ResponseEntity<List<BamsAuthority>> searchBamsAuthorities(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of BamsAuthorities for query {}", query);
        Page<BamsAuthority> page = bamsAuthorityService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/bams-authorities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
