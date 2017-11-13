package com.baoviet.login.web.rest;

import com.baoviet.login.BvloginApp;

import com.baoviet.login.domain.BamsPermission;
import com.baoviet.login.repository.BamsPermissionRepository;
import com.baoviet.login.service.BamsPermissionService;
import com.baoviet.login.repository.search.BamsPermissionSearchRepository;
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
 * Test class for the BamsPermissionResource REST controller.
 *
 * @see BamsPermissionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BvloginApp.class)
public class BamsPermissionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATEDBY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDBY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATEDDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATEDDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LASTMODIFIEDBY = "AAAAAAAAAA";
    private static final String UPDATED_LASTMODIFIEDBY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LASTMODIFIEDDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LASTMODIFIEDDATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BamsPermissionRepository bamsPermissionRepository;

    @Autowired
    private BamsPermissionService bamsPermissionService;

    @Autowired
    private BamsPermissionSearchRepository bamsPermissionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBamsPermissionMockMvc;

    private BamsPermission bamsPermission;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BamsPermissionResource bamsPermissionResource = new BamsPermissionResource(bamsPermissionService);
        this.restBamsPermissionMockMvc = MockMvcBuilders.standaloneSetup(bamsPermissionResource)
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
    public static BamsPermission createEntity(EntityManager em) {
        BamsPermission bamsPermission = new BamsPermission()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createdby(DEFAULT_CREATEDBY)
            .createddate(DEFAULT_CREATEDDATE)
            .lastmodifiedby(DEFAULT_LASTMODIFIEDBY)
            .lastmodifieddate(DEFAULT_LASTMODIFIEDDATE);
        return bamsPermission;
    }

    @Before
    public void initTest() {
        bamsPermissionSearchRepository.deleteAll();
        bamsPermission = createEntity(em);
    }

    @Test
    @Transactional
    public void createBamsPermission() throws Exception {
        int databaseSizeBeforeCreate = bamsPermissionRepository.findAll().size();

        // Create the BamsPermission
        restBamsPermissionMockMvc.perform(post("/api/bams-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsPermission)))
            .andExpect(status().isCreated());

        // Validate the BamsPermission in the database
        List<BamsPermission> bamsPermissionList = bamsPermissionRepository.findAll();
        assertThat(bamsPermissionList).hasSize(databaseSizeBeforeCreate + 1);
        BamsPermission testBamsPermission = bamsPermissionList.get(bamsPermissionList.size() - 1);
        assertThat(testBamsPermission.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBamsPermission.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBamsPermission.getCreatedby()).isEqualTo(DEFAULT_CREATEDBY);
        assertThat(testBamsPermission.getCreateddate()).isEqualTo(DEFAULT_CREATEDDATE);
        assertThat(testBamsPermission.getLastmodifiedby()).isEqualTo(DEFAULT_LASTMODIFIEDBY);
        assertThat(testBamsPermission.getLastmodifieddate()).isEqualTo(DEFAULT_LASTMODIFIEDDATE);

        // Validate the BamsPermission in Elasticsearch
        BamsPermission bamsPermissionEs = bamsPermissionSearchRepository.findOne(testBamsPermission.getId());
        assertThat(bamsPermissionEs).isEqualToComparingFieldByField(testBamsPermission);
    }

    @Test
    @Transactional
    public void createBamsPermissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bamsPermissionRepository.findAll().size();

        // Create the BamsPermission with an existing ID
        bamsPermission.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBamsPermissionMockMvc.perform(post("/api/bams-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsPermission)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BamsPermission> bamsPermissionList = bamsPermissionRepository.findAll();
        assertThat(bamsPermissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bamsPermissionRepository.findAll().size();
        // set the field null
        bamsPermission.setName(null);

        // Create the BamsPermission, which fails.

        restBamsPermissionMockMvc.perform(post("/api/bams-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsPermission)))
            .andExpect(status().isBadRequest());

        List<BamsPermission> bamsPermissionList = bamsPermissionRepository.findAll();
        assertThat(bamsPermissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBamsPermissions() throws Exception {
        // Initialize the database
        bamsPermissionRepository.saveAndFlush(bamsPermission);

        // Get all the bamsPermissionList
        restBamsPermissionMockMvc.perform(get("/api/bams-permissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bamsPermission.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createdby").value(hasItem(DEFAULT_CREATEDBY.toString())))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(DEFAULT_CREATEDDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifiedby").value(hasItem(DEFAULT_LASTMODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifieddate").value(hasItem(DEFAULT_LASTMODIFIEDDATE.toString())));
    }

    @Test
    @Transactional
    public void getBamsPermission() throws Exception {
        // Initialize the database
        bamsPermissionRepository.saveAndFlush(bamsPermission);

        // Get the bamsPermission
        restBamsPermissionMockMvc.perform(get("/api/bams-permissions/{id}", bamsPermission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bamsPermission.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createdby").value(DEFAULT_CREATEDBY.toString()))
            .andExpect(jsonPath("$.createddate").value(DEFAULT_CREATEDDATE.toString()))
            .andExpect(jsonPath("$.lastmodifiedby").value(DEFAULT_LASTMODIFIEDBY.toString()))
            .andExpect(jsonPath("$.lastmodifieddate").value(DEFAULT_LASTMODIFIEDDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBamsPermission() throws Exception {
        // Get the bamsPermission
        restBamsPermissionMockMvc.perform(get("/api/bams-permissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBamsPermission() throws Exception {
        // Initialize the database
        bamsPermissionService.save(bamsPermission);

        int databaseSizeBeforeUpdate = bamsPermissionRepository.findAll().size();

        // Update the bamsPermission
        BamsPermission updatedBamsPermission = bamsPermissionRepository.findOne(bamsPermission.getId());
        updatedBamsPermission
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdby(UPDATED_CREATEDBY)
            .createddate(UPDATED_CREATEDDATE)
            .lastmodifiedby(UPDATED_LASTMODIFIEDBY)
            .lastmodifieddate(UPDATED_LASTMODIFIEDDATE);

        restBamsPermissionMockMvc.perform(put("/api/bams-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBamsPermission)))
            .andExpect(status().isOk());

        // Validate the BamsPermission in the database
        List<BamsPermission> bamsPermissionList = bamsPermissionRepository.findAll();
        assertThat(bamsPermissionList).hasSize(databaseSizeBeforeUpdate);
        BamsPermission testBamsPermission = bamsPermissionList.get(bamsPermissionList.size() - 1);
        assertThat(testBamsPermission.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBamsPermission.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBamsPermission.getCreatedby()).isEqualTo(UPDATED_CREATEDBY);
        assertThat(testBamsPermission.getCreateddate()).isEqualTo(UPDATED_CREATEDDATE);
        assertThat(testBamsPermission.getLastmodifiedby()).isEqualTo(UPDATED_LASTMODIFIEDBY);
        assertThat(testBamsPermission.getLastmodifieddate()).isEqualTo(UPDATED_LASTMODIFIEDDATE);

        // Validate the BamsPermission in Elasticsearch
        BamsPermission bamsPermissionEs = bamsPermissionSearchRepository.findOne(testBamsPermission.getId());
        assertThat(bamsPermissionEs).isEqualToComparingFieldByField(testBamsPermission);
    }

    @Test
    @Transactional
    public void updateNonExistingBamsPermission() throws Exception {
        int databaseSizeBeforeUpdate = bamsPermissionRepository.findAll().size();

        // Create the BamsPermission

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBamsPermissionMockMvc.perform(put("/api/bams-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsPermission)))
            .andExpect(status().isCreated());

        // Validate the BamsPermission in the database
        List<BamsPermission> bamsPermissionList = bamsPermissionRepository.findAll();
        assertThat(bamsPermissionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBamsPermission() throws Exception {
        // Initialize the database
        bamsPermissionService.save(bamsPermission);

        int databaseSizeBeforeDelete = bamsPermissionRepository.findAll().size();

        // Get the bamsPermission
        restBamsPermissionMockMvc.perform(delete("/api/bams-permissions/{id}", bamsPermission.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bamsPermissionExistsInEs = bamsPermissionSearchRepository.exists(bamsPermission.getId());
        assertThat(bamsPermissionExistsInEs).isFalse();

        // Validate the database is empty
        List<BamsPermission> bamsPermissionList = bamsPermissionRepository.findAll();
        assertThat(bamsPermissionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBamsPermission() throws Exception {
        // Initialize the database
        bamsPermissionService.save(bamsPermission);

        // Search the bamsPermission
        restBamsPermissionMockMvc.perform(get("/api/_search/bams-permissions?query=id:" + bamsPermission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bamsPermission.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createdby").value(hasItem(DEFAULT_CREATEDBY.toString())))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(DEFAULT_CREATEDDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifiedby").value(hasItem(DEFAULT_LASTMODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifieddate").value(hasItem(DEFAULT_LASTMODIFIEDDATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BamsPermission.class);
    }
}
