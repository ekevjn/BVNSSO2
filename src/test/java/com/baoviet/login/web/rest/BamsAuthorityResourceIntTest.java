package com.baoviet.login.web.rest;

import com.baoviet.login.BvloginApp;

import com.baoviet.login.domain.BamsAuthority;
import com.baoviet.login.repository.BamsAuthorityRepository;
import com.baoviet.login.service.BamsAuthorityService;
import com.baoviet.login.repository.search.BamsAuthoritySearchRepository;
import com.baoviet.login.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BamsAuthorityResource REST controller.
 *
 * @see BamsAuthorityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BvloginApp.class)
public class BamsAuthorityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_CREATEDBY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDBY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATEDDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATEDDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LASTMODIFIEDBY = "AAAAAAAAAA";
    private static final String UPDATED_LASTMODIFIEDBY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LASTMODIFIEDDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LASTMODIFIEDDATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BamsAuthorityRepository bamsAuthorityRepository;

    @Autowired
    private BamsAuthorityService bamsAuthorityService;

    @Autowired
    private BamsAuthoritySearchRepository bamsAuthoritySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBamsAuthorityMockMvc;

    private BamsAuthority bamsAuthority;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BamsAuthorityResource bamsAuthorityResource = new BamsAuthorityResource(bamsAuthorityService);
        this.restBamsAuthorityMockMvc = MockMvcBuilders.standaloneSetup(bamsAuthorityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BamsAuthority createEntity(EntityManager em) {
        BamsAuthority bamsAuthority = new BamsAuthority()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .activated(DEFAULT_ACTIVATED)
            .createdby(DEFAULT_CREATEDBY)
            .createddate(DEFAULT_CREATEDDATE)
            .lastmodifiedby(DEFAULT_LASTMODIFIEDBY)
            .lastmodifieddate(DEFAULT_LASTMODIFIEDDATE);
        return bamsAuthority;
    }

    @Before
    public void initTest() {
        bamsAuthoritySearchRepository.deleteAll();
        bamsAuthority = createEntity(em);
    }

    @Test
    @Transactional
    public void createBamsAuthority() throws Exception {
        int databaseSizeBeforeCreate = bamsAuthorityRepository.findAll().size();

        // Create the BamsAuthority
        restBamsAuthorityMockMvc.perform(post("/api/bams-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsAuthority)))
            .andExpect(status().isCreated());

        // Validate the BamsAuthority in the database
        List<BamsAuthority> bamsAuthorityList = bamsAuthorityRepository.findAll();
        assertThat(bamsAuthorityList).hasSize(databaseSizeBeforeCreate + 1);
        BamsAuthority testBamsAuthority = bamsAuthorityList.get(bamsAuthorityList.size() - 1);
        assertThat(testBamsAuthority.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBamsAuthority.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBamsAuthority.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testBamsAuthority.getCreatedby()).isEqualTo(DEFAULT_CREATEDBY);
        assertThat(testBamsAuthority.getCreateddate()).isEqualTo(DEFAULT_CREATEDDATE);
        assertThat(testBamsAuthority.getLastmodifiedby()).isEqualTo(DEFAULT_LASTMODIFIEDBY);
        assertThat(testBamsAuthority.getLastmodifieddate()).isEqualTo(DEFAULT_LASTMODIFIEDDATE);

        // Validate the BamsAuthority in Elasticsearch
        BamsAuthority bamsAuthorityEs = bamsAuthoritySearchRepository.findOne(testBamsAuthority.getId());
        assertThat(bamsAuthorityEs).isEqualToComparingFieldByField(testBamsAuthority);
    }

    @Test
    @Transactional
    public void createBamsAuthorityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bamsAuthorityRepository.findAll().size();

        // Create the BamsAuthority with an existing ID
        bamsAuthority.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBamsAuthorityMockMvc.perform(post("/api/bams-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsAuthority)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BamsAuthority> bamsAuthorityList = bamsAuthorityRepository.findAll();
        assertThat(bamsAuthorityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bamsAuthorityRepository.findAll().size();
        // set the field null
        bamsAuthority.setName(null);

        // Create the BamsAuthority, which fails.

        restBamsAuthorityMockMvc.perform(post("/api/bams-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsAuthority)))
            .andExpect(status().isBadRequest());

        List<BamsAuthority> bamsAuthorityList = bamsAuthorityRepository.findAll();
        assertThat(bamsAuthorityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBamsAuthorities() throws Exception {
        // Initialize the database
        bamsAuthorityRepository.saveAndFlush(bamsAuthority);

        // Get all the bamsAuthorityList
        restBamsAuthorityMockMvc.perform(get("/api/bams-authorities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bamsAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdby").value(hasItem(DEFAULT_CREATEDBY.toString())))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(DEFAULT_CREATEDDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifiedby").value(hasItem(DEFAULT_LASTMODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifieddate").value(hasItem(DEFAULT_LASTMODIFIEDDATE.toString())));
    }

    @Test
    @Transactional
    public void getBamsAuthority() throws Exception {
        // Initialize the database
        bamsAuthorityRepository.saveAndFlush(bamsAuthority);

        // Get the bamsAuthority
        restBamsAuthorityMockMvc.perform(get("/api/bams-authorities/{id}", bamsAuthority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bamsAuthority.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdby").value(DEFAULT_CREATEDBY.toString()))
            .andExpect(jsonPath("$.createddate").value(DEFAULT_CREATEDDATE.toString()))
            .andExpect(jsonPath("$.lastmodifiedby").value(DEFAULT_LASTMODIFIEDBY.toString()))
            .andExpect(jsonPath("$.lastmodifieddate").value(DEFAULT_LASTMODIFIEDDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBamsAuthority() throws Exception {
        // Get the bamsAuthority
        restBamsAuthorityMockMvc.perform(get("/api/bams-authorities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBamsAuthority() throws Exception {
        // Initialize the database
        bamsAuthorityService.save(bamsAuthority);

        int databaseSizeBeforeUpdate = bamsAuthorityRepository.findAll().size();

        // Update the bamsAuthority
        BamsAuthority updatedBamsAuthority = bamsAuthorityRepository.findOne(bamsAuthority.getId());
        updatedBamsAuthority
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .activated(UPDATED_ACTIVATED)
            .createdby(UPDATED_CREATEDBY)
            .createddate(UPDATED_CREATEDDATE)
            .lastmodifiedby(UPDATED_LASTMODIFIEDBY)
            .lastmodifieddate(UPDATED_LASTMODIFIEDDATE);

        restBamsAuthorityMockMvc.perform(put("/api/bams-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBamsAuthority)))
            .andExpect(status().isOk());

        // Validate the BamsAuthority in the database
        List<BamsAuthority> bamsAuthorityList = bamsAuthorityRepository.findAll();
        assertThat(bamsAuthorityList).hasSize(databaseSizeBeforeUpdate);
        BamsAuthority testBamsAuthority = bamsAuthorityList.get(bamsAuthorityList.size() - 1);
        assertThat(testBamsAuthority.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBamsAuthority.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBamsAuthority.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testBamsAuthority.getCreatedby()).isEqualTo(UPDATED_CREATEDBY);
        assertThat(testBamsAuthority.getCreateddate()).isEqualTo(UPDATED_CREATEDDATE);
        assertThat(testBamsAuthority.getLastmodifiedby()).isEqualTo(UPDATED_LASTMODIFIEDBY);
        assertThat(testBamsAuthority.getLastmodifieddate()).isEqualTo(UPDATED_LASTMODIFIEDDATE);

        // Validate the BamsAuthority in Elasticsearch
        BamsAuthority bamsAuthorityEs = bamsAuthoritySearchRepository.findOne(testBamsAuthority.getId());
        assertThat(bamsAuthorityEs).isEqualToComparingFieldByField(testBamsAuthority);
    }

    @Test
    @Transactional
    public void updateNonExistingBamsAuthority() throws Exception {
        int databaseSizeBeforeUpdate = bamsAuthorityRepository.findAll().size();

        // Create the BamsAuthority

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBamsAuthorityMockMvc.perform(put("/api/bams-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsAuthority)))
            .andExpect(status().isCreated());

        // Validate the BamsAuthority in the database
        List<BamsAuthority> bamsAuthorityList = bamsAuthorityRepository.findAll();
        assertThat(bamsAuthorityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBamsAuthority() throws Exception {
        // Initialize the database
        bamsAuthorityService.save(bamsAuthority);

        int databaseSizeBeforeDelete = bamsAuthorityRepository.findAll().size();

        // Get the bamsAuthority
        restBamsAuthorityMockMvc.perform(delete("/api/bams-authorities/{id}", bamsAuthority.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bamsAuthorityExistsInEs = bamsAuthoritySearchRepository.exists(bamsAuthority.getId());
        assertThat(bamsAuthorityExistsInEs).isFalse();

        // Validate the database is empty
        List<BamsAuthority> bamsAuthorityList = bamsAuthorityRepository.findAll();
        assertThat(bamsAuthorityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBamsAuthority() throws Exception {
        // Initialize the database
        bamsAuthorityService.save(bamsAuthority);

        // Search the bamsAuthority
        restBamsAuthorityMockMvc.perform(get("/api/_search/bams-authorities?query=id:" + bamsAuthority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bamsAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdby").value(hasItem(DEFAULT_CREATEDBY.toString())))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(DEFAULT_CREATEDDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifiedby").value(hasItem(DEFAULT_LASTMODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifieddate").value(hasItem(DEFAULT_LASTMODIFIEDDATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BamsAuthority.class);
    }
}
