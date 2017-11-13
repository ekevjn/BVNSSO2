package com.baoviet.login.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.baoviet.login.domain.BamsGenerCode;
import com.baoviet.login.service.BamsGenerCodeService;
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
 * REST controller for managing BamsGenerCode.
 */
@RestController
@RequestMapping("/api")
public class BamsGenerCodeResource {

    private final Logger log = LoggerFactory.getLogger(BamsGenerCodeResource.class);

    private static final String ENTITY_NAME = "bamsGenerCode";
        
    private final BamsGenerCodeService bamsGenerCodeService;

    public BamsGenerCodeResource(BamsGenerCodeService bamsGenerCodeService) {
        this.bamsGenerCodeService = bamsGenerCodeService;
    }

    /**
     * POST  /bams-gener-codes : Create a new bamsGenerCode.
     *
     * @param bamsGenerCode the bamsGenerCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bamsGenerCode, or with status 400 (Bad Request) if the bamsGenerCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bams-gener-codes")
    @Timed
    public ResponseEntity<BamsGenerCode> createBamsGenerCode(@Valid @RequestBody BamsGenerCode bamsGenerCode) throws URISyntaxException {
        log.debug("REST request to save BamsGenerCode : {}", bamsGenerCode);
        if (bamsGenerCode.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bamsGenerCode cannot already have an ID")).body(null);
        }
        BamsGenerCode result = bamsGenerCodeService.save(bamsGenerCode);
        return ResponseEntity.created(new URI("/api/bams-gener-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bams-gener-codes : Updates an existing bamsGenerCode.
     *
     * @param bamsGenerCode the bamsGenerCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bamsGenerCode,
     * or with status 400 (Bad Request) if the bamsGenerCode is not valid,
     * or with status 500 (Internal Server Error) if the bamsGenerCode couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bams-gener-codes")
    @Timed
    public ResponseEntity<BamsGenerCode> updateBamsGenerCode(@Valid @RequestBody BamsGenerCode bamsGenerCode) throws URISyntaxException {
        log.debug("REST request to update BamsGenerCode : {}", bamsGenerCode);
        if (bamsGenerCode.getId() == null) {
            return createBamsGenerCode(bamsGenerCode);
        }
        BamsGenerCode result = bamsGenerCodeService.save(bamsGenerCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bamsGenerCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bams-gener-codes : get all the bamsGenerCodes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bamsGenerCodes in body
     */
    @GetMapping("/bams-gener-codes")
    @Timed
    public ResponseEntity<List<BamsGenerCode>> getAllBamsGenerCodes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BamsGenerCodes");
        Page<BamsGenerCode> page = bamsGenerCodeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bams-gener-codes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bams-gener-codes/:id : get the "id" bamsGenerCode.
     *
     * @param id the id of the bamsGenerCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bamsGenerCode, or with status 404 (Not Found)
     */
    @GetMapping("/bams-gener-codes/{id}")
    @Timed
    public ResponseEntity<BamsGenerCode> getBamsGenerCode(@PathVariable Long id) {
        log.debug("REST request to get BamsGenerCode : {}", id);
        BamsGenerCode bamsGenerCode = bamsGenerCodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bamsGenerCode));
    }

    /**
     * DELETE  /bams-gener-codes/:id : delete the "id" bamsGenerCode.
     *
     * @param id the id of the bamsGenerCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bams-gener-codes/{id}")
    @Timed
    public ResponseEntity<Void> deleteBamsGenerCode(@PathVariable Long id) {
        log.debug("REST request to delete BamsGenerCode : {}", id);
        bamsGenerCodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bams-gener-codes?query=:query : search for the bamsGenerCode corresponding
     * to the query.
     *
     * @param query the query of the bamsGenerCode search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/bams-gener-codes")
    @Timed
    public ResponseEntity<List<BamsGenerCode>> searchBamsGenerCodes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of BamsGenerCodes for query {}", query);
        Page<BamsGenerCode> page = bamsGenerCodeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/bams-gener-codes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
