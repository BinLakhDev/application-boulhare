package com.boulhare.backend.service.mapper;

import com.boulhare.backend.domain.Numero;
import com.boulhare.backend.service.dto.NumeroDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Numero and its DTO NumeroDTO.
 */
@Mapper(componentModel = "spring", uses = {AgenceMapper.class, UtilisateurMapper.class, ClientInstantaneMapper.class})
public interface NumeroMapper extends EntityMapper<NumeroDTO, Numero> {

    @Mapping(source = "agence.id", target = "agenceId")
    @Mapping(source = "utilisateur.id", target = "utilisateurId")
    @Mapping(source = "utilisateur.fullname", target = "utilisateurFullname")
    @Mapping(source = "clientInstantane.id", target = "clientInstantaneId")
    NumeroDTO toDto(Numero numero);

    @Mapping(source = "agenceId", target = "agence")
    @Mapping(source = "utilisateurId", target = "utilisateur")
    @Mapping(source = "clientInstantaneId", target = "clientInstantane")
    Numero toEntity(NumeroDTO numeroDTO);

    default Numero fromId(Long id) {
        if (id == null) {
            return null;
        }
        Numero numero = new Numero();
        numero.setId(id);
        return numero;
    }
}
