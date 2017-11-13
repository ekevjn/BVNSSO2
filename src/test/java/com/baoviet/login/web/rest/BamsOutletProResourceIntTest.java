package com.baoviet.login.web.rest;

import com.baoviet.login.BvloginApp;

import com.baoviet.login.domain.BamsOutletPro;
import com.baoviet.login.repository.BamsOutletProRepository;
import com.baoviet.login.service.BamsOutletProService;
import com.baoviet.login.repository.search.BamsOutletProSearchRepository;
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
 * Test class for the BamsOutletProResource REST controller.
 *
 * @see BamsOutletProResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BvloginApp.class)
public class BamsOutletProResourceIntTest {

    private static final String DEFAULT_SOURCEDATA = "AAAAAAAAAA";
    private static final String UPDATED_SOURCEDATA = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCTTYPE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTTYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EFFECTIVEDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVEDATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CEASEDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CEASEDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PRODUCTCODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANYCODE = "AAAAAAAAA";
    private static final String UPDATED_COMPANYCODE = "BBBBBBBBB";

    @Autowired
    private BamsOutletProRepository bamsOutletProRepository;

    @Autowired
    private BamsOutletProService bamsOutletProService;

    @Autowired
    private BamsOutletProSearchRepository bamsOutletProSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBamsOutletProMockMvc;

    private BamsOutletPro bamsOutletPro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BamsOutletProResource bamsOutletProResource = new BamsOutletProResource(bamsOutletProService);
        this.restBamsOutletProMockMvc = MockMvcBuilders.standaloneSetup(bamsOutletProResource)
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
    public static BamsOutletPro createEntity(EntityManager em) {
        BamsOutletPro bamsOutletPro = new BamsOutletPro()
            .sourcedata(DEFAULT_SOURCEDATA)
            .producttype(DEFAULT_PRODUCTTYPE)
            .effectivedate(DEFAULT_EFFECTIVEDATE)
            .ceasedate(DEFAULT_CEASEDATE)
            .productcode(DEFAULT_PRODUCTCODE)
            .companycode(DEFAULT_COMPANYCODE);
        return bamsOutletPro;
    }

    @Before
    public void initTest() {
        bamsOutletProSearchRepository.deleteAll();
        bamsOutletPro = createEntity(em);
    }

    @Test
    @Transactional
    public void createBamsOutletPro() throws Exception {
        int databaseSizeBeforeCreate = bamsOutletProRepository.findAll().size();

        // Create the BamsOutletPro
        restBamsOutletProMockMvc.perform(post("/api/bams-outlet-pros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsOutletPro)))
            .andExpect(status().isCreated());

        // Validate the BamsOutletPro in the database
        List<BamsOutletPro> bamsOutletProList = bamsOutletProRepository.findAll();
        assertThat(bamsOutletProList).hasSize(databaseSizeBeforeCreate + 1);
        BamsOutletPro testBamsOutletPro = bamsOutletProList.get(bamsOutletProList.size() - 1);
        assertThat(testBamsOutletPro.getSourcedata()).isEqualTo(DEFAULT_SOURCEDATA);
        assertThat(testBamsOutletPro.getProducttype()).isEqualTo(DEFAULT_PRODUCTTYPE);
        assertThat(testBamsOutletPro.getEffectivedate()).isEqualTo(DEFAULT_EFFECTIVEDATE);
        assertThat(testBamsOutletPro.getCeasedate()).isEqualTo(DEFAULT_CEASEDATE);
        assertThat(testBamsOutletPro.getProductcode()).isEqualTo(DEFAULT_PRODUCTCODE);
        assertThat(testBamsOutletPro.getCompanycode()).isEqualTo(DEFAULT_COMPANYCODE);

        // Validate the BamsOutletPro in Elasticsearch
        BamsOutletPro bamsOutletProEs = bamsOutletProSearchRepository.findOne(testBamsOutletPro.getId());
        assertThat(bamsOutletProEs).isEqualToComparingFieldByField(testBamsOutletPro);
    }

    @Test
    @Transactional
    public void createBamsOutletProWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bamsOutletProRepository.findAll().size();

        // Create the BamsOutletPro with an existing ID
        bamsOutletPro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBamsOutletProMockMvc.perform(post("/api/bams-outlet-pros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsOutletPro)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BamsOutletPro> bamsOutletProList = bamsOutletProRepository.findAll();
        assertThat(bamsOutletProList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEffectivedateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bamsOutletProRepository.findAll().size();
        // set the field null
        bamsOutletPro.setEffectivedate(null);

        // Create the BamsOutletPro, which fails.

        restBamsOutletProMockMvc.perform(post("/api/bams-outlet-pros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsOutletPro)))
            .andExpect(status().isBadRequest());

        List<BamsOutletPro> bamsOutletProList = bamsOutletProRepository.findAll();
        assertThat(bamsOutletProList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCeasedateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bamsOutletProRepository.findAll().size();
        // set the field null
        bamsOutletPro.setCeasedate(null);

        // Create the BamsOutletPro, which fails.

        restBamsOutletProMockMvc.perform(post("/api/bams-outlet-pros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsOutletPro)))
            .andExpect(status().isBadRequest());

        List<BamsOutletPro> bamsOutletProList = bamsOutletProRepository.findAll();
        assertThat(bamsOutletProList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bamsOutletProRepository.findAll().size();
        // set the field null
        bamsOutletPro.setProductcode(null);

        // Create the BamsOutletPro, which fails.

        restBamsOutletProMockMvc.perform(post("/api/bams-outlet-pros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsOutletPro)))
            .andExpect(status().isBadRequest());

        List<BamsOutletPro> bamsOutletProList = bamsOutletProRepository.findAll();
        assertThat(bamsOutletProList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompanycodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bamsOutletProRepository.findAll().size();
        // set the field null
        bamsOutletPro.setCompanycode(null);

        // Create the BamsOutletPro, which fails.

        restBamsOutletProMockMvc.perform(post("/api/bams-outlet-pros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsOutletPro)))
            .andExpect(status().isBadRequest());

        List<BamsOutletPro> bamsOutletProList = bamsOutletProRepository.findAll();
        assertThat(bamsOutletProList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBamsOutletPros() throws Exception {
        // Initialize the database
        bamsOutletProRepository.saveAndFlush(bamsOutletPro);

        // Get all the bamsOutletProList
        restBamsOutletProMockMvc.perform(get("/api/bams-outlet-pros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bamsOutletPro.getId().intValue())))
            .andExpect(jsonPath("$.[*].sourcedata").value(hasItem(DEFAULT_SOURCEDATA.toString())))
            .andExpect(jsonPath("$.[*].producttype").value(hasItem(DEFAULT_PRODUCTTYPE.toString())))
            .andExpect(jsonPath("$.[*].effectivedate").value(hasItem(DEFAULT_EFFECTIVEDATE.toString())))
            .andExpect(jsonPath("$.[*].ceasedate").value(hasItem(DEFAULT_CEASEDATE.toString())))
            .andExpect(jsonPath("$.[*].productcode").value(hasItem(DEFAULT_PRODUCTCODE.toString())))
            .andExpect(jsonPath("$.[*].companycode").value(hasItem(DEFAULT_COMPANYCODE.toString())));
    }

    @Test
    @Transactional
    public void getBamsOutletPro() throws Exception {
        // Initialize the database
        bamsOutletProRepository.saveAndFlush(bamsOutletPro);

        // Get the bamsOutletPro
        restBamsOutletProMockMvc.perform(get("/api/bams-outlet-pros/{id}", bamsOutletPro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bamsOutletPro.getId().intValue()))
            .andExpect(jsonPath("$.sourcedata").value(DEFAULT_SOURCEDATA.toString()))
            .andExpect(jsonPath("$.producttype").value(DEFAULT_PRODUCTTYPE.toString()))
            .andExpect(jsonPath("$.effectivedate").value(DEFAULT_EFFECTIVEDATE.toString()))
            .andExpect(jsonPath("$.ceasedate").value(DEFAULT_CEASEDATE.toString()))
            .andExpect(jsonPath("$.productcode").value(DEFAULT_PRODUCTCODE.toString()))
            .andExpect(jsonPath("$.companycode").value(DEFAULT_COMPANYCODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBamsOutletPro() throws Exception {
        // Get the bamsOutletPro
        restBamsOutletProMockMvc.perform(get("/api/bams-outlet-pros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBamsOutletPro() throws Exception {
        // Initialize the database
        bamsOutletProService.save(bamsOutletPro);

        int databaseSizeBeforeUpdate = bamsOutletProRepository.findAll().size();

        // Update the bamsOutletPro
        BamsOutletPro updatedBamsOutletPro = bamsOutletProRepository.findOne(bamsOutletPro.getId());
        updatedBamsOutletPro
            .sourcedata(UPDATED_SOURCEDATA)
            .producttype(UPDATED_PRODUCTTYPE)
            .effectivedate(UPDATED_EFFECTIVEDATE)
            .ceasedate(UPDATED_CEASEDATE)
            .productcode(UPDATED_PRODUCTCODE)
            .companycode(UPDATED_COMPANYCODE);

        restBamsOutletProMockMvc.perform(put("/api/bams-outlet-pros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBamsOutletPro)))
            .andExpect(status().isOk());

        // Validate the BamsOutletPro in the database
        List<BamsOutletPro> bamsOutletProList = bamsOutletProRepository.findAll();
        assertThat(bamsOutletProList).hasSize(databaseSizeBeforeUpdate);
        BamsOutletPro testBamsOutletPro = bamsOutletProList.get(bamsOutletProList.size() - 1);
        assertThat(testBamsOutletPro.getSourcedata()).isEqualTo(UPDATED_SOURCEDATA);
        assertThat(testBamsOutletPro.getProducttype()).isEqualTo(UPDATED_PRODUCTTYPE);
        assertThat(testBamsOutletPro.getEffectivedate()).isEqualTo(UPDATED_EFFECTIVEDATE);
        assertThat(testBamsOutletPro.getCeasedate()).isEqualTo(UPDATED_CEASEDATE);
        assertThat(testBamsOutletPro.getProductcode()).isEqualTo(UPDATED_PRODUCTCODE);
        assertThat(testBamsOutletPro.getCompanycode()).isEqualTo(UPDATED_COMPANYCODE);

        // Validate the BamsOutletPro in Elasticsearch
        BamsOutletPro bamsOutletProEs = bamsOutletProSearchRepository.findOne(testBamsOutletPro.getId());
        assertThat(bamsOutletProEs).isEqualToComparingFieldByField(testBamsOutletPro);
    }

    @Test
    @Transactional
    public void updateNonExistingBamsOutletPro() throws Exception {
        int databaseSizeBeforeUpdate = bamsOutletProRepository.findAll().size();

        // Create the BamsOutletPro

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBamsOutletProMockMvc.perform(put("/api/bams-outlet-pros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsOutletPro)))
            .andExpect(status().isCreated());

        // Validate the BamsOutletPro in the database
        List<BamsOutletPro> bamsOutletProList = bamsOutletProRepository.findAll();
        assertThat(bamsOutletProList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBamsOutletPro() throws Exception {
        // Initialize the database
        bamsOutletProService.save(bamsOutletPro);

        int databaseSizeBeforeDelete = bamsOutletProRepository.findAll().size();

        // Get the bamsOutletPro
        restBamsOutletProMockMvc.perform(delete("/api/bams-outlet-pros/{id}", bamsOutletPro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bamsOutletProExistsInEs = bamsOutletProSearchRepository.exists(bamsOutletPro.getId());
        assertThat(bamsOutletProExistsInEs).isFalse();

        // Validate the database is empty
        List<BamsOutletPro> bamsOutletProList = bamsOutletProRepository.findAll();
        assertThat(bamsOutletProList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBamsOutletPro() throws Exception {
        // Initialize the database
        bamsOutletProService.save(bamsOutletPro);

        // Search the bamsOutletPro
        restBamsOutletProMockMvc.perform(get("/api/_search/bams-outlet-pros?query=id:" + bamsOutletPro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bamsOutletPro.getId().intValue())))
            .andExpect(jsonPath("$.[*].sourcedata").value(hasItem(DEFAULT_SOURCEDATA.toString())))
            .andExpect(jsonPath("$.[*].producttype").value(hasItem(DEFAULT_PRODUCTTYPE.toString())))
            .andExpect(jsonPath("$.[*].effectivedate").value(hasItem(DEFAULT_EFFECTIVEDATE.toString())))
            .andExpect(jsonPath("$.[*].ceasedate").value(hasItem(DEFAULT_CEASEDATE.toString())))
            .andExpect(jsonPath("$.[*].productcode").value(hasItem(DEFAULT_PRODUCTCODE.toString())))
            .andExpect(jsonPath("$.[*].companycode").value(hasItem(DEFAULT_COMPANYCODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BamsOutletPro.class);
    }
}
