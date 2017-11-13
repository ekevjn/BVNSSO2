package com.baoviet.login.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.baoviet.login.domain.BamsOutlet;
import com.baoviet.login.service.BamsOutletService;
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

/**
 * REST controller for managing BamsOutlet.
 */
@RestController
@RequestMapping("/api")
public class BamsOutletResource {

    private final Logger log = LoggerFactory.getLogger(BamsOutletResource.class);

    private static final String ENTITY_NAME = "bamsOutlet";

    private final BamsOutletService bamsOutletService;

    public BamsOutletResource(BamsOutletService bamsOutletService) {
        this.bamsOutletService = bamsOutletService;
    }

    /**
     * POST  /bams-outlets : Create a new bamsOutlet.
     *
     * @param bamsOutlet the bamsOutlet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bamsOutlet, or with status 400 (Bad Request) if the bamsOutlet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bams-outlets")
    @Timed
    public ResponseEntity<BamsOutlet> createBamsOutlet(@Valid @RequestBody BamsOutlet bamsOutlet) throws URISyntaxException {
        log.debug("REST request to save BamsOutlet : {}", bamsOutlet);
        BamsOutlet check = bamsOutletService.findBydailyId(bamsOutlet.getDailyId());
        if(check != null) {
            if (bamsOutlet.getDailyId().equals(check.getDailyId())) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bamsOutlet cannot already have an ID")).body(null);
            }
        }
        BamsOutlet result = bamsOutletService.save(bamsOutlet);
        return ResponseEntity.created(new URI("/api/bams-outlets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bams-outlets : Updates an existing bamsOutlet.
     *
     * @param bamsOutlet the bamsOutlet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bamsOutlet,
     * or with status 400 (Bad Request) if the bamsOutlet is not valid,
     * or with status 500 (Internal Server Error) if the bamsOutlet couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bams-outlets")
    @Timed
    public ResponseEntity<BamsOutlet> updateBamsOutlet(@Valid @RequestBody BamsOutlet bamsOutlet) throws URISyntaxException {
        log.debug("REST request to update BamsOutlet : {}", bamsOutlet);
        BamsOutlet check = bamsOutletService.findBydailyId(bamsOutlet.getDailyId());
        if(check != null && !bamsOutlet.getId().equals(check.getId())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bamsOutlet cannot already have an ID")).body(null);
        }
        if (bamsOutlet.getId() == null) {
            return createBamsOutlet(bamsOutlet);
        }
        BamsOutlet result = bamsOutletService.save(bamsOutlet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bamsOutlet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bams-outlets : get all the bamsOutlets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bamsOutlets in body
     */
    @GetMapping("/bams-outlets")
    @Timed
    public ResponseEntity<List<BamsOutlet>> getAllBamsOutlets(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BamsOutlets");
        Page<BamsOutlet> page = bamsOutletService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bams-outlets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


  /*  @GetMapping("/bams-outlets/find")
    @Timed
    public List<BamsOutlet> getBamsOutlets(@RequestParam String bamoutlet) {
        log.debug("REST request to get a page of BamsOutlets");
        List<BamsOutlet> page = bamsOutletService.find(bamoutlet);
       // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bams-outlets");
        return page;
    }*/



   /* @GetMapping("/bams-outlets")
    @Timed
    public ResponseEntity<List<BamsOutlet>> getBamsOutlets(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BamsOutlets");
        Page<BamsOutlet> page = bamsOutletService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bams-outlets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }*/

    /**
     * GET  /bams-outlets/:id : get the "id" bamsOutlet.
     *
     * @param id the id of the bamsOutlet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bamsOutlet, or with status 404 (Not Found)
     */
    @GetMapping("/bams-outlets/{id}")
    @Timed
    public ResponseEntity<BamsOutlet> getBamsOutlet(@PathVariable Long id) {
        log.debug("REST request to get BamsOutlet : {}", id);
        BamsOutlet bamsOutlet = bamsOutletService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bamsOutlet));
    }

    /**
     * DELETE  /bams-outlets/:id : delete the "id" bamsOutlet.
     *
     * @param id the id of the bamsOutlet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bams-outlets/{id}")
    @Timed
    public ResponseEntity<Void> deleteBamsOutlet(@PathVariable Long id) {
        log.debug("REST request to delete BamsOutlet : {}", id);
        bamsOutletService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bams-outlets?query=:query : search for the bamsOutlet corresponding
     * to the query.
     *
     * @param query the query of the bamsOutlet search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/bams-outlets")
    @Timed
    public ResponseEntity<List<BamsOutlet>> searchBamsOutlets(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of BamsOutlets for query {}", query);
        Page<BamsOutlet> page = bamsOutletService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/bams-outlets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
