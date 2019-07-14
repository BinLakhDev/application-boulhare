package com.boulhare.backend.service.mapper;

import com.boulhare.backend.domain.ClientInstantane;
import com.boulhare.backend.service.dto.ClientInstantaneDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity ClientInstantane and its DTO ClientInstantaneDTO.
 */
@Mapper(componentModel = "spring", uses = {AgenceMapper.class})
public interface ClientInstantaneMapper extends EntityMapper<ClientInstantaneDTO, ClientInstantane> {

    @Mapping(source = "agence.id", target = "agenceId")
    @Mapping(source = "agence.codeAgence", target = "agenceCodeAgence")
    ClientInstantaneDTO toDto(ClientInstantane clientInstantane);

    @Mapping(source = "agenceId", target = "agence")
    ClientInstantane toEntity(ClientInstantaneDTO clientInstantaneDTO);

    default ClientInstantane fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClientInstantane clientInstantane = new ClientInstantane();
        clientInstantane.setId(id);
        return clientInstantane;
    }
}
