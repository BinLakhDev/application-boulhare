package com.boulhare.backend.web.rest;

import com.boulhare.backend.BoulhareApp;
import com.boulhare.backend.domain.Banque;
import com.boulhare.backend.repository.BanqueRepository;
import com.boulhare.backend.service.BanqueQueryService;
import com.boulhare.backend.service.BanqueService;
import com.boulhare.backend.service.dto.BanqueDTO;
import com.boulhare.backend.service.mapper.BanqueMapper;
import com.boulhare.backend.web.rest.errors.ExceptionTranslator;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.boulhare.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BanqueResource REST controller.
 *
 * @see BanqueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BoulhareApp.class)
public class BanqueResourceIntTest {

    private static final String DEFAULT_NAME_BANQUE = "AAAAAAAAAA";
    private static final String UPDATED_NAME_BANQUE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_SIEGE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_SIEGE = "BBBBBBBBBB";

    private static final String DEFAULT_TEL_SIEGE = "AAAAAAAAAA";
    private static final String UPDATED_TEL_SIEGE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_BANQUE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_BANQUE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    @Autowired
    private BanqueRepository banqueRepository;

    @Autowired
    private BanqueMapper banqueMapper;

    @Autowired
    private BanqueService banqueService;

    @Autowired
    private BanqueQueryService banqueQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBanqueMockMvc;

    private Banque banque;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BanqueResource banqueResource = new BanqueResource(banqueService, banqueQueryService);
        this.restBanqueMockMvc = MockMvcBuilders.standaloneSetup(banqueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banque createEntity(EntityManager em) {
        Banque banque = new Banque()
            .nameBanque(DEFAULT_NAME_BANQUE)
            .adresseSiege(DEFAULT_ADRESSE_SIEGE)
            .telSiege(DEFAULT_TEL_SIEGE)
            .codeBanque(DEFAULT_CODE_BANQUE)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE);
        return banque;
    }

    @Before
    public void initTest() {
        banque = createEntity(em);
    }

    @Test
    @Transactional
    public void createBanque() throws Exception {
        int databaseSizeBeforeCreate = banqueRepository.findAll().size();

        // Create the Banque
        BanqueDTO banqueDTO = banqueMapper.toDto(banque);
        restBanqueMockMvc.perform(post("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banqueDTO)))
            .andExpect(status().isCreated());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeCreate + 1);
        Banque testBanque = banqueList.get(banqueList.size() - 1);
        assertThat(testBanque.getNameBanque()).isEqualTo(DEFAULT_NAME_BANQUE);
        assertThat(testBanque.getAdresseSiege()).isEqualTo(DEFAULT_ADRESSE_SIEGE);
        assertThat(testBanque.getTelSiege()).isEqualTo(DEFAULT_TEL_SIEGE);
        assertThat(testBanque.getCodeBanque()).isEqualTo(DEFAULT_CODE_BANQUE);
        assertThat(testBanque.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testBanque.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createBanqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = banqueRepository.findAll().size();

        // Create the Banque with an existing ID
        banque.setId(1L);
        BanqueDTO banqueDTO = banqueMapper.toDto(banque);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBanqueMockMvc.perform(post("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameBanqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = banqueRepository.findAll().size();
        // set the field null
        banque.setNameBanque(null);

        // Create the Banque, which fails.
        BanqueDTO banqueDTO = banqueMapper.toDto(banque);

        restBanqueMockMvc.perform(post("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banqueDTO)))
            .andExpect(status().isBadRequest());

        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBanques() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList
        restBanqueMockMvc.perform(get("/api/banques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banque.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameBanque").value(hasItem(DEFAULT_NAME_BANQUE.toString())))
            .andExpect(jsonPath("$.[*].adresseSiege").value(hasItem(DEFAULT_ADRESSE_SIEGE.toString())))
            .andExpect(jsonPath("$.[*].telSiege").value(hasItem(DEFAULT_TEL_SIEGE.toString())))
            .andExpect(jsonPath("$.[*].codeBanque").value(hasItem(DEFAULT_CODE_BANQUE.toString())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));
    }
    
    @Test
    @Transactional
    public void getBanque() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get the banque
        restBanqueMockMvc.perform(get("/api/banques/{id}", banque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(banque.getId().intValue()))
            .andExpect(jsonPath("$.nameBanque").value(DEFAULT_NAME_BANQUE.toString()))
            .andExpect(jsonPath("$.adresseSiege").value(DEFAULT_ADRESSE_SIEGE.toString()))
            .andExpect(jsonPath("$.telSiege").value(DEFAULT_TEL_SIEGE.toString()))
            .andExpect(jsonPath("$.codeBanque").value(DEFAULT_CODE_BANQUE.toString()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)));
    }

    @Test
    @Transactional
    public void getAllBanquesByNameBanqueIsEqualToSomething() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList where nameBanque equals to DEFAULT_NAME_BANQUE
        defaultBanqueShouldBeFound("nameBanque.equals=" + DEFAULT_NAME_BANQUE);

        // Get all the banqueList where nameBanque equals to UPDATED_NAME_BANQUE
        defaultBanqueShouldNotBeFound("nameBanque.equals=" + UPDATED_NAME_BANQUE);
    }

    @Test
    @Transactional
    public void getAllBanquesByNameBanqueIsInShouldWork() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList where nameBanque in DEFAULT_NAME_BANQUE or UPDATED_NAME_BANQUE
        defaultBanqueShouldBeFound("nameBanque.in=" + DEFAULT_NAME_BANQUE + "," + UPDATED_NAME_BANQUE);

        // Get all the banqueList where nameBanque equals to UPDATED_NAME_BANQUE
        defaultBanqueShouldNotBeFound("nameBanque.in=" + UPDATED_NAME_BANQUE);
    }

    @Test
    @Transactional
    public void getAllBanquesByNameBanqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList where nameBanque is not null
        defaultBanqueShouldBeFound("nameBanque.specified=true");

        // Get all the banqueList where nameBanque is null
        defaultBanqueShouldNotBeFound("nameBanque.specified=false");
    }

    @Test
    @Transactional
    public void getAllBanquesByAdresseSiegeIsEqualToSomething() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList where adresseSiege equals to DEFAULT_ADRESSE_SIEGE
        defaultBanqueShouldBeFound("adresseSiege.equals=" + DEFAULT_ADRESSE_SIEGE);

        // Get all the banqueList where adresseSiege equals to UPDATED_ADRESSE_SIEGE
        defaultBanqueShouldNotBeFound("adresseSiege.equals=" + UPDATED_ADRESSE_SIEGE);
    }

    @Test
    @Transactional
    public void getAllBanquesByAdresseSiegeIsInShouldWork() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList where adresseSiege in DEFAULT_ADRESSE_SIEGE or UPDATED_ADRESSE_SIEGE
        defaultBanqueShouldBeFound("adresseSiege.in=" + DEFAULT_ADRESSE_SIEGE + "," + UPDATED_ADRESSE_SIEGE);

        // Get all the banqueList where adresseSiege equals to UPDATED_ADRESSE_SIEGE
        defaultBanqueShouldNotBeFound("adresseSiege.in=" + UPDATED_ADRESSE_SIEGE);
    }

    @Test
    @Transactional
    public void getAllBanquesByAdresseSiegeIsNullOrNotNull() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList where adresseSiege is not null
        defaultBanqueShouldBeFound("adresseSiege.specified=true");

        // Get all the banqueList where adresseSiege is null
        defaultBanqueShouldNotBeFound("adresseSiege.specified=false");
    }

    @Test
    @Transactional
    public void getAllBanquesByTelSiegeIsEqualToSomething() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList where telSiege equals to DEFAULT_TEL_SIEGE
        defaultBanqueShouldBeFound("telSiege.equals=" + DEFAULT_TEL_SIEGE);

        // Get all the banqueList where telSiege equals to UPDATED_TEL_SIEGE
        defaultBanqueShouldNotBeFound("telSiege.equals=" + UPDATED_TEL_SIEGE);
    }

    @Test
    @Transactional
    public void getAllBanquesByTelSiegeIsInShouldWork() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList where telSiege in DEFAULT_TEL_SIEGE or UPDATED_TEL_SIEGE
        defaultBanqueShouldBeFound("telSiege.in=" + DEFAULT_TEL_SIEGE + "," + UPDATED_TEL_SIEGE);

        // Get all the banqueList where telSiege equals to UPDATED_TEL_SIEGE
        defaultBanqueShouldNotBeFound("telSiege.in=" + UPDATED_TEL_SIEGE);
    }

    @Test
    @Transactional
    public void getAllBanquesByTelSiegeIsNullOrNotNull() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList where telSiege is not null
        defaultBanqueShouldBeFound("telSiege.specified=true");

        // Get all the banqueList where telSiege is null
        defaultBanqueShouldNotBeFound("telSiege.specified=false");
    }

    @Test
    @Transactional
    public void getAllBanquesByCodeBanqueIsEqualToSomething() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList where codeBanque equals to DEFAULT_CODE_BANQUE
        defaultBanqueShouldBeFound("codeBanque.equals=" + DEFAULT_CODE_BANQUE);

        // Get all the banqueList where codeBanque equals to UPDATED_CODE_BANQUE
        defaultBanqueShouldNotBeFound("codeBanque.equals=" + UPDATED_CODE_BANQUE);
    }

    @Test
    @Transactional
    public void getAllBanquesByCodeBanqueIsInShouldWork() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList where codeBanque in DEFAULT_CODE_BANQUE or UPDATED_CODE_BANQUE
        defaultBanqueShouldBeFound("codeBanque.in=" + DEFAULT_CODE_BANQUE + "," + UPDATED_CODE_BANQUE);

        // Get all the banqueList where codeBanque equals to UPDATED_CODE_BANQUE
        defaultBanqueShouldNotBeFound("codeBanque.in=" + UPDATED_CODE_BANQUE);
    }

    @Test
    @Transactional
    public void getAllBanquesByCodeBanqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList where codeBanque is not null
        defaultBanqueShouldBeFound("codeBanque.specified=true");

        // Get all the banqueList where codeBanque is null
        defaultBanqueShouldNotBeFound("codeBanque.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBanqueShouldBeFound(String filter) throws Exception {
        restBanqueMockMvc.perform(get("/api/banques?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banque.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameBanque").value(hasItem(DEFAULT_NAME_BANQUE)))
            .andExpect(jsonPath("$.[*].adresseSiege").value(hasItem(DEFAULT_ADRESSE_SIEGE)))
            .andExpect(jsonPath("$.[*].telSiege").value(hasItem(DEFAULT_TEL_SIEGE)))
            .andExpect(jsonPath("$.[*].codeBanque").value(hasItem(DEFAULT_CODE_BANQUE)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));

        // Check, that the count call also returns 1
        restBanqueMockMvc.perform(get("/api/banques/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBanqueShouldNotBeFound(String filter) throws Exception {
        restBanqueMockMvc.perform(get("/api/banques?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBanqueMockMvc.perform(get("/api/banques/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBanque() throws Exception {
        // Get the banque
        restBanqueMockMvc.perform(get("/api/banques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBanque() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        int databaseSizeBeforeUpdate = banqueRepository.findAll().size();

        // Update the banque
        Banque updatedBanque = banqueRepository.findById(banque.getId()).get();
        // Disconnect from session so that the updates on updatedBanque are not directly saved in db
        em.detach(updatedBanque);
        updatedBanque
            .nameBanque(UPDATED_NAME_BANQUE)
            .adresseSiege(UPDATED_ADRESSE_SIEGE)
            .telSiege(UPDATED_TEL_SIEGE)
            .codeBanque(UPDATED_CODE_BANQUE)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE);
        BanqueDTO banqueDTO = banqueMapper.toDto(updatedBanque);

        restBanqueMockMvc.perform(put("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banqueDTO)))
            .andExpect(status().isOk());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeUpdate);
        Banque testBanque = banqueList.get(banqueList.size() - 1);
        assertThat(testBanque.getNameBanque()).isEqualTo(UPDATED_NAME_BANQUE);
        assertThat(testBanque.getAdresseSiege()).isEqualTo(UPDATED_ADRESSE_SIEGE);
        assertThat(testBanque.getTelSiege()).isEqualTo(UPDATED_TEL_SIEGE);
        assertThat(testBanque.getCodeBanque()).isEqualTo(UPDATED_CODE_BANQUE);
        assertThat(testBanque.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testBanque.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingBanque() throws Exception {
        int databaseSizeBeforeUpdate = banqueRepository.findAll().size();

        // Create the Banque
        BanqueDTO banqueDTO = banqueMapper.toDto(banque);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanqueMockMvc.perform(put("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBanque() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        int databaseSizeBeforeDelete = banqueRepository.findAll().size();

        // Delete the banque
        restBanqueMockMvc.perform(delete("/api/banques/{id}", banque.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banque.class);
        Banque banque1 = new Banque();
        banque1.setId(1L);
        Banque banque2 = new Banque();
        banque2.setId(banque1.getId());
        assertThat(banque1).isEqualTo(banque2);
        banque2.setId(2L);
        assertThat(banque1).isNotEqualTo(banque2);
        banque1.setId(null);
        assertThat(banque1).isNotEqualTo(banque2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BanqueDTO.class);
        BanqueDTO banqueDTO1 = new BanqueDTO();
        banqueDTO1.setId(1L);
        BanqueDTO banqueDTO2 = new BanqueDTO();
        assertThat(banqueDTO1).isNotEqualTo(banqueDTO2);
        banqueDTO2.setId(banqueDTO1.getId());
        assertThat(banqueDTO1).isEqualTo(banqueDTO2);
        banqueDTO2.setId(2L);
        assertThat(banqueDTO1).isNotEqualTo(banqueDTO2);
        banqueDTO1.setId(null);
        assertThat(banqueDTO1).isNotEqualTo(banqueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(banqueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(banqueMapper.fromId(null)).isNull();
    }
}
