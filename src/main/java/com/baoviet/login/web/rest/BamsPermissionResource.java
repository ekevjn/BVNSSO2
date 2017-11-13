package com.baoviet.login.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.baoviet.login.domain.BamsPermission;
import com.baoviet.login.service.BamsPermissionService;
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
 * REST controller for managing BamsPermission.
 */
@RestController
@RequestMapping("/api")
public class BamsPermissionResource {

    private final Logger log = LoggerFactory.getLogger(BamsPermissionResource.class);

    private static final String ENTITY_NAME = "bamsPermission";
        
    private final BamsPermissionService bamsPermissionService;

    public BamsPermissionResource(BamsPermissionService bamsPermissionService) {
        this.bamsPermissionService = bamsPermissionService;
    }

    /**
     * POST  /bams-permissions : Create a new bamsPermission.
     *
     * @param bamsPermission the bamsPermission to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bamsPermission, or with status 400 (Bad Request) if the bamsPermission has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bams-permissions")
    @Timed
    public ResponseEntity<BamsPermission> createBamsPermission(@Valid @RequestBody BamsPermission bamsPermission) throws URISyntaxException {
        log.debug("REST request to save BamsPermission : {}", bamsPermission);
        if (bamsPermission.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bamsPermission cannot already have an ID")).body(null);
        }
        BamsPermission result = bamsPermissionService.save(bamsPermission);
        return ResponseEntity.created(new URI("/api/bams-permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bams-permissions : Updates an existing bamsPermission.
     *
     * @param bamsPermission the bamsPermission to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bamsPermission,
     * or with status 400 (Bad Request) if the bamsPermission is not valid,
     * or with status 500 (Internal Server Error) if the bamsPermission couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bams-permissions")
    @Timed
    public ResponseEntity<BamsPermission> updateBamsPermission(@Valid @RequestBody BamsPermission bamsPermission) throws URISyntaxException {
        log.debug("REST request to update BamsPermission : {}", bamsPermission);
        if (bamsPermission.getId() == null) {
            return createBamsPermission(bamsPermission);
        }
        BamsPermission result = bamsPermissionService.save(bamsPermission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bamsPermission.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bams-permissions : get all the bamsPermissions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bamsPermissions in body
     */
    @GetMapping("/bams-permissions")
    @Timed
    public ResponseEntity<List<BamsPermission>> getAllBamsPermissions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BamsPermissions");
        Page<BamsPermission> page = bamsPermissionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bams-permissions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bams-permissions/:id : get the "id" bamsPermission.
     *
     * @param id the id of the bamsPermission to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bamsPermission, or with status 404 (Not Found)
     */
    @GetMapping("/bams-permissions/{id}")
    @Timed
    public ResponseEntity<BamsPermission> getBamsPermission(@PathVariable Long id) {
        log.debug("REST request to get BamsPermission : {}", id);
        BamsPermission bamsPermission = bamsPermissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bamsPermission));
    }

    /**
     * DELETE  /bams-permissions/:id : delete the "id" bamsPermission.
     *
     * @param id the id of the bamsPermission to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bams-permissions/{id}")
    @Timed
    public ResponseEntity<Void> deleteBamsPermission(@PathVariable Long id) {
        log.debug("REST request to delete BamsPermission : {}", id);
        bamsPermissionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bams-permissions?query=:query : search for the bamsPermission corresponding
     * to the query.
     *
     * @param query the query of the bamsPermission search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/bams-permissions")
    @Timed
    public ResponseEntity<List<BamsPermission>> searchBamsPermissions(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of BamsPermissions for query {}", query);
        Page<BamsPermission> page = bamsPermissionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/bams-permissions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
