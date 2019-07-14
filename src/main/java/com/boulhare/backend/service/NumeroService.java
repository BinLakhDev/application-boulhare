package com.boulhare.backend.service;

import com.boulhare.backend.service.dto.NumeroDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Numero.
 */
public interface NumeroService {

    /**
     * Save a numero.
     *
     * @param numeroDTO the entity to save
     * @return the persisted entity
     */
    NumeroDTO save(NumeroDTO numeroDTO);

    /**
     * Get all the numeros.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<NumeroDTO> findAll(Pageable pageable);


    /**
     * Get the "id" numero.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<NumeroDTO> findOne(Long id);

    /**
     * Delete the "id" numero.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
