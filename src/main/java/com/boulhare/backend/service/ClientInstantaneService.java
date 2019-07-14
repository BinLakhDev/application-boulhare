package com.boulhare.backend.service;

import com.boulhare.backend.service.dto.ClientInstantaneDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ClientInstantane.
 */
public interface ClientInstantaneService {

    /**
     * Save a clientInstantane.
     *
     * @param clientInstantaneDTO the entity to save
     * @return the persisted entity
     */
    ClientInstantaneDTO save(ClientInstantaneDTO clientInstantaneDTO);

    /**
     * Get all the clientInstantanes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClientInstantaneDTO> findAll(Pageable pageable);


    /**
     * Get the "id" clientInstantane.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ClientInstantaneDTO> findOne(Long id);

    /**
     * Delete the "id" clientInstantane.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
