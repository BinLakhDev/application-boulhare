package com.boulhare.backend.web.rest;

import com.boulhare.backend.BoulhareApp;
import com.boulhare.backend.domain.Agence;
import com.boulhare.backend.domain.Banque;
import com.boulhare.backend.repository.AgenceRepository;
import com.boulhare.backend.service.AgenceQueryService;
import com.boulhare.backend.service.AgenceService;
import com.boulhare.backend.service.dto.AgenceDTO;
import com.boulhare.backend.service.mapper.AgenceMapper;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.boulhare.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AgenceResource REST controller.
 *
 * @see AgenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BoulhareApp.class)
public class AgenceResourceIntTest {

    private static final String DEFAULT_CODE_AGENCE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_AGENCE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_AGENCE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_AGENCE = "BBBBBBBBBB";

    private static final String DEFAULT_TEL_SIEGE = "AAAAAAAAAA";
    private static final String UPDATED_TEL_SIEGE = "BBBBBBBBBB";

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private AgenceMapper agenceMapper;

    @Autowired
    private AgenceService agenceService;

    @Autowired
    private AgenceQueryService agenceQueryService;

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

    private MockMvc restAgenceMockMvc;

    private Agence agence;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgenceResource agenceResource = new AgenceResource(agenceService, agenceQueryService);
        this.restAgenceMockMvc = MockMvcBuilders.standaloneSetup(agenceResource)
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
    public static Agence createEntity(EntityManager em) {
        Agence agence = new Agence()
            .codeAgence(DEFAULT_CODE_AGENCE)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .adresseAgence(DEFAULT_ADRESSE_AGENCE)
            .telSiege(DEFAULT_TEL_SIEGE);
        return agence;
    }

    @Before
    public void initTest() {
        agence = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgence() throws Exception {
        int databaseSizeBeforeCreate = agenceRepository.findAll().size();

        // Create the Agence
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);
        restAgenceMockMvc.perform(post("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeCreate + 1);
        Agence testAgence = agenceList.get(agenceList.size() - 1);
        assertThat(testAgence.getCodeAgence()).isEqualTo(DEFAULT_CODE_AGENCE);
        assertThat(testAgence.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testAgence.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testAgence.getAdresseAgence()).isEqualTo(DEFAULT_ADRESSE_AGENCE);
        assertThat(testAgence.getTelSiege()).isEqualTo(DEFAULT_TEL_SIEGE);
    }

    @Test
    @Transactional
    public void createAgenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agenceRepository.findAll().size();

        // Create the Agence with an existing ID
        agence.setId(1L);
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgenceMockMvc.perform(post("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeAgenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = agenceRepository.findAll().size();
        // set the field null
        agence.setCodeAgence(null);

        // Create the Agence, which fails.
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        restAgenceMockMvc.perform(post("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenceDTO)))
            .andExpect(status().isBadRequest());

        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = agenceRepository.findAll().size();
        // set the field null
        agence.setLongitude(null);

        // Create the Agence, which fails.
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        restAgenceMockMvc.perform(post("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenceDTO)))
            .andExpect(status().isBadRequest());

        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = agenceRepository.findAll().size();
        // set the field null
        agence.setLatitude(null);

        // Create the Agence, which fails.
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        restAgenceMockMvc.perform(post("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenceDTO)))
            .andExpect(status().isBadRequest());

        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseAgenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = agenceRepository.findAll().size();
        // set the field null
        agence.setAdresseAgence(null);

        // Create the Agence, which fails.
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        restAgenceMockMvc.perform(post("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenceDTO)))
            .andExpect(status().isBadRequest());

        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelSiegeIsRequired() throws Exception {
        int databaseSizeBeforeTest = agenceRepository.findAll().size();
        // set the field null
        agence.setTelSiege(null);

        // Create the Agence, which fails.
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        restAgenceMockMvc.perform(post("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenceDTO)))
            .andExpect(status().isBadRequest());

        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgences() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList
        restAgenceMockMvc.perform(get("/api/agences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agence.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeAgence").value(hasItem(DEFAULT_CODE_AGENCE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.toString())))
            .andExpect(jsonPath("$.[*].adresseAgence").value(hasItem(DEFAULT_ADRESSE_AGENCE.toString())))
            .andExpect(jsonPath("$.[*].telSiege").value(hasItem(DEFAULT_TEL_SIEGE.toString())));
    }
    
    @Test
    @Transactional
    public void getAgence() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get the agence
        restAgenceMockMvc.perform(get("/api/agences/{id}", agence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agence.getId().intValue()))
            .andExpect(jsonPath("$.codeAgence").value(DEFAULT_CODE_AGENCE.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.toString()))
            .andExpect(jsonPath("$.adresseAgence").value(DEFAULT_ADRESSE_AGENCE.toString()))
            .andExpect(jsonPath("$.telSiege").value(DEFAULT_TEL_SIEGE.toString()));
    }

    @Test
    @Transactional
    public void getAllAgencesByCodeAgenceIsEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where codeAgence equals to DEFAULT_CODE_AGENCE
        defaultAgenceShouldBeFound("codeAgence.equals=" + DEFAULT_CODE_AGENCE);

        // Get all the agenceList where codeAgence equals to UPDATED_CODE_AGENCE
        defaultAgenceShouldNotBeFound("codeAgence.equals=" + UPDATED_CODE_AGENCE);
    }

    @Test
    @Transactional
    public void getAllAgencesByCodeAgenceIsInShouldWork() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where codeAgence in DEFAULT_CODE_AGENCE or UPDATED_CODE_AGENCE
        defaultAgenceShouldBeFound("codeAgence.in=" + DEFAULT_CODE_AGENCE + "," + UPDATED_CODE_AGENCE);

        // Get all the agenceList where codeAgence equals to UPDATED_CODE_AGENCE
        defaultAgenceShouldNotBeFound("codeAgence.in=" + UPDATED_CODE_AGENCE);
    }

    @Test
    @Transactional
    public void getAllAgencesByCodeAgenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where codeAgence is not null
        defaultAgenceShouldBeFound("codeAgence.specified=true");

        // Get all the agenceList where codeAgence is null
        defaultAgenceShouldNotBeFound("codeAgence.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencesByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where longitude equals to DEFAULT_LONGITUDE
        defaultAgenceShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the agenceList where longitude equals to UPDATED_LONGITUDE
        defaultAgenceShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllAgencesByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultAgenceShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the agenceList where longitude equals to UPDATED_LONGITUDE
        defaultAgenceShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllAgencesByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where longitude is not null
        defaultAgenceShouldBeFound("longitude.specified=true");

        // Get all the agenceList where longitude is null
        defaultAgenceShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencesByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where latitude equals to DEFAULT_LATITUDE
        defaultAgenceShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the agenceList where latitude equals to UPDATED_LATITUDE
        defaultAgenceShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllAgencesByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultAgenceShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the agenceList where latitude equals to UPDATED_LATITUDE
        defaultAgenceShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllAgencesByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where latitude is not null
        defaultAgenceShouldBeFound("latitude.specified=true");

        // Get all the agenceList where latitude is null
        defaultAgenceShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencesByAdresseAgenceIsEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where adresseAgence equals to DEFAULT_ADRESSE_AGENCE
        defaultAgenceShouldBeFound("adresseAgence.equals=" + DEFAULT_ADRESSE_AGENCE);

        // Get all the agenceList where adresseAgence equals to UPDATED_ADRESSE_AGENCE
        defaultAgenceShouldNotBeFound("adresseAgence.equals=" + UPDATED_ADRESSE_AGENCE);
    }

    @Test
    @Transactional
    public void getAllAgencesByAdresseAgenceIsInShouldWork() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where adresseAgence in DEFAULT_ADRESSE_AGENCE or UPDATED_ADRESSE_AGENCE
        defaultAgenceShouldBeFound("adresseAgence.in=" + DEFAULT_ADRESSE_AGENCE + "," + UPDATED_ADRESSE_AGENCE);

        // Get all the agenceList where adresseAgence equals to UPDATED_ADRESSE_AGENCE
        defaultAgenceShouldNotBeFound("adresseAgence.in=" + UPDATED_ADRESSE_AGENCE);
    }

    @Test
    @Transactional
    public void getAllAgencesByAdresseAgenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where adresseAgence is not null
        defaultAgenceShouldBeFound("adresseAgence.specified=true");

        // Get all the agenceList where adresseAgence is null
        defaultAgenceShouldNotBeFound("adresseAgence.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencesByTelSiegeIsEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where telSiege equals to DEFAULT_TEL_SIEGE
        defaultAgenceShouldBeFound("telSiege.equals=" + DEFAULT_TEL_SIEGE);

        // Get all the agenceList where telSiege equals to UPDATED_TEL_SIEGE
        defaultAgenceShouldNotBeFound("telSiege.equals=" + UPDATED_TEL_SIEGE);
    }

    @Test
    @Transactional
    public void getAllAgencesByTelSiegeIsInShouldWork() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where telSiege in DEFAULT_TEL_SIEGE or UPDATED_TEL_SIEGE
        defaultAgenceShouldBeFound("telSiege.in=" + DEFAULT_TEL_SIEGE + "," + UPDATED_TEL_SIEGE);

        // Get all the agenceList where telSiege equals to UPDATED_TEL_SIEGE
        defaultAgenceShouldNotBeFound("telSiege.in=" + UPDATED_TEL_SIEGE);
    }

    @Test
    @Transactional
    public void getAllAgencesByTelSiegeIsNullOrNotNull() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where telSiege is not null
        defaultAgenceShouldBeFound("telSiege.specified=true");

        // Get all the agenceList where telSiege is null
        defaultAgenceShouldNotBeFound("telSiege.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencesByBanqueIsEqualToSomething() throws Exception {
        // Initialize the database
        Banque banque = BanqueResourceIntTest.createEntity(em);
        em.persist(banque);
        em.flush();
        agence.setBanque(banque);
        agenceRepository.saveAndFlush(agence);
        Long banqueId = banque.getId();

        // Get all the agenceList where banque equals to banqueId
        defaultAgenceShouldBeFound("banqueId.equals=" + banqueId);

        // Get all the agenceList where banque equals to banqueId + 1
        defaultAgenceShouldNotBeFound("banqueId.equals=" + (banqueId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAgenceShouldBeFound(String filter) throws Exception {
        restAgenceMockMvc.perform(get("/api/agences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agence.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeAgence").value(hasItem(DEFAULT_CODE_AGENCE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].adresseAgence").value(hasItem(DEFAULT_ADRESSE_AGENCE)))
            .andExpect(jsonPath("$.[*].telSiege").value(hasItem(DEFAULT_TEL_SIEGE)));

        // Check, that the count call also returns 1
        restAgenceMockMvc.perform(get("/api/agences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAgenceShouldNotBeFound(String filter) throws Exception {
        restAgenceMockMvc.perform(get("/api/agences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAgenceMockMvc.perform(get("/api/agences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAgence() throws Exception {
        // Get the agence
        restAgenceMockMvc.perform(get("/api/agences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgence() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        int databaseSizeBeforeUpdate = agenceRepository.findAll().size();

        // Update the agence
        Agence updatedAgence = agenceRepository.findById(agence.getId()).get();
        // Disconnect from session so that the updates on updatedAgence are not directly saved in db
        em.detach(updatedAgence);
        updatedAgence
            .codeAgence(UPDATED_CODE_AGENCE)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .adresseAgence(UPDATED_ADRESSE_AGENCE)
            .telSiege(UPDATED_TEL_SIEGE);
        AgenceDTO agenceDTO = agenceMapper.toDto(updatedAgence);

        restAgenceMockMvc.perform(put("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenceDTO)))
            .andExpect(status().isOk());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
        Agence testAgence = agenceList.get(agenceList.size() - 1);
        assertThat(testAgence.getCodeAgence()).isEqualTo(UPDATED_CODE_AGENCE);
        assertThat(testAgence.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAgence.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testAgence.getAdresseAgence()).isEqualTo(UPDATED_ADRESSE_AGENCE);
        assertThat(testAgence.getTelSiege()).isEqualTo(UPDATED_TEL_SIEGE);
    }

    @Test
    @Transactional
    public void updateNonExistingAgence() throws Exception {
        int databaseSizeBeforeUpdate = agenceRepository.findAll().size();

        // Create the Agence
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgenceMockMvc.perform(put("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgence() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        int databaseSizeBeforeDelete = agenceRepository.findAll().size();

        // Delete the agence
        restAgenceMockMvc.perform(delete("/api/agences/{id}", agence.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agence.class);
        Agence agence1 = new Agence();
        agence1.setId(1L);
        Agence agence2 = new Agence();
        agence2.setId(agence1.getId());
        assertThat(agence1).isEqualTo(agence2);
        agence2.setId(2L);
        assertThat(agence1).isNotEqualTo(agence2);
        agence1.setId(null);
        assertThat(agence1).isNotEqualTo(agence2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgenceDTO.class);
        AgenceDTO agenceDTO1 = new AgenceDTO();
        agenceDTO1.setId(1L);
        AgenceDTO agenceDTO2 = new AgenceDTO();
        assertThat(agenceDTO1).isNotEqualTo(agenceDTO2);
        agenceDTO2.setId(agenceDTO1.getId());
        assertThat(agenceDTO1).isEqualTo(agenceDTO2);
        agenceDTO2.setId(2L);
        assertThat(agenceDTO1).isNotEqualTo(agenceDTO2);
        agenceDTO1.setId(null);
        assertThat(agenceDTO1).isNotEqualTo(agenceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(agenceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(agenceMapper.fromId(null)).isNull();
    }
}
