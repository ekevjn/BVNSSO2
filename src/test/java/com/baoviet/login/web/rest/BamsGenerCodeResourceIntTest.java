package com.baoviet.login.web.rest;

import com.baoviet.login.BvloginApp;

import com.baoviet.login.domain.BamsGenerCode;
import com.baoviet.login.repository.BamsGenerCodeRepository;
import com.baoviet.login.service.BamsGenerCodeService;
import com.baoviet.login.repository.search.BamsGenerCodeSearchRepository;
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
 * Test class for the BamsGenerCodeResource REST controller.
 *
 * @see BamsGenerCodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BvloginApp.class)
public class BamsGenerCodeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

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

    private static final Long DEFAULT_VERSION_ID = 1L;
    private static final Long UPDATED_VERSION_ID = 2L;

    @Autowired
    private BamsGenerCodeRepository bamsGenerCodeRepository;

    @Autowired
    private BamsGenerCodeService bamsGenerCodeService;

    @Autowired
    private BamsGenerCodeSearchRepository bamsGenerCodeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBamsGenerCodeMockMvc;

    private BamsGenerCode bamsGenerCode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BamsGenerCodeResource bamsGenerCodeResource = new BamsGenerCodeResource(bamsGenerCodeService);
        this.restBamsGenerCodeMockMvc = MockMvcBuilders.standaloneSetup(bamsGenerCodeResource)
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
    public static BamsGenerCode createEntity(EntityManager em) {
        BamsGenerCode bamsGenerCode = new BamsGenerCode()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .activated(DEFAULT_ACTIVATED)
            .createdby(DEFAULT_CREATEDBY)
            .createddate(DEFAULT_CREATEDDATE)
            .lastmodifiedby(DEFAULT_LASTMODIFIEDBY)
            .lastmodifieddate(DEFAULT_LASTMODIFIEDDATE)
            .versionId(DEFAULT_VERSION_ID);
        return bamsGenerCode;
    }

    @Before
    public void initTest() {
        bamsGenerCodeSearchRepository.deleteAll();
        bamsGenerCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createBamsGenerCode() throws Exception {
        int databaseSizeBeforeCreate = bamsGenerCodeRepository.findAll().size();

        // Create the BamsGenerCode
        restBamsGenerCodeMockMvc.perform(post("/api/bams-gener-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsGenerCode)))
            .andExpect(status().isCreated());

        // Validate the BamsGenerCode in the database
        List<BamsGenerCode> bamsGenerCodeList = bamsGenerCodeRepository.findAll();
        assertThat(bamsGenerCodeList).hasSize(databaseSizeBeforeCreate + 1);
        BamsGenerCode testBamsGenerCode = bamsGenerCodeList.get(bamsGenerCodeList.size() - 1);
        assertThat(testBamsGenerCode.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBamsGenerCode.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBamsGenerCode.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBamsGenerCode.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testBamsGenerCode.getCreatedby()).isEqualTo(DEFAULT_CREATEDBY);
        assertThat(testBamsGenerCode.getCreateddate()).isEqualTo(DEFAULT_CREATEDDATE);
        assertThat(testBamsGenerCode.getLastmodifiedby()).isEqualTo(DEFAULT_LASTMODIFIEDBY);
        assertThat(testBamsGenerCode.getLastmodifieddate()).isEqualTo(DEFAULT_LASTMODIFIEDDATE);
        assertThat(testBamsGenerCode.getVersionId()).isEqualTo(DEFAULT_VERSION_ID);

        // Validate the BamsGenerCode in Elasticsearch
        BamsGenerCode bamsGenerCodeEs = bamsGenerCodeSearchRepository.findOne(testBamsGenerCode.getId());
        assertThat(bamsGenerCodeEs).isEqualToComparingFieldByField(testBamsGenerCode);
    }

    @Test
    @Transactional
    public void createBamsGenerCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bamsGenerCodeRepository.findAll().size();

        // Create the BamsGenerCode with an existing ID
        bamsGenerCode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBamsGenerCodeMockMvc.perform(post("/api/bams-gener-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsGenerCode)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BamsGenerCode> bamsGenerCodeList = bamsGenerCodeRepository.findAll();
        assertThat(bamsGenerCodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bamsGenerCodeRepository.findAll().size();
        // set the field null
        bamsGenerCode.setCode(null);

        // Create the BamsGenerCode, which fails.

        restBamsGenerCodeMockMvc.perform(post("/api/bams-gener-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsGenerCode)))
            .andExpect(status().isBadRequest());

        List<BamsGenerCode> bamsGenerCodeList = bamsGenerCodeRepository.findAll();
        assertThat(bamsGenerCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBamsGenerCodes() throws Exception {
        // Initialize the database
        bamsGenerCodeRepository.saveAndFlush(bamsGenerCode);

        // Get all the bamsGenerCodeList
        restBamsGenerCodeMockMvc.perform(get("/api/bams-gener-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bamsGenerCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdby").value(hasItem(DEFAULT_CREATEDBY.toString())))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(DEFAULT_CREATEDDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifiedby").value(hasItem(DEFAULT_LASTMODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifieddate").value(hasItem(DEFAULT_LASTMODIFIEDDATE.toString())))
            .andExpect(jsonPath("$.[*].versionId").value(hasItem(DEFAULT_VERSION_ID.intValue())));
    }

    @Test
    @Transactional
    public void getBamsGenerCode() throws Exception {
        // Initialize the database
        bamsGenerCodeRepository.saveAndFlush(bamsGenerCode);

        // Get the bamsGenerCode
        restBamsGenerCodeMockMvc.perform(get("/api/bams-gener-codes/{id}", bamsGenerCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bamsGenerCode.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdby").value(DEFAULT_CREATEDBY.toString()))
            .andExpect(jsonPath("$.createddate").value(DEFAULT_CREATEDDATE.toString()))
            .andExpect(jsonPath("$.lastmodifiedby").value(DEFAULT_LASTMODIFIEDBY.toString()))
            .andExpect(jsonPath("$.lastmodifieddate").value(DEFAULT_LASTMODIFIEDDATE.toString()))
            .andExpect(jsonPath("$.versionId").value(DEFAULT_VERSION_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBamsGenerCode() throws Exception {
        // Get the bamsGenerCode
        restBamsGenerCodeMockMvc.perform(get("/api/bams-gener-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBamsGenerCode() throws Exception {
        // Initialize the database
        bamsGenerCodeService.save(bamsGenerCode);

        int databaseSizeBeforeUpdate = bamsGenerCodeRepository.findAll().size();

        // Update the bamsGenerCode
        BamsGenerCode updatedBamsGenerCode = bamsGenerCodeRepository.findOne(bamsGenerCode.getId());
        updatedBamsGenerCode
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .activated(UPDATED_ACTIVATED)
            .createdby(UPDATED_CREATEDBY)
            .createddate(UPDATED_CREATEDDATE)
            .lastmodifiedby(UPDATED_LASTMODIFIEDBY)
            .lastmodifieddate(UPDATED_LASTMODIFIEDDATE)
            .versionId(UPDATED_VERSION_ID);

        restBamsGenerCodeMockMvc.perform(put("/api/bams-gener-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBamsGenerCode)))
            .andExpect(status().isOk());

        // Validate the BamsGenerCode in the database
        List<BamsGenerCode> bamsGenerCodeList = bamsGenerCodeRepository.findAll();
        assertThat(bamsGenerCodeList).hasSize(databaseSizeBeforeUpdate);
        BamsGenerCode testBamsGenerCode = bamsGenerCodeList.get(bamsGenerCodeList.size() - 1);
        assertThat(testBamsGenerCode.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBamsGenerCode.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBamsGenerCode.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBamsGenerCode.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testBamsGenerCode.getCreatedby()).isEqualTo(UPDATED_CREATEDBY);
        assertThat(testBamsGenerCode.getCreateddate()).isEqualTo(UPDATED_CREATEDDATE);
        assertThat(testBamsGenerCode.getLastmodifiedby()).isEqualTo(UPDATED_LASTMODIFIEDBY);
        assertThat(testBamsGenerCode.getLastmodifieddate()).isEqualTo(UPDATED_LASTMODIFIEDDATE);
        assertThat(testBamsGenerCode.getVersionId()).isEqualTo(UPDATED_VERSION_ID);

        // Validate the BamsGenerCode in Elasticsearch
        BamsGenerCode bamsGenerCodeEs = bamsGenerCodeSearchRepository.findOne(testBamsGenerCode.getId());
        assertThat(bamsGenerCodeEs).isEqualToComparingFieldByField(testBamsGenerCode);
    }

    @Test
    @Transactional
    public void updateNonExistingBamsGenerCode() throws Exception {
        int databaseSizeBeforeUpdate = bamsGenerCodeRepository.findAll().size();

        // Create the BamsGenerCode

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBamsGenerCodeMockMvc.perform(put("/api/bams-gener-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsGenerCode)))
            .andExpect(status().isCreated());

        // Validate the BamsGenerCode in the database
        List<BamsGenerCode> bamsGenerCodeList = bamsGenerCodeRepository.findAll();
        assertThat(bamsGenerCodeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBamsGenerCode() throws Exception {
        // Initialize the database
        bamsGenerCodeService.save(bamsGenerCode);

        int databaseSizeBeforeDelete = bamsGenerCodeRepository.findAll().size();

        // Get the bamsGenerCode
        restBamsGenerCodeMockMvc.perform(delete("/api/bams-gener-codes/{id}", bamsGenerCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bamsGenerCodeExistsInEs = bamsGenerCodeSearchRepository.exists(bamsGenerCode.getId());
        assertThat(bamsGenerCodeExistsInEs).isFalse();

        // Validate the database is empty
        List<BamsGenerCode> bamsGenerCodeList = bamsGenerCodeRepository.findAll();
        assertThat(bamsGenerCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBamsGenerCode() throws Exception {
        // Initialize the database
        bamsGenerCodeService.save(bamsGenerCode);

        // Search the bamsGenerCode
        restBamsGenerCodeMockMvc.perform(get("/api/_search/bams-gener-codes?query=id:" + bamsGenerCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bamsGenerCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdby").value(hasItem(DEFAULT_CREATEDBY.toString())))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(DEFAULT_CREATEDDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifiedby").value(hasItem(DEFAULT_LASTMODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifieddate").value(hasItem(DEFAULT_LASTMODIFIEDDATE.toString())))
            .andExpect(jsonPath("$.[*].versionId").value(hasItem(DEFAULT_VERSION_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BamsGenerCode.class);
    }
}
