package com.boulhare.backend.web.rest;

import com.boulhare.backend.service.NumeroQueryService;
import com.boulhare.backend.service.NumeroService;
import com.boulhare.backend.service.dto.NumeroCriteria;
import com.boulhare.backend.service.dto.NumeroDTO;
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
 * REST controller for managing Numero.
 */
@RestController
@RequestMapping("/api")
public class NumeroResource {

    private final Logger log = LoggerFactory.getLogger(NumeroResource.class);

    private static final String ENTITY_NAME = "numero";

    private final NumeroService numeroService;

    private final NumeroQueryService numeroQueryService;

    public NumeroResource(NumeroService numeroService, NumeroQueryService numeroQueryService) {
        this.numeroService = numeroService;
        this.numeroQueryService = numeroQueryService;
    }

    /**
     * POST  /numeros : Create a new numero.
     *
     * @param numeroDTO the numeroDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new numeroDTO, or with status 400 (Bad Request) if the numero has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/numeros")
    public ResponseEntity<NumeroDTO> createNumero(@Valid @RequestBody NumeroDTO numeroDTO) throws URISyntaxException {
        log.debug("REST request to save Numero : {}", numeroDTO);
        if (numeroDTO.getId() != null) {
            throw new BadRequestAlertException("A new numero cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NumeroDTO result = numeroService.save(numeroDTO);
        return ResponseEntity.created(new URI("/api/numeros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /numeros : Updates an existing numero.
     *
     * @param numeroDTO the numeroDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated numeroDTO,
     * or with status 400 (Bad Request) if the numeroDTO is not valid,
     * or with status 500 (Internal Server Error) if the numeroDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/numeros")
    public ResponseEntity<NumeroDTO> updateNumero(@Valid @RequestBody NumeroDTO numeroDTO) throws URISyntaxException {
        log.debug("REST request to update Numero : {}", numeroDTO);
        if (numeroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NumeroDTO result = numeroService.save(numeroDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, numeroDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /numeros : get all the numeros.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of numeros in body
     */
    @GetMapping("/numeros")
    public ResponseEntity<List<NumeroDTO>> getAllNumeros(NumeroCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Numeros by criteria: {}", criteria);
        Page<NumeroDTO> page = numeroQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/numeros");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /numeros/count : count all the numeros.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/numeros/count")
    public ResponseEntity<Long> countNumeros(NumeroCriteria criteria) {
        log.debug("REST request to count Numeros by criteria: {}", criteria);
        return ResponseEntity.ok().body(numeroQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /numeros/:id : get the "id" numero.
     *
     * @param id the id of the numeroDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the numeroDTO, or with status 404 (Not Found)
     */
    @GetMapping("/numeros/{id}")
    public ResponseEntity<NumeroDTO> getNumero(@PathVariable Long id) {
        log.debug("REST request to get Numero : {}", id);
        Optional<NumeroDTO> numeroDTO = numeroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(numeroDTO);
    }

    /**
     * DELETE  /numeros/:id : delete the "id" numero.
     *
     * @param id the id of the numeroDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/numeros/{id}")
    public ResponseEntity<Void> deleteNumero(@PathVariable Long id) {
        log.debug("REST request to delete Numero : {}", id);
        numeroService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
