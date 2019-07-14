package com.boulhare.backend.repository;

import com.boulhare.backend.domain.ClientInstantane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClientInstantane entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientInstantaneRepository extends JpaRepository<ClientInstantane, Long>, JpaSpecificationExecutor<ClientInstantane> {

}
