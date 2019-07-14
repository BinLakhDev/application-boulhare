package com.boulhare.backend.service.mapper;

import com.boulhare.backend.domain.Banque;
import com.boulhare.backend.service.dto.BanqueDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Banque and its DTO BanqueDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BanqueMapper extends EntityMapper<BanqueDTO, Banque> {



    default Banque fromId(Long id) {
        if (id == null) {
            return null;
        }
        Banque banque = new Banque();
        banque.setId(id);
        return banque;
    }
}
