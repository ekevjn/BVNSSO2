package com.baoviet.login.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.baoviet.login.domain.BamsCompany;
import com.baoviet.login.service.BamsCompanyService;
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
 * REST controller for managing BamsCompany.
 */
@RestController
@RequestMapping("/api")
public class BamsCompanyResource {

    private final Logger log = LoggerFactory.getLogger(BamsCompanyResource.class);

    private static final String ENTITY_NAME = "bamsCompany";

    private final BamsCompanyService bamsCompanyService;

    public BamsCompanyResource(BamsCompanyService bamsCompanyService) {
        this.bamsCompanyService = bamsCompanyService;
    }

    /**
     * POST  /bams-companies : Create a new bamsCompany.
     *
     * @param bamsCompany the bamsCompany to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bamsCompany, or with status 400 (Bad Request) if the bamsCompany has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bams-companies")
    @Timed
    public ResponseEntity<BamsCompany> createBamsCompany(@Valid @RequestBody BamsCompany bamsCompany) throws URISyntaxException {
        log.debug("REST request to save BamsCompany : {}", bamsCompany);
        BamsCompany check = bamsCompanyService.findBymatinh(bamsCompany.getMatinh());
        if(check != null){
            if (bamsCompany.getMatinh().equals(check.getMatinh())) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bamsCompany cannot already have an ID")).body(null);
            }
        }
        BamsCompany result = bamsCompanyService.save(bamsCompany);
        return ResponseEntity.created(new URI("/api/bams-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bams-companies : Updates an existing bamsCompany.
     *
     * @param bamsCompany the bamsCompany to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bamsCompany,
     * or with status 400 (Bad Request) if the bamsCompany is not valid,
     * or with status 500 (Internal Server Error) if the bamsCompany couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bams-companies")
    @Timed
    public ResponseEntity<BamsCompany> updateBamsCompany(@Valid @RequestBody BamsCompany bamsCompany) throws URISyntaxException {
        log.debug("REST request to update BamsCompany : {}", bamsCompany);
        BamsCompany check = bamsCompanyService.findBymatinh(bamsCompany.getMatinh());
        if(check != null && !bamsCompany.getId().equals(check.getId())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bamsCompany cannot already have an ID")).body(null);
        }
        if (bamsCompany.getId() == null) {
            return createBamsCompany(bamsCompany);
        }
        BamsCompany result = bamsCompanyService.save(bamsCompany);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bamsCompany.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bams-companies : get all the bamsCompanies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bamsCompanies in body
     */
    @GetMapping("/bams-companies")
    @Timed
    public ResponseEntity<List<BamsCompany>> getAllBamsCompanies(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BamsCompanies");
        Page<BamsCompany> page = bamsCompanyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bams-companies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bams-companies/:id : get the "id" bamsCompany.
     *
     * @param id the id of the bamsCompany to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bamsCompany, or with status 404 (Not Found)
     */
    @GetMapping("/bams-companies/{id}")
    @Timed
    public ResponseEntity<BamsCompany> getBamsCompany(@PathVariable Long id) {
        log.debug("REST request to get BamsCompany : {}", id);
        BamsCompany bamsCompany = bamsCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bamsCompany));
    }

    /**
     * DELETE  /bams-companies/:id : delete the "id" bamsCompany.
     *
     * @param id the id of the bamsCompany to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bams-companies/{id}")
    @Timed
    public ResponseEntity<Void> deleteBamsCompany(@PathVariable Long id) {
        log.debug("REST request to delete BamsCompany : {}", id);
        bamsCompanyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bams-companies?query=:query : search for the bamsCompany corresponding
     * to the query.
     *
     * @param query the query of the bamsCompany search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/bams-companies")
    @Timed
    public ResponseEntity<List<BamsCompany>> searchBamsCompanies(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of BamsCompanies for query {}", query);
        Page<BamsCompany> page = bamsCompanyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/bams-companies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
