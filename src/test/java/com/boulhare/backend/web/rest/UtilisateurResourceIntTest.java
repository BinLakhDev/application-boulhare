package com.boulhare.backend.web.rest;

import com.boulhare.backend.BoulhareApp;
import com.boulhare.backend.domain.Agence;
import com.boulhare.backend.domain.User;
import com.boulhare.backend.domain.Utilisateur;
import com.boulhare.backend.repository.UtilisateurRepository;
import com.boulhare.backend.service.UtilisateurQueryService;
import com.boulhare.backend.service.UtilisateurService;
import com.boulhare.backend.service.dto.UtilisateurDTO;
import com.boulhare.backend.service.mapper.UtilisateurMapper;
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
 * Test class for the UtilisateurResource REST controller.
 *
 * @see UtilisateurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BoulhareApp.class)
public class UtilisateurResourceIntTest {

    private static final String DEFAULT_FULLNAME = "AAAAAAAAAA";
    private static final String UPDATED_FULLNAME = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_IBAN = "AAAAAAAAAA";
    private static final String UPDATED_IBAN = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private UtilisateurMapper utilisateurMapper;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private UtilisateurQueryService utilisateurQueryService;

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

    private MockMvc restUtilisateurMockMvc;

    private Utilisateur utilisateur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UtilisateurResource utilisateurResource = new UtilisateurResource(utilisateurService, utilisateurQueryService);
        this.restUtilisateurMockMvc = MockMvcBuilders.standaloneSetup(utilisateurResource)
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
    public static Utilisateur createEntity(EntityManager em) {
        Utilisateur utilisateur = new Utilisateur()
            .fullname(DEFAULT_FULLNAME)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .iban(DEFAULT_IBAN)
            .numero(DEFAULT_NUMERO)
            .email(DEFAULT_EMAIL);
        return utilisateur;
    }

    @Before
    public void initTest() {
        utilisateur = createEntity(em);
    }

    @Test
    @Transactional
    public void createUtilisateur() throws Exception {
        int databaseSizeBeforeCreate = utilisateurRepository.findAll().size();

        // Create the Utilisateur
        UtilisateurDTO utilisateurDTO = utilisateurMapper.toDto(utilisateur);
        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateurDTO)))
            .andExpect(status().isCreated());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeCreate + 1);
        Utilisateur testUtilisateur = utilisateurList.get(utilisateurList.size() - 1);
        assertThat(testUtilisateur.getFullname()).isEqualTo(DEFAULT_FULLNAME);
        assertThat(testUtilisateur.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUtilisateur.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUtilisateur.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testUtilisateur.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testUtilisateur.getIban()).isEqualTo(DEFAULT_IBAN);
        assertThat(testUtilisateur.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testUtilisateur.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createUtilisateurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = utilisateurRepository.findAll().size();

        // Create the Utilisateur with an existing ID
        utilisateur.setId(1L);
        UtilisateurDTO utilisateurDTO = utilisateurMapper.toDto(utilisateur);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFullnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilisateurRepository.findAll().size();
        // set the field null
        utilisateur.setFullname(null);

        // Create the Utilisateur, which fails.
        UtilisateurDTO utilisateurDTO = utilisateurMapper.toDto(utilisateur);

        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateurDTO)))
            .andExpect(status().isBadRequest());

        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilisateurRepository.findAll().size();
        // set the field null
        utilisateur.setUsername(null);

        // Create the Utilisateur, which fails.
        UtilisateurDTO utilisateurDTO = utilisateurMapper.toDto(utilisateur);

        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateurDTO)))
            .andExpect(status().isBadRequest());

        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilisateurRepository.findAll().size();
        // set the field null
        utilisateur.setPassword(null);

        // Create the Utilisateur, which fails.
        UtilisateurDTO utilisateurDTO = utilisateurMapper.toDto(utilisateur);

        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateurDTO)))
            .andExpect(status().isBadRequest());

        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIbanIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilisateurRepository.findAll().size();
        // set the field null
        utilisateur.setIban(null);

        // Create the Utilisateur, which fails.
        UtilisateurDTO utilisateurDTO = utilisateurMapper.toDto(utilisateur);

        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateurDTO)))
            .andExpect(status().isBadRequest());

        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilisateurRepository.findAll().size();
        // set the field null
        utilisateur.setNumero(null);

        // Create the Utilisateur, which fails.
        UtilisateurDTO utilisateurDTO = utilisateurMapper.toDto(utilisateur);

        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateurDTO)))
            .andExpect(status().isBadRequest());

        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUtilisateurs() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList
        restUtilisateurMockMvc.perform(get("/api/utilisateurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(utilisateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getUtilisateur() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get the utilisateur
        restUtilisateurMockMvc.perform(get("/api/utilisateurs/{id}", utilisateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(utilisateur.getId().intValue()))
            .andExpect(jsonPath("$.fullname").value(DEFAULT_FULLNAME.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.iban").value(DEFAULT_IBAN.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getAllUtilisateursByFullnameIsEqualToSomething() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where fullname equals to DEFAULT_FULLNAME
        defaultUtilisateurShouldBeFound("fullname.equals=" + DEFAULT_FULLNAME);

        // Get all the utilisateurList where fullname equals to UPDATED_FULLNAME
        defaultUtilisateurShouldNotBeFound("fullname.equals=" + UPDATED_FULLNAME);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByFullnameIsInShouldWork() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where fullname in DEFAULT_FULLNAME or UPDATED_FULLNAME
        defaultUtilisateurShouldBeFound("fullname.in=" + DEFAULT_FULLNAME + "," + UPDATED_FULLNAME);

        // Get all the utilisateurList where fullname equals to UPDATED_FULLNAME
        defaultUtilisateurShouldNotBeFound("fullname.in=" + UPDATED_FULLNAME);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByFullnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where fullname is not null
        defaultUtilisateurShouldBeFound("fullname.specified=true");

        // Get all the utilisateurList where fullname is null
        defaultUtilisateurShouldNotBeFound("fullname.specified=false");
    }

    @Test
    @Transactional
    public void getAllUtilisateursByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where username equals to DEFAULT_USERNAME
        defaultUtilisateurShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the utilisateurList where username equals to UPDATED_USERNAME
        defaultUtilisateurShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultUtilisateurShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the utilisateurList where username equals to UPDATED_USERNAME
        defaultUtilisateurShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where username is not null
        defaultUtilisateurShouldBeFound("username.specified=true");

        // Get all the utilisateurList where username is null
        defaultUtilisateurShouldNotBeFound("username.specified=false");
    }

    @Test
    @Transactional
    public void getAllUtilisateursByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where password equals to DEFAULT_PASSWORD
        defaultUtilisateurShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the utilisateurList where password equals to UPDATED_PASSWORD
        defaultUtilisateurShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultUtilisateurShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the utilisateurList where password equals to UPDATED_PASSWORD
        defaultUtilisateurShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where password is not null
        defaultUtilisateurShouldBeFound("password.specified=true");

        // Get all the utilisateurList where password is null
        defaultUtilisateurShouldNotBeFound("password.specified=false");
    }

    @Test
    @Transactional
    public void getAllUtilisateursByIbanIsEqualToSomething() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where iban equals to DEFAULT_IBAN
        defaultUtilisateurShouldBeFound("iban.equals=" + DEFAULT_IBAN);

        // Get all the utilisateurList where iban equals to UPDATED_IBAN
        defaultUtilisateurShouldNotBeFound("iban.equals=" + UPDATED_IBAN);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByIbanIsInShouldWork() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where iban in DEFAULT_IBAN or UPDATED_IBAN
        defaultUtilisateurShouldBeFound("iban.in=" + DEFAULT_IBAN + "," + UPDATED_IBAN);

        // Get all the utilisateurList where iban equals to UPDATED_IBAN
        defaultUtilisateurShouldNotBeFound("iban.in=" + UPDATED_IBAN);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByIbanIsNullOrNotNull() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where iban is not null
        defaultUtilisateurShouldBeFound("iban.specified=true");

        // Get all the utilisateurList where iban is null
        defaultUtilisateurShouldNotBeFound("iban.specified=false");
    }

    @Test
    @Transactional
    public void getAllUtilisateursByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where numero equals to DEFAULT_NUMERO
        defaultUtilisateurShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the utilisateurList where numero equals to UPDATED_NUMERO
        defaultUtilisateurShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultUtilisateurShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the utilisateurList where numero equals to UPDATED_NUMERO
        defaultUtilisateurShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where numero is not null
        defaultUtilisateurShouldBeFound("numero.specified=true");

        // Get all the utilisateurList where numero is null
        defaultUtilisateurShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    public void getAllUtilisateursByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where email equals to DEFAULT_EMAIL
        defaultUtilisateurShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the utilisateurList where email equals to UPDATED_EMAIL
        defaultUtilisateurShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultUtilisateurShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the utilisateurList where email equals to UPDATED_EMAIL
        defaultUtilisateurShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where email is not null
        defaultUtilisateurShouldBeFound("email.specified=true");

        // Get all the utilisateurList where email is null
        defaultUtilisateurShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllUtilisateursByAgenceIsEqualToSomething() throws Exception {
        // Initialize the database
        Agence agence = AgenceResourceIntTest.createEntity(em);
        em.persist(agence);
        em.flush();
        utilisateur.setAgence(agence);
        utilisateurRepository.saveAndFlush(utilisateur);
        Long agenceId = agence.getId();

        // Get all the utilisateurList where agence equals to agenceId
        defaultUtilisateurShouldBeFound("agenceId.equals=" + agenceId);

        // Get all the utilisateurList where agence equals to agenceId + 1
        defaultUtilisateurShouldNotBeFound("agenceId.equals=" + (agenceId + 1));
    }


    @Test
    @Transactional
    public void getAllUtilisateursByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        utilisateur.setUser(user);
        utilisateurRepository.saveAndFlush(utilisateur);
        Long userId = user.getId();

        // Get all the utilisateurList where user equals to userId
        defaultUtilisateurShouldBeFound("userId.equals=" + userId);

        // Get all the utilisateurList where user equals to userId + 1
        defaultUtilisateurShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultUtilisateurShouldBeFound(String filter) throws Exception {
        restUtilisateurMockMvc.perform(get("/api/utilisateurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(utilisateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));

        // Check, that the count call also returns 1
        restUtilisateurMockMvc.perform(get("/api/utilisateurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultUtilisateurShouldNotBeFound(String filter) throws Exception {
        restUtilisateurMockMvc.perform(get("/api/utilisateurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUtilisateurMockMvc.perform(get("/api/utilisateurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUtilisateur() throws Exception {
        // Get the utilisateur
        restUtilisateurMockMvc.perform(get("/api/utilisateurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUtilisateur() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();

        // Update the utilisateur
        Utilisateur updatedUtilisateur = utilisateurRepository.findById(utilisateur.getId()).get();
        // Disconnect from session so that the updates on updatedUtilisateur are not directly saved in db
        em.detach(updatedUtilisateur);
        updatedUtilisateur
            .fullname(UPDATED_FULLNAME)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .iban(UPDATED_IBAN)
            .numero(UPDATED_NUMERO)
            .email(UPDATED_EMAIL);
        UtilisateurDTO utilisateurDTO = utilisateurMapper.toDto(updatedUtilisateur);

        restUtilisateurMockMvc.perform(put("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateurDTO)))
            .andExpect(status().isOk());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);
        Utilisateur testUtilisateur = utilisateurList.get(utilisateurList.size() - 1);
        assertThat(testUtilisateur.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testUtilisateur.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUtilisateur.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUtilisateur.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testUtilisateur.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testUtilisateur.getIban()).isEqualTo(UPDATED_IBAN);
        assertThat(testUtilisateur.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testUtilisateur.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingUtilisateur() throws Exception {
        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();

        // Create the Utilisateur
        UtilisateurDTO utilisateurDTO = utilisateurMapper.toDto(utilisateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUtilisateurMockMvc.perform(put("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUtilisateur() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        int databaseSizeBeforeDelete = utilisateurRepository.findAll().size();

        // Delete the utilisateur
        restUtilisateurMockMvc.perform(delete("/api/utilisateurs/{id}", utilisateur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Utilisateur.class);
        Utilisateur utilisateur1 = new Utilisateur();
        utilisateur1.setId(1L);
        Utilisateur utilisateur2 = new Utilisateur();
        utilisateur2.setId(utilisateur1.getId());
        assertThat(utilisateur1).isEqualTo(utilisateur2);
        utilisateur2.setId(2L);
        assertThat(utilisateur1).isNotEqualTo(utilisateur2);
        utilisateur1.setId(null);
        assertThat(utilisateur1).isNotEqualTo(utilisateur2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UtilisateurDTO.class);
        UtilisateurDTO utilisateurDTO1 = new UtilisateurDTO();
        utilisateurDTO1.setId(1L);
        UtilisateurDTO utilisateurDTO2 = new UtilisateurDTO();
        assertThat(utilisateurDTO1).isNotEqualTo(utilisateurDTO2);
        utilisateurDTO2.setId(utilisateurDTO1.getId());
        assertThat(utilisateurDTO1).isEqualTo(utilisateurDTO2);
        utilisateurDTO2.setId(2L);
        assertThat(utilisateurDTO1).isNotEqualTo(utilisateurDTO2);
        utilisateurDTO1.setId(null);
        assertThat(utilisateurDTO1).isNotEqualTo(utilisateurDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(utilisateurMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(utilisateurMapper.fromId(null)).isNull();
    }
}
