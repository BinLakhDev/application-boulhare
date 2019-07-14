package com.boulhare.backend.service;

import com.boulhare.backend.service.dto.AgenceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Agence.
 */
public interface AgenceService {

    /**
     * Save a agence.
     *
     * @param agenceDTO the entity to save
     * @return the persisted entity
     */
    AgenceDTO save(AgenceDTO agenceDTO);

    /**
     * Get all the agences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AgenceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" agence.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AgenceDTO> findOne(Long id);

    /**
     * Delete the "id" agence.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
