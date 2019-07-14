package com.boulhare.backend.web.rest;

import com.boulhare.backend.BoulhareApp;
import com.boulhare.backend.domain.Agence;
import com.boulhare.backend.domain.ClientInstantane;
import com.boulhare.backend.repository.ClientInstantaneRepository;
import com.boulhare.backend.service.ClientInstantaneQueryService;
import com.boulhare.backend.service.ClientInstantaneService;
import com.boulhare.backend.service.dto.ClientInstantaneDTO;
import com.boulhare.backend.service.mapper.ClientInstantaneMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.boulhare.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClientInstantaneResource REST controller.
 *
 * @see ClientInstantaneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BoulhareApp.class)
public class ClientInstantaneResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ClientInstantaneRepository clientInstantaneRepository;

    @Autowired
    private ClientInstantaneMapper clientInstantaneMapper;

    @Autowired
    private ClientInstantaneService clientInstantaneService;

    @Autowired
    private ClientInstantaneQueryService clientInstantaneQueryService;

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

    private MockMvc restClientInstantaneMockMvc;

    private ClientInstantane clientInstantane;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientInstantaneResource clientInstantaneResource = new ClientInstantaneResource(clientInstantaneService, clientInstantaneQueryService);
        this.restClientInstantaneMockMvc = MockMvcBuilders.standaloneSetup(clientInstantaneResource)
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
    public static ClientInstantane createEntity(EntityManager em) {
        ClientInstantane clientInstantane = new ClientInstantane()
            .nom(DEFAULT_NOM)
            .phone(DEFAULT_PHONE)
            .date(DEFAULT_DATE);
        return clientInstantane;
    }

    @Before
    public void initTest() {
        clientInstantane = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientInstantane() throws Exception {
        int databaseSizeBeforeCreate = clientInstantaneRepository.findAll().size();

        // Create the ClientInstantane
        ClientInstantaneDTO clientInstantaneDTO = clientInstantaneMapper.toDto(clientInstantane);
        restClientInstantaneMockMvc.perform(post("/api/client-instantanes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientInstantaneDTO)))
            .andExpect(status().isCreated());

        // Validate the ClientInstantane in the database
        List<ClientInstantane> clientInstantaneList = clientInstantaneRepository.findAll();
        assertThat(clientInstantaneList).hasSize(databaseSizeBeforeCreate + 1);
        ClientInstantane testClientInstantane = clientInstantaneList.get(clientInstantaneList.size() - 1);
        assertThat(testClientInstantane.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testClientInstantane.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testClientInstantane.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createClientInstantaneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientInstantaneRepository.findAll().size();

        // Create the ClientInstantane with an existing ID
        clientInstantane.setId(1L);
        ClientInstantaneDTO clientInstantaneDTO = clientInstantaneMapper.toDto(clientInstantane);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientInstantaneMockMvc.perform(post("/api/client-instantanes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientInstantaneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientInstantane in the database
        List<ClientInstantane> clientInstantaneList = clientInstantaneRepository.findAll();
        assertThat(clientInstantaneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientInstantaneRepository.findAll().size();
        // set the field null
        clientInstantane.setNom(null);

        // Create the ClientInstantane, which fails.
        ClientInstantaneDTO clientInstantaneDTO = clientInstantaneMapper.toDto(clientInstantane);

        restClientInstantaneMockMvc.perform(post("/api/client-instantanes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientInstantaneDTO)))
            .andExpect(status().isBadRequest());

        List<ClientInstantane> clientInstantaneList = clientInstantaneRepository.findAll();
        assertThat(clientInstantaneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientInstantaneRepository.findAll().size();
        // set the field null
        clientInstantane.setPhone(null);

        // Create the ClientInstantane, which fails.
        ClientInstantaneDTO clientInstantaneDTO = clientInstantaneMapper.toDto(clientInstantane);

        restClientInstantaneMockMvc.perform(post("/api/client-instantanes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientInstantaneDTO)))
            .andExpect(status().isBadRequest());

        List<ClientInstantane> clientInstantaneList = clientInstantaneRepository.findAll();
        assertThat(clientInstantaneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientInstantanes() throws Exception {
        // Initialize the database
        clientInstantaneRepository.saveAndFlush(clientInstantane);

        // Get all the clientInstantaneList
        restClientInstantaneMockMvc.perform(get("/api/client-instantanes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientInstantane.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getClientInstantane() throws Exception {
        // Initialize the database
        clientInstantaneRepository.saveAndFlush(clientInstantane);

        // Get the clientInstantane
        restClientInstantaneMockMvc.perform(get("/api/client-instantanes/{id}", clientInstantane.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientInstantane.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllClientInstantanesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        clientInstantaneRepository.saveAndFlush(clientInstantane);

        // Get all the clientInstantaneList where nom equals to DEFAULT_NOM
        defaultClientInstantaneShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the clientInstantaneList where nom equals to UPDATED_NOM
        defaultClientInstantaneShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllClientInstantanesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        clientInstantaneRepository.saveAndFlush(clientInstantane);

        // Get all the clientInstantaneList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultClientInstantaneShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the clientInstantaneList where nom equals to UPDATED_NOM
        defaultClientInstantaneShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllClientInstantanesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientInstantaneRepository.saveAndFlush(clientInstantane);

        // Get all the clientInstantaneList where nom is not null
        defaultClientInstantaneShouldBeFound("nom.specified=true");

        // Get all the clientInstantaneList where nom is null
        defaultClientInstantaneShouldNotBeFound("nom.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientInstantanesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        clientInstantaneRepository.saveAndFlush(clientInstantane);

        // Get all the clientInstantaneList where phone equals to DEFAULT_PHONE
        defaultClientInstantaneShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the clientInstantaneList where phone equals to UPDATED_PHONE
        defaultClientInstantaneShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllClientInstantanesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        clientInstantaneRepository.saveAndFlush(clientInstantane);

        // Get all the clientInstantaneList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultClientInstantaneShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the clientInstantaneList where phone equals to UPDATED_PHONE
        defaultClientInstantaneShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllClientInstantanesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientInstantaneRepository.saveAndFlush(clientInstantane);

        // Get all the clientInstantaneList where phone is not null
        defaultClientInstantaneShouldBeFound("phone.specified=true");

        // Get all the clientInstantaneList where phone is null
        defaultClientInstantaneShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientInstantanesByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        clientInstantaneRepository.saveAndFlush(clientInstantane);

        // Get all the clientInstantaneList where date equals to DEFAULT_DATE
        defaultClientInstantaneShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the clientInstantaneList where date equals to UPDATED_DATE
        defaultClientInstantaneShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientInstantanesByDateIsInShouldWork() throws Exception {
        // Initialize the database
        clientInstantaneRepository.saveAndFlush(clientInstantane);

        // Get all the clientInstantaneList where date in DEFAULT_DATE or UPDATED_DATE
        defaultClientInstantaneShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the clientInstantaneList where date equals to UPDATED_DATE
        defaultClientInstantaneShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientInstantanesByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientInstantaneRepository.saveAndFlush(clientInstantane);

        // Get all the clientInstantaneList where date is not null
        defaultClientInstantaneShouldBeFound("date.specified=true");

        // Get all the clientInstantaneList where date is null
        defaultClientInstantaneShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientInstantanesByAgenceIsEqualToSomething() throws Exception {
        // Initialize the database
        Agence agence = AgenceResourceIntTest.createEntity(em);
        em.persist(agence);
        em.flush();
        clientInstantane.setAgence(agence);
        clientInstantaneRepository.saveAndFlush(clientInstantane);
        Long agenceId = agence.getId();

        // Get all the clientInstantaneList where agence equals to agenceId
        defaultClientInstantaneShouldBeFound("agenceId.equals=" + agenceId);

        // Get all the clientInstantaneList where agence equals to agenceId + 1
        defaultClientInstantaneShouldNotBeFound("agenceId.equals=" + (agenceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultClientInstantaneShouldBeFound(String filter) throws Exception {
        restClientInstantaneMockMvc.perform(get("/api/client-instantanes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientInstantane.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restClientInstantaneMockMvc.perform(get("/api/client-instantanes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultClientInstantaneShouldNotBeFound(String filter) throws Exception {
        restClientInstantaneMockMvc.perform(get("/api/client-instantanes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClientInstantaneMockMvc.perform(get("/api/client-instantanes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingClientInstantane() throws Exception {
        // Get the clientInstantane
        restClientInstantaneMockMvc.perform(get("/api/client-instantanes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientInstantane() throws Exception {
        // Initialize the database
        clientInstantaneRepository.saveAndFlush(clientInstantane);

        int databaseSizeBeforeUpdate = clientInstantaneRepository.findAll().size();

        // Update the clientInstantane
        ClientInstantane updatedClientInstantane = clientInstantaneRepository.findById(clientInstantane.getId()).get();
        // Disconnect from session so that the updates on updatedClientInstantane are not directly saved in db
        em.detach(updatedClientInstantane);
        updatedClientInstantane
            .nom(UPDATED_NOM)
            .phone(UPDATED_PHONE)
            .date(UPDATED_DATE);
        ClientInstantaneDTO clientInstantaneDTO = clientInstantaneMapper.toDto(updatedClientInstantane);

        restClientInstantaneMockMvc.perform(put("/api/client-instantanes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientInstantaneDTO)))
            .andExpect(status().isOk());

        // Validate the ClientInstantane in the database
        List<ClientInstantane> clientInstantaneList = clientInstantaneRepository.findAll();
        assertThat(clientInstantaneList).hasSize(databaseSizeBeforeUpdate);
        ClientInstantane testClientInstantane = clientInstantaneList.get(clientInstantaneList.size() - 1);
        assertThat(testClientInstantane.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testClientInstantane.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testClientInstantane.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingClientInstantane() throws Exception {
        int databaseSizeBeforeUpdate = clientInstantaneRepository.findAll().size();

        // Create the ClientInstantane
        ClientInstantaneDTO clientInstantaneDTO = clientInstantaneMapper.toDto(clientInstantane);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientInstantaneMockMvc.perform(put("/api/client-instantanes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientInstantaneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientInstantane in the database
        List<ClientInstantane> clientInstantaneList = clientInstantaneRepository.findAll();
        assertThat(clientInstantaneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientInstantane() throws Exception {
        // Initialize the database
        clientInstantaneRepository.saveAndFlush(clientInstantane);

        int databaseSizeBeforeDelete = clientInstantaneRepository.findAll().size();

        // Delete the clientInstantane
        restClientInstantaneMockMvc.perform(delete("/api/client-instantanes/{id}", clientInstantane.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientInstantane> clientInstantaneList = clientInstantaneRepository.findAll();
        assertThat(clientInstantaneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientInstantane.class);
        ClientInstantane clientInstantane1 = new ClientInstantane();
        clientInstantane1.setId(1L);
        ClientInstantane clientInstantane2 = new ClientInstantane();
        clientInstantane2.setId(clientInstantane1.getId());
        assertThat(clientInstantane1).isEqualTo(clientInstantane2);
        clientInstantane2.setId(2L);
        assertThat(clientInstantane1).isNotEqualTo(clientInstantane2);
        clientInstantane1.setId(null);
        assertThat(clientInstantane1).isNotEqualTo(clientInstantane2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientInstantaneDTO.class);
        ClientInstantaneDTO clientInstantaneDTO1 = new ClientInstantaneDTO();
        clientInstantaneDTO1.setId(1L);
        ClientInstantaneDTO clientInstantaneDTO2 = new ClientInstantaneDTO();
        assertThat(clientInstantaneDTO1).isNotEqualTo(clientInstantaneDTO2);
        clientInstantaneDTO2.setId(clientInstantaneDTO1.getId());
        assertThat(clientInstantaneDTO1).isEqualTo(clientInstantaneDTO2);
        clientInstantaneDTO2.setId(2L);
        assertThat(clientInstantaneDTO1).isNotEqualTo(clientInstantaneDTO2);
        clientInstantaneDTO1.setId(null);
        assertThat(clientInstantaneDTO1).isNotEqualTo(clientInstantaneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clientInstantaneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clientInstantaneMapper.fromId(null)).isNull();
    }
}
