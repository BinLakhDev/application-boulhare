package com.boulhare.backend.service;

import com.boulhare.backend.domain.Banque;
import com.boulhare.backend.domain.Banque_;
import com.boulhare.backend.repository.BanqueRepository;
import com.boulhare.backend.service.dto.BanqueCriteria;
import com.boulhare.backend.service.dto.BanqueDTO;
import com.boulhare.backend.service.mapper.BanqueMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for Banque entities in the database.
 * The main input is a {@link BanqueCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BanqueDTO} or a {@link Page} of {@link BanqueDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BanqueQueryService extends QueryService<Banque> {

    private final Logger log = LoggerFactory.getLogger(BanqueQueryService.class);

    private final BanqueRepository banqueRepository;

    private final BanqueMapper banqueMapper;

    public BanqueQueryService(BanqueRepository banqueRepository, BanqueMapper banqueMapper) {
        this.banqueRepository = banqueRepository;
        this.banqueMapper = banqueMapper;
    }

    /**
     * Return a {@link List} of {@link BanqueDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BanqueDTO> findByCriteria(BanqueCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Banque> specification = createSpecification(criteria);
        return banqueMapper.toDto(banqueRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BanqueDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BanqueDTO> findByCriteria(BanqueCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Banque> specification = createSpecification(criteria);
        return banqueRepository.findAll(specification, page)
            .map(banqueMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BanqueCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Banque> specification = createSpecification(criteria);
        return banqueRepository.count(specification);
    }

    /**
     * Function to convert BanqueCriteria to a {@link Specification}
     */
    private Specification<Banque> createSpecification(BanqueCriteria criteria) {
        Specification<Banque> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Banque_.id));
            }
            if (criteria.getNameBanque() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameBanque(), Banque_.nameBanque));
            }
            if (criteria.getAdresseSiege() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresseSiege(), Banque_.adresseSiege));
            }
            if (criteria.getTelSiege() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelSiege(), Banque_.telSiege));
            }
            if (criteria.getCodeBanque() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeBanque(), Banque_.codeBanque));
            }
        }
        return specification;
    }
}
