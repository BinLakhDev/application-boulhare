package com.boulhare.backend.repository;

import com.boulhare.backend.domain.Numero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Numero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NumeroRepository extends JpaRepository<Numero, Long>, JpaSpecificationExecutor<Numero> {

}
