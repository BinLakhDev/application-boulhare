package com.boulhare.backend.service;

import com.boulhare.backend.service.dto.BanqueDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Banque.
 */
public interface BanqueService {

    /**
     * Save a banque.
     *
     * @param banqueDTO the entity to save
     * @return the persisted entity
     */
    BanqueDTO save(BanqueDTO banqueDTO);

    /**
     * Get all the banques.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BanqueDTO> findAll(Pageable pageable);


    /**
     * Get the "id" banque.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BanqueDTO> findOne(Long id);

    /**
     * Delete the "id" banque.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
