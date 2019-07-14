package com.boulhare.backend.service;

import com.boulhare.backend.domain.Agence_;
import com.boulhare.backend.domain.ClientInstantane;
import com.boulhare.backend.domain.ClientInstantane_;
import com.boulhare.backend.repository.ClientInstantaneRepository;
import com.boulhare.backend.service.dto.ClientInstantaneCriteria;
import com.boulhare.backend.service.dto.ClientInstantaneDTO;
import com.boulhare.backend.service.mapper.ClientInstantaneMapper;
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
 * Service for executing complex queries for ClientInstantane entities in the database.
 * The main input is a {@link ClientInstantaneCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClientInstantaneDTO} or a {@link Page} of {@link ClientInstantaneDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClientInstantaneQueryService extends QueryService<ClientInstantane> {

    private final Logger log = LoggerFactory.getLogger(ClientInstantaneQueryService.class);

    private final ClientInstantaneRepository clientInstantaneRepository;

    private final ClientInstantaneMapper clientInstantaneMapper;

    public ClientInstantaneQueryService(ClientInstantaneRepository clientInstantaneRepository, ClientInstantaneMapper clientInstantaneMapper) {
        this.clientInstantaneRepository = clientInstantaneRepository;
        this.clientInstantaneMapper = clientInstantaneMapper;
    }

    /**
     * Return a {@link List} of {@link ClientInstantaneDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClientInstantaneDTO> findByCriteria(ClientInstantaneCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClientInstantane> specification = createSpecification(criteria);
        return clientInstantaneMapper.toDto(clientInstantaneRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClientInstantaneDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientInstantaneDTO> findByCriteria(ClientInstantaneCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClientInstantane> specification = createSpecification(criteria);
        return clientInstantaneRepository.findAll(specification, page)
            .map(clientInstantaneMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClientInstantaneCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClientInstantane> specification = createSpecification(criteria);
        return clientInstantaneRepository.count(specification);
    }

    /**
     * Function to convert ClientInstantaneCriteria to a {@link Specification}
     */
    private Specification<ClientInstantane> createSpecification(ClientInstantaneCriteria criteria) {
        Specification<ClientInstantane> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ClientInstantane_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), ClientInstantane_.nom));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), ClientInstantane_.phone));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), ClientInstantane_.date));
            }
            if (criteria.getAgenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getAgenceId(),
                    root -> root.join(ClientInstantane_.agence, JoinType.LEFT).get(Agence_.id)));
            }
        }
        return specification;
    }
}
