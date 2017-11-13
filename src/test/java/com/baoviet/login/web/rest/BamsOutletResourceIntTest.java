package com.baoviet.login.web.rest;

import com.baoviet.login.BvloginApp;

import com.baoviet.login.domain.BamsOutlet;
import com.baoviet.login.repository.BamsOutletRepository;
import com.baoviet.login.service.BamsOutletService;
import com.baoviet.login.repository.search.BamsOutletSearchRepository;
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
 * Test class for the BamsOutletResource REST controller.
 *
 * @see BamsOutletResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BvloginApp.class)
public class BamsOutletResourceIntTest {

    private static final String DEFAULT_DAILY_ID = "AAAAAAAAAA";
    private static final String UPDATED_DAILY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TENDAILY = "AAAAAAAAAA";
    private static final String UPDATED_TENDAILY = "BBBBBBBBBB";

    private static final String DEFAULT_CVDL = "AAAAAAAAAA";
    private static final String UPDATED_CVDL = "BBBBBBBBBB";

    private static final String DEFAULT_MOTA_CVDL = "AAAAAAAAAA";
    private static final String UPDATED_MOTA_CVDL = "BBBBBBBBBB";

    private static final String DEFAULT_DIACHI = "AAAAAAAAAA";
    private static final String UPDATED_DIACHI = "BBBBBBBBBB";

    private static final String DEFAULT_DIENTHOAI = "AAAAAAAAAA";
    private static final String UPDATED_DIENTHOAI = "BBBBBBBBBB";

    private static final String DEFAULT_MATINH = "AAAAAAAAAA";
    private static final String UPDATED_MATINH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_VPHD = "AAAAAAAAAA";
    private static final String UPDATED_VPHD = "BBBBBBBBBB";

    private static final String DEFAULT_DIACHIVPHD = "AAAAAAAAAA";
    private static final String UPDATED_DIACHIVPHD = "BBBBBBBBBB";

    private static final String DEFAULT_MAPHONGBAN = "AAAAAAAAAA";
    private static final String UPDATED_MAPHONGBAN = "BBBBBBBBBB";

    private static final String DEFAULT_TENPHONGBAN = "AAAAAAAAAA";
    private static final String UPDATED_TENPHONGBAN = "BBBBBBBBBB";

    private static final String DEFAULT_MANHOM = "AAAAAAAAAA";
    private static final String UPDATED_MANHOM = "BBBBBBBBBB";

    private static final String DEFAULT_TENNHOM = "AAAAAAAAAA";
    private static final String UPDATED_TENNHOM = "BBBBBBBBBB";

    @Autowired
    private BamsOutletRepository bamsOutletRepository;

    @Autowired
    private BamsOutletService bamsOutletService;

    @Autowired
    private BamsOutletSearchRepository bamsOutletSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBamsOutletMockMvc;

    private BamsOutlet bamsOutlet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BamsOutletResource bamsOutletResource = new BamsOutletResource(bamsOutletService);
        this.restBamsOutletMockMvc = MockMvcBuilders.standaloneSetup(bamsOutletResource)
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
    public static BamsOutlet createEntity(EntityManager em) {
        BamsOutlet bamsOutlet = new BamsOutlet()
            .dailyId(DEFAULT_DAILY_ID)
            .tendaily(DEFAULT_TENDAILY)
            .cvdl(DEFAULT_CVDL)
            .motaCvdl(DEFAULT_MOTA_CVDL)
            .diachi(DEFAULT_DIACHI)
            .dienthoai(DEFAULT_DIENTHOAI)
            .matinh(DEFAULT_MATINH)
            .activated(DEFAULT_ACTIVATED)
            .vphd(DEFAULT_VPHD)
            .diachivphd(DEFAULT_DIACHIVPHD)
            .maphongban(DEFAULT_MAPHONGBAN)
            .tenphongban(DEFAULT_TENPHONGBAN)
            .manhom(DEFAULT_MANHOM)
            .tennhom(DEFAULT_TENNHOM);
        return bamsOutlet;
    }

    @Before
    public void initTest() {
        bamsOutletSearchRepository.deleteAll();
        bamsOutlet = createEntity(em);
    }

    @Test
    @Transactional
    public void createBamsOutlet() throws Exception {
        int databaseSizeBeforeCreate = bamsOutletRepository.findAll().size();

        // Create the BamsOutlet
        restBamsOutletMockMvc.perform(post("/api/bams-outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsOutlet)))
            .andExpect(status().isCreated());

        // Validate the BamsOutlet in the database
        List<BamsOutlet> bamsOutletList = bamsOutletRepository.findAll();
        assertThat(bamsOutletList).hasSize(databaseSizeBeforeCreate + 1);
        BamsOutlet testBamsOutlet = bamsOutletList.get(bamsOutletList.size() - 1);
        assertThat(testBamsOutlet.getDailyId()).isEqualTo(DEFAULT_DAILY_ID);
        assertThat(testBamsOutlet.getTendaily()).isEqualTo(DEFAULT_TENDAILY);
        assertThat(testBamsOutlet.getCvdl()).isEqualTo(DEFAULT_CVDL);
        assertThat(testBamsOutlet.getMotaCvdl()).isEqualTo(DEFAULT_MOTA_CVDL);
        assertThat(testBamsOutlet.getDiachi()).isEqualTo(DEFAULT_DIACHI);
        assertThat(testBamsOutlet.getDienthoai()).isEqualTo(DEFAULT_DIENTHOAI);
        assertThat(testBamsOutlet.getMatinh()).isEqualTo(DEFAULT_MATINH);
        assertThat(testBamsOutlet.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testBamsOutlet.getVphd()).isEqualTo(DEFAULT_VPHD);
        assertThat(testBamsOutlet.getDiachivphd()).isEqualTo(DEFAULT_DIACHIVPHD);
        assertThat(testBamsOutlet.getMaphongban()).isEqualTo(DEFAULT_MAPHONGBAN);
        assertThat(testBamsOutlet.getTenphongban()).isEqualTo(DEFAULT_TENPHONGBAN);
        assertThat(testBamsOutlet.getManhom()).isEqualTo(DEFAULT_MANHOM);
        assertThat(testBamsOutlet.getTennhom()).isEqualTo(DEFAULT_TENNHOM);

        // Validate the BamsOutlet in Elasticsearch
        BamsOutlet bamsOutletEs = bamsOutletSearchRepository.findOne(testBamsOutlet.getId());
        assertThat(bamsOutletEs).isEqualToComparingFieldByField(testBamsOutlet);
    }

    @Test
    @Transactional
    public void createBamsOutletWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bamsOutletRepository.findAll().size();

        // Create the BamsOutlet with an existing ID
        bamsOutlet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBamsOutletMockMvc.perform(post("/api/bams-outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsOutlet)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BamsOutlet> bamsOutletList = bamsOutletRepository.findAll();
        assertThat(bamsOutletList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBamsOutlets() throws Exception {
        // Initialize the database
        bamsOutletRepository.saveAndFlush(bamsOutlet);

        // Get all the bamsOutletList
        restBamsOutletMockMvc.perform(get("/api/bams-outlets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bamsOutlet.getId().intValue())))
            .andExpect(jsonPath("$.[*].dailyId").value(hasItem(DEFAULT_DAILY_ID.toString())))
            .andExpect(jsonPath("$.[*].tendaily").value(hasItem(DEFAULT_TENDAILY.toString())))
            .andExpect(jsonPath("$.[*].cvdl").value(hasItem(DEFAULT_CVDL.toString())))
            .andExpect(jsonPath("$.[*].motaCvdl").value(hasItem(DEFAULT_MOTA_CVDL.toString())))
            .andExpect(jsonPath("$.[*].diachi").value(hasItem(DEFAULT_DIACHI.toString())))
            .andExpect(jsonPath("$.[*].dienthoai").value(hasItem(DEFAULT_DIENTHOAI.toString())))
            .andExpect(jsonPath("$.[*].matinh").value(hasItem(DEFAULT_MATINH.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].vphd").value(hasItem(DEFAULT_VPHD.toString())))
            .andExpect(jsonPath("$.[*].diachivphd").value(hasItem(DEFAULT_DIACHIVPHD.toString())))
            .andExpect(jsonPath("$.[*].maphongban").value(hasItem(DEFAULT_MAPHONGBAN.toString())))
            .andExpect(jsonPath("$.[*].tenphongban").value(hasItem(DEFAULT_TENPHONGBAN.toString())))
            .andExpect(jsonPath("$.[*].manhom").value(hasItem(DEFAULT_MANHOM.toString())))
            .andExpect(jsonPath("$.[*].tennhom").value(hasItem(DEFAULT_TENNHOM.toString())));
    }

    @Test
    @Transactional
    public void getBamsOutlet() throws Exception {
        // Initialize the database
        bamsOutletRepository.saveAndFlush(bamsOutlet);

        // Get the bamsOutlet
        restBamsOutletMockMvc.perform(get("/api/bams-outlets/{id}", bamsOutlet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bamsOutlet.getId().intValue()))
            .andExpect(jsonPath("$.dailyId").value(DEFAULT_DAILY_ID.toString()))
            .andExpect(jsonPath("$.tendaily").value(DEFAULT_TENDAILY.toString()))
            .andExpect(jsonPath("$.cvdl").value(DEFAULT_CVDL.toString()))
            .andExpect(jsonPath("$.motaCvdl").value(DEFAULT_MOTA_CVDL.toString()))
            .andExpect(jsonPath("$.diachi").value(DEFAULT_DIACHI.toString()))
            .andExpect(jsonPath("$.dienthoai").value(DEFAULT_DIENTHOAI.toString()))
            .andExpect(jsonPath("$.matinh").value(DEFAULT_MATINH.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.vphd").value(DEFAULT_VPHD.toString()))
            .andExpect(jsonPath("$.diachivphd").value(DEFAULT_DIACHIVPHD.toString()))
            .andExpect(jsonPath("$.maphongban").value(DEFAULT_MAPHONGBAN.toString()))
            .andExpect(jsonPath("$.tenphongban").value(DEFAULT_TENPHONGBAN.toString()))
            .andExpect(jsonPath("$.manhom").value(DEFAULT_MANHOM.toString()))
            .andExpect(jsonPath("$.tennhom").value(DEFAULT_TENNHOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBamsOutlet() throws Exception {
        // Get the bamsOutlet
        restBamsOutletMockMvc.perform(get("/api/bams-outlets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBamsOutlet() throws Exception {
        // Initialize the database
        bamsOutletService.save(bamsOutlet);

        int databaseSizeBeforeUpdate = bamsOutletRepository.findAll().size();

        // Update the bamsOutlet
        BamsOutlet updatedBamsOutlet = bamsOutletRepository.findOne(bamsOutlet.getId());
        updatedBamsOutlet
            .dailyId(UPDATED_DAILY_ID)
            .tendaily(UPDATED_TENDAILY)
            .cvdl(UPDATED_CVDL)
            .motaCvdl(UPDATED_MOTA_CVDL)
            .diachi(UPDATED_DIACHI)
            .dienthoai(UPDATED_DIENTHOAI)
            .matinh(UPDATED_MATINH)
            .activated(UPDATED_ACTIVATED)
            .vphd(UPDATED_VPHD)
            .diachivphd(UPDATED_DIACHIVPHD)
            .maphongban(UPDATED_MAPHONGBAN)
            .tenphongban(UPDATED_TENPHONGBAN)
            .manhom(UPDATED_MANHOM)
            .tennhom(UPDATED_TENNHOM);

        restBamsOutletMockMvc.perform(put("/api/bams-outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBamsOutlet)))
            .andExpect(status().isOk());

        // Validate the BamsOutlet in the database
        List<BamsOutlet> bamsOutletList = bamsOutletRepository.findAll();
        assertThat(bamsOutletList).hasSize(databaseSizeBeforeUpdate);
        BamsOutlet testBamsOutlet = bamsOutletList.get(bamsOutletList.size() - 1);
        assertThat(testBamsOutlet.getDailyId()).isEqualTo(UPDATED_DAILY_ID);
        assertThat(testBamsOutlet.getTendaily()).isEqualTo(UPDATED_TENDAILY);
        assertThat(testBamsOutlet.getCvdl()).isEqualTo(UPDATED_CVDL);
        assertThat(testBamsOutlet.getMotaCvdl()).isEqualTo(UPDATED_MOTA_CVDL);
        assertThat(testBamsOutlet.getDiachi()).isEqualTo(UPDATED_DIACHI);
        assertThat(testBamsOutlet.getDienthoai()).isEqualTo(UPDATED_DIENTHOAI);
        assertThat(testBamsOutlet.getMatinh()).isEqualTo(UPDATED_MATINH);
        assertThat(testBamsOutlet.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testBamsOutlet.getVphd()).isEqualTo(UPDATED_VPHD);
        assertThat(testBamsOutlet.getDiachivphd()).isEqualTo(UPDATED_DIACHIVPHD);
        assertThat(testBamsOutlet.getMaphongban()).isEqualTo(UPDATED_MAPHONGBAN);
        assertThat(testBamsOutlet.getTenphongban()).isEqualTo(UPDATED_TENPHONGBAN);
        assertThat(testBamsOutlet.getManhom()).isEqualTo(UPDATED_MANHOM);
        assertThat(testBamsOutlet.getTennhom()).isEqualTo(UPDATED_TENNHOM);

        // Validate the BamsOutlet in Elasticsearch
        BamsOutlet bamsOutletEs = bamsOutletSearchRepository.findOne(testBamsOutlet.getId());
        assertThat(bamsOutletEs).isEqualToComparingFieldByField(testBamsOutlet);
    }

    @Test
    @Transactional
    public void updateNonExistingBamsOutlet() throws Exception {
        int databaseSizeBeforeUpdate = bamsOutletRepository.findAll().size();

        // Create the BamsOutlet

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBamsOutletMockMvc.perform(put("/api/bams-outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsOutlet)))
            .andExpect(status().isCreated());

        // Validate the BamsOutlet in the database
        List<BamsOutlet> bamsOutletList = bamsOutletRepository.findAll();
        assertThat(bamsOutletList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBamsOutlet() throws Exception {
        // Initialize the database
        bamsOutletService.save(bamsOutlet);

        int databaseSizeBeforeDelete = bamsOutletRepository.findAll().size();

        // Get the bamsOutlet
        restBamsOutletMockMvc.perform(delete("/api/bams-outlets/{id}", bamsOutlet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bamsOutletExistsInEs = bamsOutletSearchRepository.exists(bamsOutlet.getId());
        assertThat(bamsOutletExistsInEs).isFalse();

        // Validate the database is empty
        List<BamsOutlet> bamsOutletList = bamsOutletRepository.findAll();
        assertThat(bamsOutletList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBamsOutlet() throws Exception {
        // Initialize the database
        bamsOutletService.save(bamsOutlet);

        // Search the bamsOutlet
        restBamsOutletMockMvc.perform(get("/api/_search/bams-outlets?query=id:" + bamsOutlet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bamsOutlet.getId().intValue())))
            .andExpect(jsonPath("$.[*].dailyId").value(hasItem(DEFAULT_DAILY_ID.toString())))
            .andExpect(jsonPath("$.[*].tendaily").value(hasItem(DEFAULT_TENDAILY.toString())))
            .andExpect(jsonPath("$.[*].cvdl").value(hasItem(DEFAULT_CVDL.toString())))
            .andExpect(jsonPath("$.[*].motaCvdl").value(hasItem(DEFAULT_MOTA_CVDL.toString())))
            .andExpect(jsonPath("$.[*].diachi").value(hasItem(DEFAULT_DIACHI.toString())))
            .andExpect(jsonPath("$.[*].dienthoai").value(hasItem(DEFAULT_DIENTHOAI.toString())))
            .andExpect(jsonPath("$.[*].matinh").value(hasItem(DEFAULT_MATINH.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].vphd").value(hasItem(DEFAULT_VPHD.toString())))
            .andExpect(jsonPath("$.[*].diachivphd").value(hasItem(DEFAULT_DIACHIVPHD.toString())))
            .andExpect(jsonPath("$.[*].maphongban").value(hasItem(DEFAULT_MAPHONGBAN.toString())))
            .andExpect(jsonPath("$.[*].tenphongban").value(hasItem(DEFAULT_TENPHONGBAN.toString())))
            .andExpect(jsonPath("$.[*].manhom").value(hasItem(DEFAULT_MANHOM.toString())))
            .andExpect(jsonPath("$.[*].tennhom").value(hasItem(DEFAULT_TENNHOM.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BamsOutlet.class);
    }
}
