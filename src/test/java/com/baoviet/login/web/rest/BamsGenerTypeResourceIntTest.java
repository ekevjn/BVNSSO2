package com.baoviet.login.web.rest;

import com.baoviet.login.BvloginApp;

import com.baoviet.login.domain.BamsGenerType;
import com.baoviet.login.repository.BamsGenerTypeRepository;
import com.baoviet.login.service.BamsGenerTypeService;
import com.baoviet.login.repository.search.BamsGenerTypeSearchRepository;
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
 * Test class for the BamsGenerTypeResource REST controller.
 *
 * @see BamsGenerTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BvloginApp.class)
public class BamsGenerTypeResourceIntTest {

    private static final String DEFAULT_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TYPE_STATUS_CODE = false;
    private static final Boolean UPDATED_TYPE_STATUS_CODE = true;

    private static final String DEFAULT_TYPE_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TYPE_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TYPE_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TYPE_LAST_UPDATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_LAST_UPDATE_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TYPE_LAST_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TYPE_LAST_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_TYPE_VERSION_ID = 1D;
    private static final Double UPDATED_TYPE_VERSION_ID = 2D;

    @Autowired
    private BamsGenerTypeRepository bamsGenerTypeRepository;

    @Autowired
    private BamsGenerTypeService bamsGenerTypeService;

    @Autowired
    private BamsGenerTypeSearchRepository bamsGenerTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBamsGenerTypeMockMvc;

    private BamsGenerType bamsGenerType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BamsGenerTypeResource bamsGenerTypeResource = new BamsGenerTypeResource(bamsGenerTypeService);
        this.restBamsGenerTypeMockMvc = MockMvcBuilders.standaloneSetup(bamsGenerTypeResource)
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
    public static BamsGenerType createEntity(EntityManager em) {
        BamsGenerType bamsGenerType = new BamsGenerType()
            .typeCode(DEFAULT_TYPE_CODE)
            .typeName(DEFAULT_TYPE_NAME)
            .typeStatusCode(DEFAULT_TYPE_STATUS_CODE)
            .typeCreatedBy(DEFAULT_TYPE_CREATED_BY)
            .typeCreatedDate(DEFAULT_TYPE_CREATED_DATE)
            .typeLastUpdateBy(DEFAULT_TYPE_LAST_UPDATE_BY)
            .typeLastUpdateDate(DEFAULT_TYPE_LAST_UPDATE_DATE)
            .typeVersionId(DEFAULT_TYPE_VERSION_ID);
        return bamsGenerType;
    }

    @Before
    public void initTest() {
        bamsGenerTypeSearchRepository.deleteAll();
        bamsGenerType = createEntity(em);
    }

    @Test
    @Transactional
    public void createBamsGenerType() throws Exception {
        int databaseSizeBeforeCreate = bamsGenerTypeRepository.findAll().size();

        // Create the BamsGenerType
        restBamsGenerTypeMockMvc.perform(post("/api/bams-gener-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsGenerType)))
            .andExpect(status().isCreated());

        // Validate the BamsGenerType in the database
        List<BamsGenerType> bamsGenerTypeList = bamsGenerTypeRepository.findAll();
        assertThat(bamsGenerTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BamsGenerType testBamsGenerType = bamsGenerTypeList.get(bamsGenerTypeList.size() - 1);
        assertThat(testBamsGenerType.getTypeCode()).isEqualTo(DEFAULT_TYPE_CODE);
        assertThat(testBamsGenerType.getTypeName()).isEqualTo(DEFAULT_TYPE_NAME);
        assertThat(testBamsGenerType.isTypeStatusCode()).isEqualTo(DEFAULT_TYPE_STATUS_CODE);
        assertThat(testBamsGenerType.getTypeCreatedBy()).isEqualTo(DEFAULT_TYPE_CREATED_BY);
        assertThat(testBamsGenerType.getTypeCreatedDate()).isEqualTo(DEFAULT_TYPE_CREATED_DATE);
        assertThat(testBamsGenerType.getTypeLastUpdateBy()).isEqualTo(DEFAULT_TYPE_LAST_UPDATE_BY);
        assertThat(testBamsGenerType.getTypeLastUpdateDate()).isEqualTo(DEFAULT_TYPE_LAST_UPDATE_DATE);
        assertThat(testBamsGenerType.getTypeVersionId()).isEqualTo(DEFAULT_TYPE_VERSION_ID);

        // Validate the BamsGenerType in Elasticsearch
        BamsGenerType bamsGenerTypeEs = bamsGenerTypeSearchRepository.findOne(testBamsGenerType.getId());
        assertThat(bamsGenerTypeEs).isEqualToComparingFieldByField(testBamsGenerType);
    }

    @Test
    @Transactional
    public void createBamsGenerTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bamsGenerTypeRepository.findAll().size();

        // Create the BamsGenerType with an existing ID
        bamsGenerType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBamsGenerTypeMockMvc.perform(post("/api/bams-gener-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsGenerType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BamsGenerType> bamsGenerTypeList = bamsGenerTypeRepository.findAll();
        assertThat(bamsGenerTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bamsGenerTypeRepository.findAll().size();
        // set the field null
        bamsGenerType.setTypeCode(null);

        // Create the BamsGenerType, which fails.

        restBamsGenerTypeMockMvc.perform(post("/api/bams-gener-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsGenerType)))
            .andExpect(status().isBadRequest());

        List<BamsGenerType> bamsGenerTypeList = bamsGenerTypeRepository.findAll();
        assertThat(bamsGenerTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBamsGenerTypes() throws Exception {
        // Initialize the database
        bamsGenerTypeRepository.saveAndFlush(bamsGenerType);

        // Get all the bamsGenerTypeList
        restBamsGenerTypeMockMvc.perform(get("/api/bams-gener-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bamsGenerType.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeCode").value(hasItem(DEFAULT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].typeName").value(hasItem(DEFAULT_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].typeStatusCode").value(hasItem(DEFAULT_TYPE_STATUS_CODE.booleanValue())))
            .andExpect(jsonPath("$.[*].typeCreatedBy").value(hasItem(DEFAULT_TYPE_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].typeCreatedDate").value(hasItem(DEFAULT_TYPE_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].typeLastUpdateBy").value(hasItem(DEFAULT_TYPE_LAST_UPDATE_BY.toString())))
            .andExpect(jsonPath("$.[*].typeLastUpdateDate").value(hasItem(DEFAULT_TYPE_LAST_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].typeVersionId").value(hasItem(DEFAULT_TYPE_VERSION_ID.doubleValue())));
    }

    @Test
    @Transactional
    public void getBamsGenerType() throws Exception {
        // Initialize the database
        bamsGenerTypeRepository.saveAndFlush(bamsGenerType);

        // Get the bamsGenerType
        restBamsGenerTypeMockMvc.perform(get("/api/bams-gener-types/{id}", bamsGenerType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bamsGenerType.getId().intValue()))
            .andExpect(jsonPath("$.typeCode").value(DEFAULT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.typeName").value(DEFAULT_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.typeStatusCode").value(DEFAULT_TYPE_STATUS_CODE.booleanValue()))
            .andExpect(jsonPath("$.typeCreatedBy").value(DEFAULT_TYPE_CREATED_BY.toString()))
            .andExpect(jsonPath("$.typeCreatedDate").value(DEFAULT_TYPE_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.typeLastUpdateBy").value(DEFAULT_TYPE_LAST_UPDATE_BY.toString()))
            .andExpect(jsonPath("$.typeLastUpdateDate").value(DEFAULT_TYPE_LAST_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.typeVersionId").value(DEFAULT_TYPE_VERSION_ID.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBamsGenerType() throws Exception {
        // Get the bamsGenerType
        restBamsGenerTypeMockMvc.perform(get("/api/bams-gener-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBamsGenerType() throws Exception {
        // Initialize the database
        bamsGenerTypeService.save(bamsGenerType);

        int databaseSizeBeforeUpdate = bamsGenerTypeRepository.findAll().size();

        // Update the bamsGenerType
        BamsGenerType updatedBamsGenerType = bamsGenerTypeRepository.findOne(bamsGenerType.getId());
        updatedBamsGenerType
            .typeCode(UPDATED_TYPE_CODE)
            .typeName(UPDATED_TYPE_NAME)
            .typeStatusCode(UPDATED_TYPE_STATUS_CODE)
            .typeCreatedBy(UPDATED_TYPE_CREATED_BY)
            .typeCreatedDate(UPDATED_TYPE_CREATED_DATE)
            .typeLastUpdateBy(UPDATED_TYPE_LAST_UPDATE_BY)
            .typeLastUpdateDate(UPDATED_TYPE_LAST_UPDATE_DATE)
            .typeVersionId(UPDATED_TYPE_VERSION_ID);

        restBamsGenerTypeMockMvc.perform(put("/api/bams-gener-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBamsGenerType)))
            .andExpect(status().isOk());

        // Validate the BamsGenerType in the database
        List<BamsGenerType> bamsGenerTypeList = bamsGenerTypeRepository.findAll();
        assertThat(bamsGenerTypeList).hasSize(databaseSizeBeforeUpdate);
        BamsGenerType testBamsGenerType = bamsGenerTypeList.get(bamsGenerTypeList.size() - 1);
        assertThat(testBamsGenerType.getTypeCode()).isEqualTo(UPDATED_TYPE_CODE);
        assertThat(testBamsGenerType.getTypeName()).isEqualTo(UPDATED_TYPE_NAME);
        assertThat(testBamsGenerType.isTypeStatusCode()).isEqualTo(UPDATED_TYPE_STATUS_CODE);
        assertThat(testBamsGenerType.getTypeCreatedBy()).isEqualTo(UPDATED_TYPE_CREATED_BY);
        assertThat(testBamsGenerType.getTypeCreatedDate()).isEqualTo(UPDATED_TYPE_CREATED_DATE);
        assertThat(testBamsGenerType.getTypeLastUpdateBy()).isEqualTo(UPDATED_TYPE_LAST_UPDATE_BY);
        assertThat(testBamsGenerType.getTypeLastUpdateDate()).isEqualTo(UPDATED_TYPE_LAST_UPDATE_DATE);
        assertThat(testBamsGenerType.getTypeVersionId()).isEqualTo(UPDATED_TYPE_VERSION_ID);

        // Validate the BamsGenerType in Elasticsearch
        BamsGenerType bamsGenerTypeEs = bamsGenerTypeSearchRepository.findOne(testBamsGenerType.getId());
        assertThat(bamsGenerTypeEs).isEqualToComparingFieldByField(testBamsGenerType);
    }

    @Test
    @Transactional
    public void updateNonExistingBamsGenerType() throws Exception {
        int databaseSizeBeforeUpdate = bamsGenerTypeRepository.findAll().size();

        // Create the BamsGenerType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBamsGenerTypeMockMvc.perform(put("/api/bams-gener-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsGenerType)))
            .andExpect(status().isCreated());

        // Validate the BamsGenerType in the database
        List<BamsGenerType> bamsGenerTypeList = bamsGenerTypeRepository.findAll();
        assertThat(bamsGenerTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBamsGenerType() throws Exception {
        // Initialize the database
        bamsGenerTypeService.save(bamsGenerType);

        int databaseSizeBeforeDelete = bamsGenerTypeRepository.findAll().size();

        // Get the bamsGenerType
        restBamsGenerTypeMockMvc.perform(delete("/api/bams-gener-types/{id}", bamsGenerType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bamsGenerTypeExistsInEs = bamsGenerTypeSearchRepository.exists(bamsGenerType.getId());
        assertThat(bamsGenerTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<BamsGenerType> bamsGenerTypeList = bamsGenerTypeRepository.findAll();
        assertThat(bamsGenerTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBamsGenerType() throws Exception {
        // Initialize the database
        bamsGenerTypeService.save(bamsGenerType);

        // Search the bamsGenerType
        restBamsGenerTypeMockMvc.perform(get("/api/_search/bams-gener-types?query=id:" + bamsGenerType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bamsGenerType.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeCode").value(hasItem(DEFAULT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].typeName").value(hasItem(DEFAULT_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].typeStatusCode").value(hasItem(DEFAULT_TYPE_STATUS_CODE.booleanValue())))
            .andExpect(jsonPath("$.[*].typeCreatedBy").value(hasItem(DEFAULT_TYPE_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].typeCreatedDate").value(hasItem(DEFAULT_TYPE_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].typeLastUpdateBy").value(hasItem(DEFAULT_TYPE_LAST_UPDATE_BY.toString())))
            .andExpect(jsonPath("$.[*].typeLastUpdateDate").value(hasItem(DEFAULT_TYPE_LAST_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].typeVersionId").value(hasItem(DEFAULT_TYPE_VERSION_ID.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BamsGenerType.class);
    }
}
