package com.boulhare.backend.service.impl;

import com.boulhare.backend.domain.Banque;
import com.boulhare.backend.repository.BanqueRepository;
import com.boulhare.backend.service.BanqueService;
import com.boulhare.backend.service.dto.BanqueDTO;
import com.boulhare.backend.service.mapper.BanqueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Banque.
 */
@Service
@Transactional
public class BanqueServiceImpl implements BanqueService {

    private final Logger log = LoggerFactory.getLogger(BanqueServiceImpl.class);

    private final BanqueRepository banqueRepository;

    private final BanqueMapper banqueMapper;

    public BanqueServiceImpl(BanqueRepository banqueRepository, BanqueMapper banqueMapper) {
        this.banqueRepository = banqueRepository;
        this.banqueMapper = banqueMapper;
    }

    /**
     * Save a banque.
     *
     * @param banqueDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BanqueDTO save(BanqueDTO banqueDTO) {
        log.debug("Request to save Banque : {}", banqueDTO);
        Banque banque = banqueMapper.toEntity(banqueDTO);
        banque = banqueRepository.save(banque);
        return banqueMapper.toDto(banque);
    }

    /**
     * Get all the banques.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BanqueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Banques");
        return banqueRepository.findAll(pageable)
            .map(banqueMapper::toDto);
    }


    /**
     * Get one banque by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BanqueDTO> findOne(Long id) {
        log.debug("Request to get Banque : {}", id);
        return banqueRepository.findById(id)
            .map(banqueMapper::toDto);
    }

    /**
     * Delete the banque by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Banque : {}", id);
        banqueRepository.deleteById(id);
    }
}
