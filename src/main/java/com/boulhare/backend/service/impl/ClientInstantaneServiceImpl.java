package com.boulhare.backend.service.impl;

import com.boulhare.backend.domain.ClientInstantane;
import com.boulhare.backend.repository.ClientInstantaneRepository;
import com.boulhare.backend.service.ClientInstantaneService;
import com.boulhare.backend.service.dto.ClientInstantaneDTO;
import com.boulhare.backend.service.mapper.ClientInstantaneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ClientInstantane.
 */
@Service
@Transactional
public class ClientInstantaneServiceImpl implements ClientInstantaneService {

    private final Logger log = LoggerFactory.getLogger(ClientInstantaneServiceImpl.class);

    private final ClientInstantaneRepository clientInstantaneRepository;

    private final ClientInstantaneMapper clientInstantaneMapper;

    public ClientInstantaneServiceImpl(ClientInstantaneRepository clientInstantaneRepository, ClientInstantaneMapper clientInstantaneMapper) {
        this.clientInstantaneRepository = clientInstantaneRepository;
        this.clientInstantaneMapper = clientInstantaneMapper;
    }

    /**
     * Save a clientInstantane.
     *
     * @param clientInstantaneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClientInstantaneDTO save(ClientInstantaneDTO clientInstantaneDTO) {
        log.debug("Request to save ClientInstantane : {}", clientInstantaneDTO);
        ClientInstantane clientInstantane = clientInstantaneMapper.toEntity(clientInstantaneDTO);
        clientInstantane = clientInstantaneRepository.save(clientInstantane);
        return clientInstantaneMapper.toDto(clientInstantane);
    }

    /**
     * Get all the clientInstantanes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientInstantaneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClientInstantanes");
        return clientInstantaneRepository.findAll(pageable)
            .map(clientInstantaneMapper::toDto);
    }


    /**
     * Get one clientInstantane by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClientInstantaneDTO> findOne(Long id) {
        log.debug("Request to get ClientInstantane : {}", id);
        return clientInstantaneRepository.findById(id)
            .map(clientInstantaneMapper::toDto);
    }

    /**
     * Delete the clientInstantane by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientInstantane : {}", id);
        clientInstantaneRepository.deleteById(id);
    }
}
