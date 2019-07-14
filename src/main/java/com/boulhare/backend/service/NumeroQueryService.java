package com.boulhare.backend.service;

import com.boulhare.backend.domain.*;
import com.boulhare.backend.repository.NumeroRepository;
import com.boulhare.backend.service.dto.NumeroCriteria;
import com.boulhare.backend.service.dto.NumeroDTO;
import com.boulhare.backend.service.mapper.NumeroMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for Numero entities in the database.
 * The main input is a {@link NumeroCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NumeroDTO} or a {@link Page} of {@link NumeroDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NumeroQueryService extends QueryService<Numero> {

    private final Logger log = LoggerFactory.getLogger(NumeroQueryService.class);

    private final NumeroRepository numeroRepository;

    private final NumeroMapper numeroMapper;

    public NumeroQueryService(NumeroRepository numeroRepository, NumeroMapper numeroMapper) {
        this.numeroRepository = numeroRepository;
        this.numeroMapper = numeroMapper;
    }

    /**
     * Return a {@link List} of {@link NumeroDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NumeroDTO> findByCriteria(NumeroCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Numero> specification = createSpecification(criteria);
        return numeroMapper.toDto(numeroRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NumeroDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NumeroDTO> findByCriteria(NumeroCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Numero> specification = createSpecification(criteria);
        return numeroRepository.findAll(specification, page)
            .map(numeroMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NumeroCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Numero> specification = createSpecification(criteria);
        return numeroRepository.count(specification);
    }

    /**
     * Function to convert NumeroCriteria to a {@link Specification}
     */
    private Specification<Numero> createSpecification(NumeroCriteria criteria) {
        Specification<Numero> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Numero_.id));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), Numero_.numero));
            }
            if (criteria.getStatuts() != null) {
                specification = specification.and(buildSpecification(criteria.getStatuts(), Numero_.statuts));
            }
            if (criteria.getAgenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getAgenceId(),
                    root -> root.join(Numero_.agence, JoinType.LEFT).get(Agence_.id)));
            }
            if (criteria.getUtilisateurId() != null) {
                specification = specification.and(buildSpecification(criteria.getUtilisateurId(),
                    root -> root.join(Numero_.utilisateur, JoinType.LEFT).get(Utilisateur_.id)));
            }
            if (criteria.getClientInstantaneId() != null) {
                specification = specification.and(buildSpecification(criteria.getClientInstantaneId(),
                    root -> root.join(Numero_.clientInstantane, JoinType.LEFT).get(ClientInstantane_.id)));
            }
        }
        return specification;
    }
}
