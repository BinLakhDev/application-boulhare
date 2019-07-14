package com.boulhare.backend.service.mapper;

import com.boulhare.backend.domain.Agence;
import com.boulhare.backend.service.dto.AgenceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Agence and its DTO AgenceDTO.
 */
@Mapper(componentModel = "spring", uses = {BanqueMapper.class})
public interface AgenceMapper extends EntityMapper<AgenceDTO, Agence> {

    @Mapping(source = "banque.id", target = "banqueId")
    @Mapping(source = "banque.nameBanque", target = "banqueNameBanque")
    AgenceDTO toDto(Agence agence);

    @Mapping(source = "banqueId", target = "banque")
    Agence toEntity(AgenceDTO agenceDTO);

    default Agence fromId(Long id) {
        if (id == null) {
            return null;
        }
        Agence agence = new Agence();
        agence.setId(id);
        return agence;
    }
}
