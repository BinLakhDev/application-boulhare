package com.boulhare.backend.service.impl;

import com.boulhare.backend.domain.Agence;
import com.boulhare.backend.repository.AgenceRepository;
import com.boulhare.backend.service.AgenceService;
import com.boulhare.backend.service.dto.AgenceDTO;
import com.boulhare.backend.service.mapper.AgenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Agence.
 */
@Service
@Transactional
public class AgenceServiceImpl implements AgenceService {

    private final Logger log = LoggerFactory.getLogger(AgenceServiceImpl.class);

    private final AgenceRepository agenceRepository;

    private final AgenceMapper agenceMapper;

    public AgenceServiceImpl(AgenceRepository agenceRepository, AgenceMapper agenceMapper) {
        this.agenceRepository = agenceRepository;
        this.agenceMapper = agenceMapper;
    }

    /**
     * Save a agence.
     *
     * @param agenceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AgenceDTO save(AgenceDTO agenceDTO) {
        log.debug("Request to save Agence : {}", agenceDTO);
        Agence agence = agenceMapper.toEntity(agenceDTO);
        agence = agenceRepository.save(agence);
        return agenceMapper.toDto(agence);
    }

    /**
     * Get all the agences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AgenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Agences");
        return agenceRepository.findAll(pageable)
            .map(agenceMapper::toDto);
    }


    /**
     * Get one agence by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AgenceDTO> findOne(Long id) {
        log.debug("Request to get Agence : {}", id);
        return agenceRepository.findById(id)
            .map(agenceMapper::toDto);
    }

    /**
     * Delete the agence by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Agence : {}", id);
        agenceRepository.deleteById(id);
    }
}
