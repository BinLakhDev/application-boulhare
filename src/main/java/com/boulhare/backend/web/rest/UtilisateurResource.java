package com.boulhare.backend.web.rest;

import com.boulhare.backend.service.UtilisateurQueryService;
import com.boulhare.backend.service.UtilisateurService;
import com.boulhare.backend.service.dto.UtilisateurCriteria;
import com.boulhare.backend.service.dto.UtilisateurDTO;
import com.boulhare.backend.web.rest.errors.BadRequestAlertException;
import com.boulhare.backend.web.rest.util.HeaderUtil;
import com.boulhare.backend.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Utilisateur.
 */
@RestController
@RequestMapping("/api")
public class UtilisateurResource {

    private final Logger log = LoggerFactory.getLogger(UtilisateurResource.class);

    private static final String ENTITY_NAME = "utilisateur";

    private final UtilisateurService utilisateurService;

    private final UtilisateurQueryService utilisateurQueryService;

    public UtilisateurResource(UtilisateurService utilisateurService, UtilisateurQueryService utilisateurQueryService) {
        this.utilisateurService = utilisateurService;
        this.utilisateurQueryService = utilisateurQueryService;
    }

    /**
     * POST  /utilisateurs : Create a new utilisateur.
     *
     * @param utilisateurDTO the utilisateurDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new utilisateurDTO, or with status 400 (Bad Request) if the utilisateur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/utilisateurs")
    public ResponseEntity<UtilisateurDTO> createUtilisateur(@Valid @RequestBody UtilisateurDTO utilisateurDTO) throws URISyntaxException {
        log.debug("REST request to save Utilisateur : {}", utilisateurDTO);
        if (utilisateurDTO.getId() != null) {
            throw new BadRequestAlertException("A new utilisateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UtilisateurDTO result = utilisateurService.save(utilisateurDTO);
        return ResponseEntity.created(new URI("/api/utilisateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /utilisateurs : Updates an existing utilisateur.
     *
     * @param utilisateurDTO the utilisateurDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated utilisateurDTO,
     * or with status 400 (Bad Request) if the utilisateurDTO is not valid,
     * or with status 500 (Internal Server Error) if the utilisateurDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/utilisateurs")
    public ResponseEntity<UtilisateurDTO> updateUtilisateur(@Valid @RequestBody UtilisateurDTO utilisateurDTO) throws URISyntaxException {
        log.debug("REST request to update Utilisateur : {}", utilisateurDTO);
        if (utilisateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UtilisateurDTO result = utilisateurService.save(utilisateurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, utilisateurDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /utilisateurs : get all the utilisateurs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of utilisateurs in body
     */
    @GetMapping("/utilisateurs")
    public ResponseEntity<List<UtilisateurDTO>> getAllUtilisateurs(UtilisateurCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Utilisateurs by criteria: {}", criteria);
        Page<UtilisateurDTO> page = utilisateurQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/utilisateurs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /utilisateurs/count : count all the utilisateurs.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/utilisateurs/count")
    public ResponseEntity<Long> countUtilisateurs(UtilisateurCriteria criteria) {
        log.debug("REST request to count Utilisateurs by criteria: {}", criteria);
        return ResponseEntity.ok().body(utilisateurQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /utilisateurs/:id : get the "id" utilisateur.
     *
     * @param id the id of the utilisateurDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the utilisateurDTO, or with status 404 (Not Found)
     */
    @GetMapping("/utilisateurs/{id}")
    public ResponseEntity<UtilisateurDTO> getUtilisateur(@PathVariable Long id) {
        log.debug("REST request to get Utilisateur : {}", id);
        Optional<UtilisateurDTO> utilisateurDTO = utilisateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(utilisateurDTO);
    }

    /**
     * DELETE  /utilisateurs/:id : delete the "id" utilisateur.
     *
     * @param id the id of the utilisateurDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/utilisateurs/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        log.debug("REST request to delete Utilisateur : {}", id);
        utilisateurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
