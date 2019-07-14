package com.boulhare.backend.web.rest;

import com.boulhare.backend.BoulhareApp;
import com.boulhare.backend.domain.Agence;
import com.boulhare.backend.domain.ClientInstantane;
import com.boulhare.backend.domain.Numero;
import com.boulhare.backend.domain.Utilisateur;
import com.boulhare.backend.domain.enumeration.Status;
import com.boulhare.backend.repository.NumeroRepository;
import com.boulhare.backend.service.NumeroQueryService;
import com.boulhare.backend.service.NumeroService;
import com.boulhare.backend.service.dto.NumeroDTO;
import com.boulhare.backend.service.mapper.NumeroMapper;
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
 * Test class for the NumeroResource REST controller.
 *
 * @see NumeroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BoulhareApp.class)
public class NumeroResourceIntTest {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUTS = Status.UTILISER;
    private static final Status UPDATED_STATUTS = Status.NONUTILUSER;

    @Autowired
    private NumeroRepository numeroRepository;

    @Autowired
    private NumeroMapper numeroMapper;

    @Autowired
    private NumeroService numeroService;

    @Autowired
    private NumeroQueryService numeroQueryService;

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

    private MockMvc restNumeroMockMvc;

    private Numero numero;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NumeroResource numeroResource = new NumeroResource(numeroService, numeroQueryService);
        this.restNumeroMockMvc = MockMvcBuilders.standaloneSetup(numeroResource)
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
    public static Numero createEntity(EntityManager em) {
        Numero numero = new Numero()
            .numero(DEFAULT_NUMERO)
            .statuts(DEFAULT_STATUTS);
        return numero;
    }

    @Before
    public void initTest() {
        numero = createEntity(em);
    }

    @Test
    @Transactional
    public void createNumero() throws Exception {
        int databaseSizeBeforeCreate = numeroRepository.findAll().size();

        // Create the Numero
        NumeroDTO numeroDTO = numeroMapper.toDto(numero);
        restNumeroMockMvc.perform(post("/api/numeros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroDTO)))
            .andExpect(status().isCreated());

        // Validate the Numero in the database
        List<Numero> numeroList = numeroRepository.findAll();
        assertThat(numeroList).hasSize(databaseSizeBeforeCreate + 1);
        Numero testNumero = numeroList.get(numeroList.size() - 1);
        assertThat(testNumero.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testNumero.getStatuts()).isEqualTo(DEFAULT_STATUTS);
    }

    @Test
    @Transactional
    public void createNumeroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = numeroRepository.findAll().size();

        // Create the Numero with an existing ID
        numero.setId(1L);
        NumeroDTO numeroDTO = numeroMapper.toDto(numero);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNumeroMockMvc.perform(post("/api/numeros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Numero in the database
        List<Numero> numeroList = numeroRepository.findAll();
        assertThat(numeroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = numeroRepository.findAll().size();
        // set the field null
        numero.setNumero(null);

        // Create the Numero, which fails.
        NumeroDTO numeroDTO = numeroMapper.toDto(numero);

        restNumeroMockMvc.perform(post("/api/numeros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroDTO)))
            .andExpect(status().isBadRequest());

        List<Numero> numeroList = numeroRepository.findAll();
        assertThat(numeroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNumeros() throws Exception {
        // Initialize the database
        numeroRepository.saveAndFlush(numero);

        // Get all the numeroList
        restNumeroMockMvc.perform(get("/api/numeros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(numero.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].statuts").value(hasItem(DEFAULT_STATUTS.toString())));
    }
    
    @Test
    @Transactional
    public void getNumero() throws Exception {
        // Initialize the database
        numeroRepository.saveAndFlush(numero);

        // Get the numero
        restNumeroMockMvc.perform(get("/api/numeros/{id}", numero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(numero.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.statuts").value(DEFAULT_STATUTS.toString()));
    }

    @Test
    @Transactional
    public void getAllNumerosByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        numeroRepository.saveAndFlush(numero);

        // Get all the numeroList where numero equals to DEFAULT_NUMERO
        defaultNumeroShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the numeroList where numero equals to UPDATED_NUMERO
        defaultNumeroShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllNumerosByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        numeroRepository.saveAndFlush(numero);

        // Get all the numeroList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultNumeroShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the numeroList where numero equals to UPDATED_NUMERO
        defaultNumeroShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllNumerosByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        numeroRepository.saveAndFlush(numero);

        // Get all the numeroList where numero is not null
        defaultNumeroShouldBeFound("numero.specified=true");

        // Get all the numeroList where numero is null
        defaultNumeroShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    public void getAllNumerosByStatutsIsEqualToSomething() throws Exception {
        // Initialize the database
        numeroRepository.saveAndFlush(numero);

        // Get all the numeroList where statuts equals to DEFAULT_STATUTS
        defaultNumeroShouldBeFound("statuts.equals=" + DEFAULT_STATUTS);

        // Get all the numeroList where statuts equals to UPDATED_STATUTS
        defaultNumeroShouldNotBeFound("statuts.equals=" + UPDATED_STATUTS);
    }

    @Test
    @Transactional
    public void getAllNumerosByStatutsIsInShouldWork() throws Exception {
        // Initialize the database
        numeroRepository.saveAndFlush(numero);

        // Get all the numeroList where statuts in DEFAULT_STATUTS or UPDATED_STATUTS
        defaultNumeroShouldBeFound("statuts.in=" + DEFAULT_STATUTS + "," + UPDATED_STATUTS);

        // Get all the numeroList where statuts equals to UPDATED_STATUTS
        defaultNumeroShouldNotBeFound("statuts.in=" + UPDATED_STATUTS);
    }

    @Test
    @Transactional
    public void getAllNumerosByStatutsIsNullOrNotNull() throws Exception {
        // Initialize the database
        numeroRepository.saveAndFlush(numero);

        // Get all the numeroList where statuts is not null
        defaultNumeroShouldBeFound("statuts.specified=true");

        // Get all the numeroList where statuts is null
        defaultNumeroShouldNotBeFound("statuts.specified=false");
    }

    @Test
    @Transactional
    public void getAllNumerosByAgenceIsEqualToSomething() throws Exception {
        // Initialize the database
        Agence agence = AgenceResourceIntTest.createEntity(em);
        em.persist(agence);
        em.flush();
        numero.setAgence(agence);
        numeroRepository.saveAndFlush(numero);
        Long agenceId = agence.getId();

        // Get all the numeroList where agence equals to agenceId
        defaultNumeroShouldBeFound("agenceId.equals=" + agenceId);

        // Get all the numeroList where agence equals to agenceId + 1
        defaultNumeroShouldNotBeFound("agenceId.equals=" + (agenceId + 1));
    }


    @Test
    @Transactional
    public void getAllNumerosByUtilisateurIsEqualToSomething() throws Exception {
        // Initialize the database
        Utilisateur utilisateur = UtilisateurResourceIntTest.createEntity(em);
        em.persist(utilisateur);
        em.flush();
        numero.setUtilisateur(utilisateur);
        numeroRepository.saveAndFlush(numero);
        Long utilisateurId = utilisateur.getId();

        // Get all the numeroList where utilisateur equals to utilisateurId
        defaultNumeroShouldBeFound("utilisateurId.equals=" + utilisateurId);

        // Get all the numeroList where utilisateur equals to utilisateurId + 1
        defaultNumeroShouldNotBeFound("utilisateurId.equals=" + (utilisateurId + 1));
    }


    @Test
    @Transactional
    public void getAllNumerosByClientInstantaneIsEqualToSomething() throws Exception {
        // Initialize the database
        ClientInstantane clientInstantane = ClientInstantaneResourceIntTest.createEntity(em);
        em.persist(clientInstantane);
        em.flush();
        numero.setClientInstantane(clientInstantane);
        numeroRepository.saveAndFlush(numero);
        Long clientInstantaneId = clientInstantane.getId();

        // Get all the numeroList where clientInstantane equals to clientInstantaneId
        defaultNumeroShouldBeFound("clientInstantaneId.equals=" + clientInstantaneId);

        // Get all the numeroList where clientInstantane equals to clientInstantaneId + 1
        defaultNumeroShouldNotBeFound("clientInstantaneId.equals=" + (clientInstantaneId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNumeroShouldBeFound(String filter) throws Exception {
        restNumeroMockMvc.perform(get("/api/numeros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(numero.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].statuts").value(hasItem(DEFAULT_STATUTS.toString())));

        // Check, that the count call also returns 1
        restNumeroMockMvc.perform(get("/api/numeros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNumeroShouldNotBeFound(String filter) throws Exception {
        restNumeroMockMvc.perform(get("/api/numeros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNumeroMockMvc.perform(get("/api/numeros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNumero() throws Exception {
        // Get the numero
        restNumeroMockMvc.perform(get("/api/numeros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNumero() throws Exception {
        // Initialize the database
        numeroRepository.saveAndFlush(numero);

        int databaseSizeBeforeUpdate = numeroRepository.findAll().size();

        // Update the numero
        Numero updatedNumero = numeroRepository.findById(numero.getId()).get();
        // Disconnect from session so that the updates on updatedNumero are not directly saved in db
        em.detach(updatedNumero);
        updatedNumero
            .numero(UPDATED_NUMERO)
            .statuts(UPDATED_STATUTS);
        NumeroDTO numeroDTO = numeroMapper.toDto(updatedNumero);

        restNumeroMockMvc.perform(put("/api/numeros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroDTO)))
            .andExpect(status().isOk());

        // Validate the Numero in the database
        List<Numero> numeroList = numeroRepository.findAll();
        assertThat(numeroList).hasSize(databaseSizeBeforeUpdate);
        Numero testNumero = numeroList.get(numeroList.size() - 1);
        assertThat(testNumero.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testNumero.getStatuts()).isEqualTo(UPDATED_STATUTS);
    }

    @Test
    @Transactional
    public void updateNonExistingNumero() throws Exception {
        int databaseSizeBeforeUpdate = numeroRepository.findAll().size();

        // Create the Numero
        NumeroDTO numeroDTO = numeroMapper.toDto(numero);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNumeroMockMvc.perform(put("/api/numeros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Numero in the database
        List<Numero> numeroList = numeroRepository.findAll();
        assertThat(numeroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNumero() throws Exception {
        // Initialize the database
        numeroRepository.saveAndFlush(numero);

        int databaseSizeBeforeDelete = numeroRepository.findAll().size();

        // Delete the numero
        restNumeroMockMvc.perform(delete("/api/numeros/{id}", numero.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Numero> numeroList = numeroRepository.findAll();
        assertThat(numeroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Numero.class);
        Numero numero1 = new Numero();
        numero1.setId(1L);
        Numero numero2 = new Numero();
        numero2.setId(numero1.getId());
        assertThat(numero1).isEqualTo(numero2);
        numero2.setId(2L);
        assertThat(numero1).isNotEqualTo(numero2);
        numero1.setId(null);
        assertThat(numero1).isNotEqualTo(numero2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumeroDTO.class);
        NumeroDTO numeroDTO1 = new NumeroDTO();
        numeroDTO1.setId(1L);
        NumeroDTO numeroDTO2 = new NumeroDTO();
        assertThat(numeroDTO1).isNotEqualTo(numeroDTO2);
        numeroDTO2.setId(numeroDTO1.getId());
        assertThat(numeroDTO1).isEqualTo(numeroDTO2);
        numeroDTO2.setId(2L);
        assertThat(numeroDTO1).isNotEqualTo(numeroDTO2);
        numeroDTO1.setId(null);
        assertThat(numeroDTO1).isNotEqualTo(numeroDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(numeroMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(numeroMapper.fromId(null)).isNull();
    }
}
