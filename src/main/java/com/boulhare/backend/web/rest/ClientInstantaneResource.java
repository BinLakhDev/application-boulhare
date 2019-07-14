package com.boulhare.backend.web.rest;

import com.boulhare.backend.service.ClientInstantaneQueryService;
import com.boulhare.backend.service.ClientInstantaneService;
import com.boulhare.backend.service.dto.ClientInstantaneCriteria;
import com.boulhare.backend.service.dto.ClientInstantaneDTO;
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
 * REST controller for managing ClientInstantane.
 */
@RestController
@RequestMapping("/api")
public class ClientInstantaneResource {

    private final Logger log = LoggerFactory.getLogger(ClientInstantaneResource.class);

    private static final String ENTITY_NAME = "clientInstantane";

    private final ClientInstantaneService clientInstantaneService;

    private final ClientInstantaneQueryService clientInstantaneQueryService;

    public ClientInstantaneResource(ClientInstantaneService clientInstantaneService, ClientInstantaneQueryService clientInstantaneQueryService) {
        this.clientInstantaneService = clientInstantaneService;
        this.clientInstantaneQueryService = clientInstantaneQueryService;
    }

    /**
     * POST  /client-instantanes : Create a new clientInstantane.
     *
     * @param clientInstantaneDTO the clientInstantaneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientInstantaneDTO, or with status 400 (Bad Request) if the clientInstantane has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-instantanes")
    public ResponseEntity<ClientInstantaneDTO> createClientInstantane(@Valid @RequestBody ClientInstantaneDTO clientInstantaneDTO) throws URISyntaxException {
        log.debug("REST request to save ClientInstantane : {}", clientInstantaneDTO);
        if (clientInstantaneDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientInstantane cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientInstantaneDTO result = clientInstantaneService.save(clientInstantaneDTO);
        return ResponseEntity.created(new URI("/api/client-instantanes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-instantanes : Updates an existing clientInstantane.
     *
     * @param clientInstantaneDTO the clientInstantaneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientInstantaneDTO,
     * or with status 400 (Bad Request) if the clientInstantaneDTO is not valid,
     * or with status 500 (Internal Server Error) if the clientInstantaneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-instantanes")
    public ResponseEntity<ClientInstantaneDTO> updateClientInstantane(@Valid @RequestBody ClientInstantaneDTO clientInstantaneDTO) throws URISyntaxException {
        log.debug("REST request to update ClientInstantane : {}", clientInstantaneDTO);
        if (clientInstantaneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientInstantaneDTO result = clientInstantaneService.save(clientInstantaneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientInstantaneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-instantanes : get all the clientInstantanes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of clientInstantanes in body
     */
    @GetMapping("/client-instantanes")
    public ResponseEntity<List<ClientInstantaneDTO>> getAllClientInstantanes(ClientInstantaneCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ClientInstantanes by criteria: {}", criteria);
        Page<ClientInstantaneDTO> page = clientInstantaneQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/client-instantanes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /client-instantanes/count : count all the clientInstantanes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/client-instantanes/count")
    public ResponseEntity<Long> countClientInstantanes(ClientInstantaneCriteria criteria) {
        log.debug("REST request to count ClientInstantanes by criteria: {}", criteria);
        return ResponseEntity.ok().body(clientInstantaneQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /client-instantanes/:id : get the "id" clientInstantane.
     *
     * @param id the id of the clientInstantaneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientInstantaneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/client-instantanes/{id}")
    public ResponseEntity<ClientInstantaneDTO> getClientInstantane(@PathVariable Long id) {
        log.debug("REST request to get ClientInstantane : {}", id);
        Optional<ClientInstantaneDTO> clientInstantaneDTO = clientInstantaneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientInstantaneDTO);
    }

    /**
     * DELETE  /client-instantanes/:id : delete the "id" clientInstantane.
     *
     * @param id the id of the clientInstantaneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-instantanes/{id}")
    public ResponseEntity<Void> deleteClientInstantane(@PathVariable Long id) {
        log.debug("REST request to delete ClientInstantane : {}", id);
        clientInstantaneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
