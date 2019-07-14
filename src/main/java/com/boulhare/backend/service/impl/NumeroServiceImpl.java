package com.boulhare.backend.service.impl;

import com.boulhare.backend.domain.Numero;
import com.boulhare.backend.repository.NumeroRepository;
import com.boulhare.backend.service.NumeroService;
import com.boulhare.backend.service.dto.NumeroDTO;
import com.boulhare.backend.service.mapper.NumeroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Numero.
 */
@Service
@Transactional
public class NumeroServiceImpl implements NumeroService {

    private final Logger log = LoggerFactory.getLogger(NumeroServiceImpl.class);

    private final NumeroRepository numeroRepository;

    private final NumeroMapper numeroMapper;

    public NumeroServiceImpl(NumeroRepository numeroRepository, NumeroMapper numeroMapper) {
        this.numeroRepository = numeroRepository;
        this.numeroMapper = numeroMapper;
    }

    /**
     * Save a numero.
     *
     * @param numeroDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NumeroDTO save(NumeroDTO numeroDTO) {
        log.debug("Request to save Numero : {}", numeroDTO);
        Numero numero = numeroMapper.toEntity(numeroDTO);
        numero = numeroRepository.save(numero);
        return numeroMapper.toDto(numero);
    }

    /**
     * Get all the numeros.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NumeroDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Numeros");
        return numeroRepository.findAll(pageable)
            .map(numeroMapper::toDto);
    }


    /**
     * Get one numero by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NumeroDTO> findOne(Long id) {
        log.debug("Request to get Numero : {}", id);
        return numeroRepository.findById(id)
            .map(numeroMapper::toDto);
    }

    /**
     * Delete the numero by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Numero : {}", id);
        numeroRepository.deleteById(id);
    }
}
