package com.baoviet.login.web.rest;

import com.baoviet.login.BvloginApp;

import com.baoviet.login.domain.BamsCompany;
import com.baoviet.login.repository.BamsCompanyRepository;
import com.baoviet.login.service.BamsCompanyService;
import com.baoviet.login.repository.search.BamsCompanySearchRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BamsCompanyResource REST controller.
 *
 * @see BamsCompanyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BvloginApp.class)
public class BamsCompanyResourceIntTest {

    private static final String DEFAULT_MATINH = "AAAAAAAAAA";
    private static final String UPDATED_MATINH = "BBBBBBBBBB";

    private static final String DEFAULT_TENTINH = "AAAAAAAAAA";
    private static final String UPDATED_TENTINH = "BBBBBBBBBB";

    private static final String DEFAULT_DIACHI = "AAAAAAAAAA";
    private static final String UPDATED_DIACHI = "BBBBBBBBBB";

    private static final String DEFAULT_DIENTHOAI = "AAAAAAAAAA";
    private static final String UPDATED_DIENTHOAI = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_TENTINH_ENG = "AAAAAAAAAA";
    private static final String UPDATED_TENTINH_ENG = "BBBBBBBBBB";

    private static final String DEFAULT_DIACHI_ENG = "AAAAAAAAAA";
    private static final String UPDATED_DIACHI_ENG = "BBBBBBBBBB";

    private static final String DEFAULT_DIENTHOAI_ENG = "AAAAAAAAAA";
    private static final String UPDATED_DIENTHOAI_ENG = "BBBBBBBBBB";

    private static final String DEFAULT_FAX_ENG = "AAAAAAAAAA";
    private static final String UPDATED_FAX_ENG = "BBBBBBBBBB";

    private static final String DEFAULT_COMP_ID = "AAAAAAAAAA";
    private static final String UPDATED_COMP_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_SEQUENCED = 1L;
    private static final Long UPDATED_SEQUENCED = 2L;

    @Autowired
    private BamsCompanyRepository bamsCompanyRepository;

    @Autowired
    private BamsCompanyService bamsCompanyService;

    @Autowired
    private BamsCompanySearchRepository bamsCompanySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBamsCompanyMockMvc;

    private BamsCompany bamsCompany;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BamsCompanyResource bamsCompanyResource = new BamsCompanyResource(bamsCompanyService);
        this.restBamsCompanyMockMvc = MockMvcBuilders.standaloneSetup(bamsCompanyResource)
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
    public static BamsCompany createEntity(EntityManager em) {
        BamsCompany bamsCompany = new BamsCompany()
            .matinh(DEFAULT_MATINH)
            .tentinh(DEFAULT_TENTINH)
            .diachi(DEFAULT_DIACHI)
            .dienthoai(DEFAULT_DIENTHOAI)
            .fax(DEFAULT_FAX)
            .tentinhEng(DEFAULT_TENTINH_ENG)
            .diachiEng(DEFAULT_DIACHI_ENG)
            .dienthoaiEng(DEFAULT_DIENTHOAI_ENG)
            .faxEng(DEFAULT_FAX_ENG)
            .compId(DEFAULT_COMP_ID)
            .sequenced(DEFAULT_SEQUENCED);
        return bamsCompany;
    }

    @Before
    public void initTest() {
        bamsCompanySearchRepository.deleteAll();
        bamsCompany = createEntity(em);
    }

    @Test
    @Transactional
    public void createBamsCompany() throws Exception {
        int databaseSizeBeforeCreate = bamsCompanyRepository.findAll().size();

        // Create the BamsCompany
        restBamsCompanyMockMvc.perform(post("/api/bams-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsCompany)))
            .andExpect(status().isCreated());

        // Validate the BamsCompany in the database
        List<BamsCompany> bamsCompanyList = bamsCompanyRepository.findAll();
        assertThat(bamsCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        BamsCompany testBamsCompany = bamsCompanyList.get(bamsCompanyList.size() - 1);
        assertThat(testBamsCompany.getMatinh()).isEqualTo(DEFAULT_MATINH);
        assertThat(testBamsCompany.getTentinh()).isEqualTo(DEFAULT_TENTINH);
        assertThat(testBamsCompany.getDiachi()).isEqualTo(DEFAULT_DIACHI);
        assertThat(testBamsCompany.getDienthoai()).isEqualTo(DEFAULT_DIENTHOAI);
        assertThat(testBamsCompany.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testBamsCompany.getTentinhEng()).isEqualTo(DEFAULT_TENTINH_ENG);
        assertThat(testBamsCompany.getDiachiEng()).isEqualTo(DEFAULT_DIACHI_ENG);
        assertThat(testBamsCompany.getDienthoaiEng()).isEqualTo(DEFAULT_DIENTHOAI_ENG);
        assertThat(testBamsCompany.getFaxEng()).isEqualTo(DEFAULT_FAX_ENG);
        assertThat(testBamsCompany.getCompId()).isEqualTo(DEFAULT_COMP_ID);
        assertThat(testBamsCompany.getSequenced()).isEqualTo(DEFAULT_SEQUENCED);

        // Validate the BamsCompany in Elasticsearch
        BamsCompany bamsCompanyEs = bamsCompanySearchRepository.findOne(testBamsCompany.getId());
        assertThat(bamsCompanyEs).isEqualToComparingFieldByField(testBamsCompany);
    }

    @Test
    @Transactional
    public void createBamsCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bamsCompanyRepository.findAll().size();

        // Create the BamsCompany with an existing ID
        bamsCompany.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBamsCompanyMockMvc.perform(post("/api/bams-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsCompany)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BamsCompany> bamsCompanyList = bamsCompanyRepository.findAll();
        assertThat(bamsCompanyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMatinhIsRequired() throws Exception {
        int databaseSizeBeforeTest = bamsCompanyRepository.findAll().size();
        // set the field null
        bamsCompany.setMatinh(null);

        // Create the BamsCompany, which fails.

        restBamsCompanyMockMvc.perform(post("/api/bams-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsCompany)))
            .andExpect(status().isBadRequest());

        List<BamsCompany> bamsCompanyList = bamsCompanyRepository.findAll();
        assertThat(bamsCompanyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBamsCompanies() throws Exception {
        // Initialize the database
        bamsCompanyRepository.saveAndFlush(bamsCompany);

        // Get all the bamsCompanyList
        restBamsCompanyMockMvc.perform(get("/api/bams-companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bamsCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].matinh").value(hasItem(DEFAULT_MATINH.toString())))
            .andExpect(jsonPath("$.[*].tentinh").value(hasItem(DEFAULT_TENTINH.toString())))
            .andExpect(jsonPath("$.[*].diachi").value(hasItem(DEFAULT_DIACHI.toString())))
            .andExpect(jsonPath("$.[*].dienthoai").value(hasItem(DEFAULT_DIENTHOAI.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].tentinhEng").value(hasItem(DEFAULT_TENTINH_ENG.toString())))
            .andExpect(jsonPath("$.[*].diachiEng").value(hasItem(DEFAULT_DIACHI_ENG.toString())))
            .andExpect(jsonPath("$.[*].dienthoaiEng").value(hasItem(DEFAULT_DIENTHOAI_ENG.toString())))
            .andExpect(jsonPath("$.[*].faxEng").value(hasItem(DEFAULT_FAX_ENG.toString())))
            .andExpect(jsonPath("$.[*].compId").value(hasItem(DEFAULT_COMP_ID.toString())))
            .andExpect(jsonPath("$.[*].sequenced").value(hasItem(DEFAULT_SEQUENCED.intValue())));
    }

    @Test
    @Transactional
    public void getBamsCompany() throws Exception {
        // Initialize the database
        bamsCompanyRepository.saveAndFlush(bamsCompany);

        // Get the bamsCompany
        restBamsCompanyMockMvc.perform(get("/api/bams-companies/{id}", bamsCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bamsCompany.getId().intValue()))
            .andExpect(jsonPath("$.matinh").value(DEFAULT_MATINH.toString()))
            .andExpect(jsonPath("$.tentinh").value(DEFAULT_TENTINH.toString()))
            .andExpect(jsonPath("$.diachi").value(DEFAULT_DIACHI.toString()))
            .andExpect(jsonPath("$.dienthoai").value(DEFAULT_DIENTHOAI.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.tentinhEng").value(DEFAULT_TENTINH_ENG.toString()))
            .andExpect(jsonPath("$.diachiEng").value(DEFAULT_DIACHI_ENG.toString()))
            .andExpect(jsonPath("$.dienthoaiEng").value(DEFAULT_DIENTHOAI_ENG.toString()))
            .andExpect(jsonPath("$.faxEng").value(DEFAULT_FAX_ENG.toString()))
            .andExpect(jsonPath("$.compId").value(DEFAULT_COMP_ID.toString()))
            .andExpect(jsonPath("$.sequenced").value(DEFAULT_SEQUENCED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBamsCompany() throws Exception {
        // Get the bamsCompany
        restBamsCompanyMockMvc.perform(get("/api/bams-companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBamsCompany() throws Exception {
        // Initialize the database
        bamsCompanyService.save(bamsCompany);

        int databaseSizeBeforeUpdate = bamsCompanyRepository.findAll().size();

        // Update the bamsCompany
        BamsCompany updatedBamsCompany = bamsCompanyRepository.findOne(bamsCompany.getId());
        updatedBamsCompany
            .matinh(UPDATED_MATINH)
            .tentinh(UPDATED_TENTINH)
            .diachi(UPDATED_DIACHI)
            .dienthoai(UPDATED_DIENTHOAI)
            .fax(UPDATED_FAX)
            .tentinhEng(UPDATED_TENTINH_ENG)
            .diachiEng(UPDATED_DIACHI_ENG)
            .dienthoaiEng(UPDATED_DIENTHOAI_ENG)
            .faxEng(UPDATED_FAX_ENG)
            .compId(UPDATED_COMP_ID)
            .sequenced(UPDATED_SEQUENCED);

        restBamsCompanyMockMvc.perform(put("/api/bams-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBamsCompany)))
            .andExpect(status().isOk());

        // Validate the BamsCompany in the database
        List<BamsCompany> bamsCompanyList = bamsCompanyRepository.findAll();
        assertThat(bamsCompanyList).hasSize(databaseSizeBeforeUpdate);
        BamsCompany testBamsCompany = bamsCompanyList.get(bamsCompanyList.size() - 1);
        assertThat(testBamsCompany.getMatinh()).isEqualTo(UPDATED_MATINH);
        assertThat(testBamsCompany.getTentinh()).isEqualTo(UPDATED_TENTINH);
        assertThat(testBamsCompany.getDiachi()).isEqualTo(UPDATED_DIACHI);
        assertThat(testBamsCompany.getDienthoai()).isEqualTo(UPDATED_DIENTHOAI);
        assertThat(testBamsCompany.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testBamsCompany.getTentinhEng()).isEqualTo(UPDATED_TENTINH_ENG);
        assertThat(testBamsCompany.getDiachiEng()).isEqualTo(UPDATED_DIACHI_ENG);
        assertThat(testBamsCompany.getDienthoaiEng()).isEqualTo(UPDATED_DIENTHOAI_ENG);
        assertThat(testBamsCompany.getFaxEng()).isEqualTo(UPDATED_FAX_ENG);
        assertThat(testBamsCompany.getCompId()).isEqualTo(UPDATED_COMP_ID);
        assertThat(testBamsCompany.getSequenced()).isEqualTo(UPDATED_SEQUENCED);

        // Validate the BamsCompany in Elasticsearch
        BamsCompany bamsCompanyEs = bamsCompanySearchRepository.findOne(testBamsCompany.getId());
        assertThat(bamsCompanyEs).isEqualToComparingFieldByField(testBamsCompany);
    }

    @Test
    @Transactional
    public void updateNonExistingBamsCompany() throws Exception {
        int databaseSizeBeforeUpdate = bamsCompanyRepository.findAll().size();

        // Create the BamsCompany

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBamsCompanyMockMvc.perform(put("/api/bams-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsCompany)))
            .andExpect(status().isCreated());

        // Validate the BamsCompany in the database
        List<BamsCompany> bamsCompanyList = bamsCompanyRepository.findAll();
        assertThat(bamsCompanyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBamsCompany() throws Exception {
        // Initialize the database
        bamsCompanyService.save(bamsCompany);

        int databaseSizeBeforeDelete = bamsCompanyRepository.findAll().size();

        // Get the bamsCompany
        restBamsCompanyMockMvc.perform(delete("/api/bams-companies/{id}", bamsCompany.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bamsCompanyExistsInEs = bamsCompanySearchRepository.exists(bamsCompany.getId());
        assertThat(bamsCompanyExistsInEs).isFalse();

        // Validate the database is empty
        List<BamsCompany> bamsCompanyList = bamsCompanyRepository.findAll();
        assertThat(bamsCompanyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBamsCompany() throws Exception {
        // Initialize the database
        bamsCompanyService.save(bamsCompany);

        // Search the bamsCompany
        restBamsCompanyMockMvc.perform(get("/api/_search/bams-companies?query=id:" + bamsCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bamsCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].matinh").value(hasItem(DEFAULT_MATINH.toString())))
            .andExpect(jsonPath("$.[*].tentinh").value(hasItem(DEFAULT_TENTINH.toString())))
            .andExpect(jsonPath("$.[*].diachi").value(hasItem(DEFAULT_DIACHI.toString())))
            .andExpect(jsonPath("$.[*].dienthoai").value(hasItem(DEFAULT_DIENTHOAI.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].tentinhEng").value(hasItem(DEFAULT_TENTINH_ENG.toString())))
            .andExpect(jsonPath("$.[*].diachiEng").value(hasItem(DEFAULT_DIACHI_ENG.toString())))
            .andExpect(jsonPath("$.[*].dienthoaiEng").value(hasItem(DEFAULT_DIENTHOAI_ENG.toString())))
            .andExpect(jsonPath("$.[*].faxEng").value(hasItem(DEFAULT_FAX_ENG.toString())))
            .andExpect(jsonPath("$.[*].compId").value(hasItem(DEFAULT_COMP_ID.toString())))
            .andExpect(jsonPath("$.[*].sequenced").value(hasItem(DEFAULT_SEQUENCED.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BamsCompany.class);
    }
}
