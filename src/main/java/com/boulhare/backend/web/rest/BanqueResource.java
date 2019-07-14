package com.boulhare.backend.web.rest;

import com.boulhare.backend.service.BanqueQueryService;
import com.boulhare.backend.service.BanqueService;
import com.boulhare.backend.service.dto.BanqueCriteria;
import com.boulhare.backend.service.dto.BanqueDTO;
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
 * REST controller for managing Banque.
 */
@RestController
@RequestMapping("/api")
public class BanqueResource {

    private final Logger log = LoggerFactory.getLogger(BanqueResource.class);

    private static final String ENTITY_NAME = "banque";

    private final BanqueService banqueService;

    private final BanqueQueryService banqueQueryService;

    public BanqueResource(BanqueService banqueService, BanqueQueryService banqueQueryService) {
        this.banqueService = banqueService;
        this.banqueQueryService = banqueQueryService;
    }

    /**
     * POST  /banques : Create a new banque.
     *
     * @param banqueDTO the banqueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new banqueDTO, or with status 400 (Bad Request) if the banque has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/banques")
    public ResponseEntity<BanqueDTO> createBanque(@Valid @RequestBody BanqueDTO banqueDTO) throws URISyntaxException {
        log.debug("REST request to save Banque : {}", banqueDTO);
        if (banqueDTO.getId() != null) {
            throw new BadRequestAlertException("A new banque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BanqueDTO result = banqueService.save(banqueDTO);
        return ResponseEntity.created(new URI("/api/banques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /banques : Updates an existing banque.
     *
     * @param banqueDTO the banqueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated banqueDTO,
     * or with status 400 (Bad Request) if the banqueDTO is not valid,
     * or with status 500 (Internal Server Error) if the banqueDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/banques")
    public ResponseEntity<BanqueDTO> updateBanque(@Valid @RequestBody BanqueDTO banqueDTO) throws URISyntaxException {
        log.debug("REST request to update Banque : {}", banqueDTO);
        if (banqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BanqueDTO result = banqueService.save(banqueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, banqueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /banques : get all the banques.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of banques in body
     */
    @GetMapping("/banques")
    public ResponseEntity<List<BanqueDTO>> getAllBanques(BanqueCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Banques by criteria: {}", criteria);
        Page<BanqueDTO> page = banqueQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/banques");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /banques/count : count all the banques.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/banques/count")
    public ResponseEntity<Long> countBanques(BanqueCriteria criteria) {
        log.debug("REST request to count Banques by criteria: {}", criteria);
        return ResponseEntity.ok().body(banqueQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /banques/:id : get the "id" banque.
     *
     * @param id the id of the banqueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the banqueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/banques/{id}")
    public ResponseEntity<BanqueDTO> getBanque(@PathVariable Long id) {
        log.debug("REST request to get Banque : {}", id);
        Optional<BanqueDTO> banqueDTO = banqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(banqueDTO);
    }

    /**
     * DELETE  /banques/:id : delete the "id" banque.
     *
     * @param id the id of the banqueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/banques/{id}")
    public ResponseEntity<Void> deleteBanque(@PathVariable Long id) {
        log.debug("REST request to delete Banque : {}", id);
        banqueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
