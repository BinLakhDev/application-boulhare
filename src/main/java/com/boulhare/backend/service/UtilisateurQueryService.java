package com.boulhare.backend.service;

import com.boulhare.backend.domain.Agence_;
import com.boulhare.backend.domain.User_;
import com.boulhare.backend.domain.Utilisateur;
import com.boulhare.backend.domain.Utilisateur_;
import com.boulhare.backend.repository.UtilisateurRepository;
import com.boulhare.backend.service.dto.UtilisateurCriteria;
import com.boulhare.backend.service.dto.UtilisateurDTO;
import com.boulhare.backend.service.mapper.UtilisateurMapper;
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
 * Service for executing complex queries for Utilisateur entities in the database.
 * The main input is a {@link UtilisateurCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UtilisateurDTO} or a {@link Page} of {@link UtilisateurDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UtilisateurQueryService extends QueryService<Utilisateur> {

    private final Logger log = LoggerFactory.getLogger(UtilisateurQueryService.class);

    private final UtilisateurRepository utilisateurRepository;

    private final UtilisateurMapper utilisateurMapper;

    public UtilisateurQueryService(UtilisateurRepository utilisateurRepository, UtilisateurMapper utilisateurMapper) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurMapper = utilisateurMapper;
    }

    /**
     * Return a {@link List} of {@link UtilisateurDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UtilisateurDTO> findByCriteria(UtilisateurCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Utilisateur> specification = createSpecification(criteria);
        return utilisateurMapper.toDto(utilisateurRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UtilisateurDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UtilisateurDTO> findByCriteria(UtilisateurCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Utilisateur> specification = createSpecification(criteria);
        return utilisateurRepository.findAll(specification, page)
            .map(utilisateurMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UtilisateurCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Utilisateur> specification = createSpecification(criteria);
        return utilisateurRepository.count(specification);
    }

    /**
     * Function to convert UtilisateurCriteria to a {@link Specification}
     */
    private Specification<Utilisateur> createSpecification(UtilisateurCriteria criteria) {
        Specification<Utilisateur> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Utilisateur_.id));
            }
            if (criteria.getFullname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullname(), Utilisateur_.fullname));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), Utilisateur_.username));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), Utilisateur_.password));
            }
            if (criteria.getIban() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIban(), Utilisateur_.iban));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), Utilisateur_.numero));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Utilisateur_.email));
            }
            if (criteria.getAgenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getAgenceId(),
                    root -> root.join(Utilisateur_.agence, JoinType.LEFT).get(Agence_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Utilisateur_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
