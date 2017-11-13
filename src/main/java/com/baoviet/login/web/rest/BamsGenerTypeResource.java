package com.baoviet.login.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.baoviet.login.domain.BamsGenerType;
import com.baoviet.login.service.BamsGenerTypeService;
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
 * REST controller for managing BamsGenerType.
 */
@RestController
@RequestMapping("/api")
public class BamsGenerTypeResource {

    private final Logger log = LoggerFactory.getLogger(BamsGenerTypeResource.class);

    private static final String ENTITY_NAME = "bamsGenerType";
        
    private final BamsGenerTypeService bamsGenerTypeService;

    public BamsGenerTypeResource(BamsGenerTypeService bamsGenerTypeService) {
        this.bamsGenerTypeService = bamsGenerTypeService;
    }

    /**
     * POST  /bams-gener-types : Create a new bamsGenerType.
     *
     * @param bamsGenerType the bamsGenerType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bamsGenerType, or with status 400 (Bad Request) if the bamsGenerType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bams-gener-types")
    @Timed
    public ResponseEntity<BamsGenerType> createBamsGenerType(@Valid @RequestBody BamsGenerType bamsGenerType) throws URISyntaxException {
        log.debug("REST request to save BamsGenerType : {}", bamsGenerType);
        if (bamsGenerType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bamsGenerType cannot already have an ID")).body(null);
        }
        BamsGenerType result = bamsGenerTypeService.save(bamsGenerType);
        return ResponseEntity.created(new URI("/api/bams-gener-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bams-gener-types : Updates an existing bamsGenerType.
     *
     * @param bamsGenerType the bamsGenerType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bamsGenerType,
     * or with status 400 (Bad Request) if the bamsGenerType is not valid,
     * or with status 500 (Internal Server Error) if the bamsGenerType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bams-gener-types")
    @Timed
    public ResponseEntity<BamsGenerType> updateBamsGenerType(@Valid @RequestBody BamsGenerType bamsGenerType) throws URISyntaxException {
        log.debug("REST request to update BamsGenerType : {}", bamsGenerType);
        if (bamsGenerType.getId() == null) {
            return createBamsGenerType(bamsGenerType);
        }
        BamsGenerType result = bamsGenerTypeService.save(bamsGenerType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bamsGenerType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bams-gener-types : get all the bamsGenerTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bamsGenerTypes in body
     */
    @GetMapping("/bams-gener-types")
    @Timed
    public ResponseEntity<List<BamsGenerType>> getAllBamsGenerTypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BamsGenerTypes");
        Page<BamsGenerType> page = bamsGenerTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bams-gener-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bams-gener-types/:id : get the "id" bamsGenerType.
     *
     * @param id the id of the bamsGenerType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bamsGenerType, or with status 404 (Not Found)
     */
    @GetMapping("/bams-gener-types/{id}")
    @Timed
    public ResponseEntity<BamsGenerType> getBamsGenerType(@PathVariable Long id) {
        log.debug("REST request to get BamsGenerType : {}", id);
        BamsGenerType bamsGenerType = bamsGenerTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bamsGenerType));
    }

    /**
     * DELETE  /bams-gener-types/:id : delete the "id" bamsGenerType.
     *
     * @param id the id of the bamsGenerType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bams-gener-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteBamsGenerType(@PathVariable Long id) {
        log.debug("REST request to delete BamsGenerType : {}", id);
        bamsGenerTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bams-gener-types?query=:query : search for the bamsGenerType corresponding
     * to the query.
     *
     * @param query the query of the bamsGenerType search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/bams-gener-types")
    @Timed
    public ResponseEntity<List<BamsGenerType>> searchBamsGenerTypes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of BamsGenerTypes for query {}", query);
        Page<BamsGenerType> page = bamsGenerTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/bams-gener-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
