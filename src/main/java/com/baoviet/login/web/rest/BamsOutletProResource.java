package com.baoviet.login.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.baoviet.login.domain.BamsOutletPro;
import com.baoviet.login.service.BamsOutletProService;
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
 * REST controller for managing BamsOutletPro.
 */
@RestController
@RequestMapping("/api")
public class BamsOutletProResource {

    private final Logger log = LoggerFactory.getLogger(BamsOutletProResource.class);

    private static final String ENTITY_NAME = "bamsOutletPro";

    private final BamsOutletProService bamsOutletProService;

    public BamsOutletProResource(BamsOutletProService bamsOutletProService) {
        this.bamsOutletProService = bamsOutletProService;
    }

    /**
     * POST  /bams-outlet-pros : Create a new bamsOutletPro.
     *
     * @param bamsOutletPro the bamsOutletPro to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bamsOutletPro, or with status 400 (Bad Request) if the bamsOutletPro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bams-outlet-pros")
    @Timed
    public ResponseEntity<BamsOutletPro> createBamsOutletPro(@Valid @RequestBody BamsOutletPro bamsOutletPro) throws URISyntaxException {
        log.debug("REST request to save BamsOutletPro : {}", bamsOutletPro);
        BamsOutletPro check = bamsOutletProService.findByproductcode(bamsOutletPro.getProductcode());
        if(check != null){
            if (bamsOutletPro.getProductcode().equals(check.getProductcode())) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bamsOutletPro cannot already have an ID")).body(null);
            }
        }
        if((bamsOutletPro.getEffectivedate()).compareTo(bamsOutletPro.getCeasedate()) > 0 ){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "datecompare", "A new bamsOutletPro cannot already have an ID")).body(null);
        }
        BamsOutletPro result = bamsOutletProService.save(bamsOutletPro);
        return ResponseEntity.created(new URI("/api/bams-outlet-pros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bams-outlet-pros : Updates an existing bamsOutletPro.
     *
     * @param bamsOutletPro the bamsOutletPro to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bamsOutletPro,
     * or with status 400 (Bad Request) if the bamsOutletPro is not valid,
     * or with status 500 (Internal Server Error) if the bamsOutletPro couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bams-outlet-pros")
    @Timed
    public ResponseEntity<BamsOutletPro> updateBamsOutletPro(@Valid @RequestBody BamsOutletPro bamsOutletPro) throws URISyntaxException {
        log.debug("REST request to update BamsOutletPro : {}", bamsOutletPro);
        BamsOutletPro check = bamsOutletProService.findByproductcode(bamsOutletPro.getProductcode());
        if(check != null && !bamsOutletPro.getId().equals(check.getId())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bamsOutletPro cannot already have an ID")).body(null);
        }
        if (bamsOutletPro.getId() == null) {
            return createBamsOutletPro(bamsOutletPro);
        }
        if((bamsOutletPro.getEffectivedate()).compareTo(bamsOutletPro.getCeasedate()) > 0 ){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "datecompare", "A new bamsOutletPro cannot already have an ID")).body(null);
        }
        BamsOutletPro result = bamsOutletProService.save(bamsOutletPro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bamsOutletPro.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bams-outlet-pros : get all the bamsOutletPros.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bamsOutletPros in body
     */
    @GetMapping("/bams-outlet-pros")
    @Timed
    public ResponseEntity<List<BamsOutletPro>> getAllBamsOutletPros(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BamsOutletPros");
        Page<BamsOutletPro> page = bamsOutletProService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bams-outlet-pros");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /bams-outlet-pros/:id : get the "id" bamsOutletPro.
     *
     * @param id the id of the bamsOutletPro to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bamsOutletPro, or with status 404 (Not Found)
     */
    @GetMapping("/bams-outlet-pros/{id}")
    @Timed
    public ResponseEntity<BamsOutletPro> getBamsOutletPro(@PathVariable Long id) {
        log.debug("REST request to get BamsOutletPro : {}", id);
        BamsOutletPro bamsOutletPro = bamsOutletProService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bamsOutletPro));
    }

    /**
     * DELETE  /bams-outlet-pros/:id : delete the "id" bamsOutletPro.
     *
     * @param id the id of the bamsOutletPro to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bams-outlet-pros/{id}")
    @Timed
    public ResponseEntity<Void> deleteBamsOutletPro(@PathVariable Long id) {
        log.debug("REST request to delete BamsOutletPro : {}", id);
        bamsOutletProService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bams-outlet-pros?query=:query : search for the bamsOutletPro corresponding
     * to the query.
     *
     * @param query the query of the bamsOutletPro search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/bams-outlet-pros")
    @Timed
    public ResponseEntity<List<BamsOutletPro>> searchBamsOutletPros(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of BamsOutletPros for query {}", query);
        Page<BamsOutletPro> page = bamsOutletProService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/bams-outlet-pros");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
