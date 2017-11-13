package com.baoviet.login.web.rest;

import com.baoviet.login.BvloginApp;

import com.baoviet.login.domain.BamsUser;
import com.baoviet.login.repository.BamsUserRepository;
import com.baoviet.login.service.BamsUserService;
import com.baoviet.login.repository.search.BamsUserSearchRepository;
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
 * Test class for the BamsUserResource REST controller.
 *
 * @see BamsUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BvloginApp.class)
public class BamsUserResourceIntTest {

    private static final String DEFAULT_UNAME = "AAAAAAAAAA";
    private static final String UPDATED_UNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORDHASH = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORDHASH = "BBBBBBBBBB";

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGEURL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEURL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_LANGKEY = "AAAAA";
    private static final String UPDATED_LANGKEY = "BBBBB";

    private static final String DEFAULT_ACTIVATIONKEY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATIONKEY = "BBBBBBBBBB";

    private static final String DEFAULT_RESETKEY = "AAAAAAAAAA";
    private static final String UPDATED_RESETKEY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATEDBY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDBY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATEDDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATEDDATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RESETDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RESETDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LASTMODIFIEDBY = "AAAAAAAAAA";
    private static final String UPDATED_LASTMODIFIEDBY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LASTMODIFIEDDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LASTMODIFIEDDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_USER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_OUTLET_ID = "AAAAAAAAAA";
    private static final String UPDATED_OUTLET_ID = "BBBBBBBBBB";

    private static final String DEFAULT_COMP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBI_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOBI_PHONE = "BBBBBBBBBB";

    @Autowired
    private BamsUserRepository bamsUserRepository;

    @Autowired
    private BamsUserService bamsUserService;

    @Autowired
    private BamsUserSearchRepository bamsUserSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBamsUserMockMvc;

    private BamsUser bamsUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BamsUserResource bamsUserResource = new BamsUserResource(bamsUserService);
        this.restBamsUserMockMvc = MockMvcBuilders.standaloneSetup(bamsUserResource)
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
    public static BamsUser createEntity(EntityManager em) {
        BamsUser bamsUser = new BamsUser()
            .uname(DEFAULT_UNAME)
            .passwordhash(DEFAULT_PASSWORDHASH)
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .email(DEFAULT_EMAIL)
            .gender(DEFAULT_GENDER)
            .imageurl(DEFAULT_IMAGEURL)
            .activated(DEFAULT_ACTIVATED)
            .langkey(DEFAULT_LANGKEY)
            .activationkey(DEFAULT_ACTIVATIONKEY)
            .resetkey(DEFAULT_RESETKEY)
            .createdby(DEFAULT_CREATEDBY)
            .createddate(DEFAULT_CREATEDDATE)
            .resetdate(DEFAULT_RESETDATE)
            .lastmodifiedby(DEFAULT_LASTMODIFIEDBY)
            .lastmodifieddate(DEFAULT_LASTMODIFIEDDATE)
            .user_type(DEFAULT_USER_TYPE)
            .outlet_id(DEFAULT_OUTLET_ID)
            .comp_code(DEFAULT_COMP_CODE)
            .mobi_phone(DEFAULT_MOBI_PHONE);
        return bamsUser;
    }

    @Before
    public void initTest() {
        bamsUserSearchRepository.deleteAll();
        bamsUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createBamsUser() throws Exception {
        int databaseSizeBeforeCreate = bamsUserRepository.findAll().size();

        // Create the BamsUser
        restBamsUserMockMvc.perform(post("/api/bams-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsUser)))
            .andExpect(status().isCreated());

        // Validate the BamsUser in the database
        List<BamsUser> bamsUserList = bamsUserRepository.findAll();
        assertThat(bamsUserList).hasSize(databaseSizeBeforeCreate + 1);
        BamsUser testBamsUser = bamsUserList.get(bamsUserList.size() - 1);
        assertThat(testBamsUser.getUname()).isEqualTo(DEFAULT_UNAME);
        assertThat(testBamsUser.getPasswordhash()).isEqualTo(DEFAULT_PASSWORDHASH);
        assertThat(testBamsUser.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testBamsUser.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testBamsUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBamsUser.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testBamsUser.getImageurl()).isEqualTo(DEFAULT_IMAGEURL);
        assertThat(testBamsUser.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testBamsUser.getLangkey()).isEqualTo(DEFAULT_LANGKEY);
        assertThat(testBamsUser.getActivationkey()).isEqualTo(DEFAULT_ACTIVATIONKEY);
        assertThat(testBamsUser.getResetkey()).isEqualTo(DEFAULT_RESETKEY);
        assertThat(testBamsUser.getCreatedby()).isEqualTo(DEFAULT_CREATEDBY);
        assertThat(testBamsUser.getCreateddate()).isEqualTo(DEFAULT_CREATEDDATE);
        assertThat(testBamsUser.getResetdate()).isEqualTo(DEFAULT_RESETDATE);
        assertThat(testBamsUser.getLastmodifiedby()).isEqualTo(DEFAULT_LASTMODIFIEDBY);
        assertThat(testBamsUser.getLastmodifieddate()).isEqualTo(DEFAULT_LASTMODIFIEDDATE);
        assertThat(testBamsUser.getUser_type()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testBamsUser.getOutlet_id()).isEqualTo(DEFAULT_OUTLET_ID);
        assertThat(testBamsUser.getComp_code()).isEqualTo(DEFAULT_COMP_CODE);
        assertThat(testBamsUser.getMobi_phone()).isEqualTo(DEFAULT_MOBI_PHONE);

        // Validate the BamsUser in Elasticsearch
        BamsUser bamsUserEs = bamsUserSearchRepository.findOne(testBamsUser.getId());
        assertThat(bamsUserEs).isEqualToComparingFieldByField(testBamsUser);
    }

    @Test
    @Transactional
    public void createBamsUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bamsUserRepository.findAll().size();

        // Create the BamsUser with an existing ID
        bamsUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBamsUserMockMvc.perform(post("/api/bams-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsUser)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BamsUser> bamsUserList = bamsUserRepository.findAll();
        assertThat(bamsUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bamsUserRepository.findAll().size();
        // set the field null
        bamsUser.setUname(null);

        // Create the BamsUser, which fails.

        restBamsUserMockMvc.perform(post("/api/bams-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsUser)))
            .andExpect(status().isBadRequest());

        List<BamsUser> bamsUserList = bamsUserRepository.findAll();
        assertThat(bamsUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordhashIsRequired() throws Exception {
        int databaseSizeBeforeTest = bamsUserRepository.findAll().size();
        // set the field null
        bamsUser.setPasswordhash(null);

        // Create the BamsUser, which fails.

        restBamsUserMockMvc.perform(post("/api/bams-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsUser)))
            .andExpect(status().isBadRequest());

        List<BamsUser> bamsUserList = bamsUserRepository.findAll();
        assertThat(bamsUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBamsUsers() throws Exception {
        // Initialize the database
        bamsUserRepository.saveAndFlush(bamsUser);

        // Get all the bamsUserList
        restBamsUserMockMvc.perform(get("/api/bams-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bamsUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].uname").value(hasItem(DEFAULT_UNAME.toString())))
            .andExpect(jsonPath("$.[*].passwordhash").value(hasItem(DEFAULT_PASSWORDHASH.toString())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].imageurl").value(hasItem(DEFAULT_IMAGEURL.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].langkey").value(hasItem(DEFAULT_LANGKEY.toString())))
            .andExpect(jsonPath("$.[*].activationkey").value(hasItem(DEFAULT_ACTIVATIONKEY.toString())))
            .andExpect(jsonPath("$.[*].resetkey").value(hasItem(DEFAULT_RESETKEY.toString())))
            .andExpect(jsonPath("$.[*].createdby").value(hasItem(DEFAULT_CREATEDBY.toString())))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(DEFAULT_CREATEDDATE.toString())))
            .andExpect(jsonPath("$.[*].resetdate").value(hasItem(DEFAULT_RESETDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifiedby").value(hasItem(DEFAULT_LASTMODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifieddate").value(hasItem(DEFAULT_LASTMODIFIEDDATE.toString())))
            .andExpect(jsonPath("$.[*].user_type").value(hasItem(DEFAULT_USER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].outlet_id").value(hasItem(DEFAULT_OUTLET_ID.toString())))
            .andExpect(jsonPath("$.[*].comp_code").value(hasItem(DEFAULT_COMP_CODE.toString())))
            .andExpect(jsonPath("$.[*].mobi_phone").value(hasItem(DEFAULT_MOBI_PHONE.toString())));
    }

    @Test
    @Transactional
    public void getBamsUser() throws Exception {
        // Initialize the database
        bamsUserRepository.saveAndFlush(bamsUser);

        // Get the bamsUser
        restBamsUserMockMvc.perform(get("/api/bams-users/{id}", bamsUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bamsUser.getId().intValue()))
            .andExpect(jsonPath("$.uname").value(DEFAULT_UNAME.toString()))
            .andExpect(jsonPath("$.passwordhash").value(DEFAULT_PASSWORDHASH.toString()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.imageurl").value(DEFAULT_IMAGEURL.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.langkey").value(DEFAULT_LANGKEY.toString()))
            .andExpect(jsonPath("$.activationkey").value(DEFAULT_ACTIVATIONKEY.toString()))
            .andExpect(jsonPath("$.resetkey").value(DEFAULT_RESETKEY.toString()))
            .andExpect(jsonPath("$.createdby").value(DEFAULT_CREATEDBY.toString()))
            .andExpect(jsonPath("$.createddate").value(DEFAULT_CREATEDDATE.toString()))
            .andExpect(jsonPath("$.resetdate").value(DEFAULT_RESETDATE.toString()))
            .andExpect(jsonPath("$.lastmodifiedby").value(DEFAULT_LASTMODIFIEDBY.toString()))
            .andExpect(jsonPath("$.lastmodifieddate").value(DEFAULT_LASTMODIFIEDDATE.toString()))
            .andExpect(jsonPath("$.user_type").value(DEFAULT_USER_TYPE.toString()))
            .andExpect(jsonPath("$.outlet_id").value(DEFAULT_OUTLET_ID.toString()))
            .andExpect(jsonPath("$.comp_code").value(DEFAULT_COMP_CODE.toString()))
            .andExpect(jsonPath("$.mobi_phone").value(DEFAULT_MOBI_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBamsUser() throws Exception {
        // Get the bamsUser
        restBamsUserMockMvc.perform(get("/api/bams-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBamsUser() throws Exception {
        // Initialize the database
        bamsUserService.save(bamsUser);

        int databaseSizeBeforeUpdate = bamsUserRepository.findAll().size();

        // Update the bamsUser
        BamsUser updatedBamsUser = bamsUserRepository.findOne(bamsUser.getId());
        updatedBamsUser
            .uname(UPDATED_UNAME)
            .passwordhash(UPDATED_PASSWORDHASH)
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .gender(UPDATED_GENDER)
            .imageurl(UPDATED_IMAGEURL)
            .activated(UPDATED_ACTIVATED)
            .langkey(UPDATED_LANGKEY)
            .activationkey(UPDATED_ACTIVATIONKEY)
            .resetkey(UPDATED_RESETKEY)
            .createdby(UPDATED_CREATEDBY)
            .createddate(UPDATED_CREATEDDATE)
            .resetdate(UPDATED_RESETDATE)
            .lastmodifiedby(UPDATED_LASTMODIFIEDBY)
            .lastmodifieddate(UPDATED_LASTMODIFIEDDATE)
            .user_type(UPDATED_USER_TYPE)
            .outlet_id(UPDATED_OUTLET_ID)
            .comp_code(UPDATED_COMP_CODE)
            .mobi_phone(UPDATED_MOBI_PHONE);

        restBamsUserMockMvc.perform(put("/api/bams-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBamsUser)))
            .andExpect(status().isOk());

        // Validate the BamsUser in the database
        List<BamsUser> bamsUserList = bamsUserRepository.findAll();
        assertThat(bamsUserList).hasSize(databaseSizeBeforeUpdate);
        BamsUser testBamsUser = bamsUserList.get(bamsUserList.size() - 1);
        assertThat(testBamsUser.getUname()).isEqualTo(UPDATED_UNAME);
        assertThat(testBamsUser.getPasswordhash()).isEqualTo(UPDATED_PASSWORDHASH);
        assertThat(testBamsUser.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testBamsUser.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testBamsUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBamsUser.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testBamsUser.getImageurl()).isEqualTo(UPDATED_IMAGEURL);
        assertThat(testBamsUser.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testBamsUser.getLangkey()).isEqualTo(UPDATED_LANGKEY);
        assertThat(testBamsUser.getActivationkey()).isEqualTo(UPDATED_ACTIVATIONKEY);
        assertThat(testBamsUser.getResetkey()).isEqualTo(UPDATED_RESETKEY);
        assertThat(testBamsUser.getCreatedby()).isEqualTo(UPDATED_CREATEDBY);
        assertThat(testBamsUser.getCreateddate()).isEqualTo(UPDATED_CREATEDDATE);
        assertThat(testBamsUser.getResetdate()).isEqualTo(UPDATED_RESETDATE);
        assertThat(testBamsUser.getLastmodifiedby()).isEqualTo(UPDATED_LASTMODIFIEDBY);
        assertThat(testBamsUser.getLastmodifieddate()).isEqualTo(UPDATED_LASTMODIFIEDDATE);
        assertThat(testBamsUser.getUser_type()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testBamsUser.getOutlet_id()).isEqualTo(UPDATED_OUTLET_ID);
        assertThat(testBamsUser.getComp_code()).isEqualTo(UPDATED_COMP_CODE);
        assertThat(testBamsUser.getMobi_phone()).isEqualTo(UPDATED_MOBI_PHONE);

        // Validate the BamsUser in Elasticsearch
        BamsUser bamsUserEs = bamsUserSearchRepository.findOne(testBamsUser.getId());
        assertThat(bamsUserEs).isEqualToComparingFieldByField(testBamsUser);
    }

    @Test
    @Transactional
    public void updateNonExistingBamsUser() throws Exception {
        int databaseSizeBeforeUpdate = bamsUserRepository.findAll().size();

        // Create the BamsUser

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBamsUserMockMvc.perform(put("/api/bams-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bamsUser)))
            .andExpect(status().isCreated());

        // Validate the BamsUser in the database
        List<BamsUser> bamsUserList = bamsUserRepository.findAll();
        assertThat(bamsUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBamsUser() throws Exception {
        // Initialize the database
        bamsUserService.save(bamsUser);

        int databaseSizeBeforeDelete = bamsUserRepository.findAll().size();

        // Get the bamsUser
        restBamsUserMockMvc.perform(delete("/api/bams-users/{id}", bamsUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bamsUserExistsInEs = bamsUserSearchRepository.exists(bamsUser.getId());
        assertThat(bamsUserExistsInEs).isFalse();

        // Validate the database is empty
        List<BamsUser> bamsUserList = bamsUserRepository.findAll();
        assertThat(bamsUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBamsUser() throws Exception {
        // Initialize the database
        bamsUserService.save(bamsUser);

        // Search the bamsUser
        restBamsUserMockMvc.perform(get("/api/_search/bams-users?query=id:" + bamsUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bamsUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].uname").value(hasItem(DEFAULT_UNAME.toString())))
            .andExpect(jsonPath("$.[*].passwordhash").value(hasItem(DEFAULT_PASSWORDHASH.toString())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].imageurl").value(hasItem(DEFAULT_IMAGEURL.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].langkey").value(hasItem(DEFAULT_LANGKEY.toString())))
            .andExpect(jsonPath("$.[*].activationkey").value(hasItem(DEFAULT_ACTIVATIONKEY.toString())))
            .andExpect(jsonPath("$.[*].resetkey").value(hasItem(DEFAULT_RESETKEY.toString())))
            .andExpect(jsonPath("$.[*].createdby").value(hasItem(DEFAULT_CREATEDBY.toString())))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(DEFAULT_CREATEDDATE.toString())))
            .andExpect(jsonPath("$.[*].resetdate").value(hasItem(DEFAULT_RESETDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifiedby").value(hasItem(DEFAULT_LASTMODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifieddate").value(hasItem(DEFAULT_LASTMODIFIEDDATE.toString())))
            .andExpect(jsonPath("$.[*].user_type").value(hasItem(DEFAULT_USER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].outlet_id").value(hasItem(DEFAULT_OUTLET_ID.toString())))
            .andExpect(jsonPath("$.[*].comp_code").value(hasItem(DEFAULT_COMP_CODE.toString())))
            .andExpect(jsonPath("$.[*].mobi_phone").value(hasItem(DEFAULT_MOBI_PHONE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BamsUser.class);
    }
}
