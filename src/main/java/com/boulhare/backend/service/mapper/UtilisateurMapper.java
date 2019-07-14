package com.boulhare.backend.service.mapper;

import com.boulhare.backend.domain.Utilisateur;
import com.boulhare.backend.service.dto.UtilisateurDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Utilisateur and its DTO UtilisateurDTO.
 */
@Mapper(componentModel = "spring", uses = {AgenceMapper.class, UserMapper.class})
public interface UtilisateurMapper extends EntityMapper<UtilisateurDTO, Utilisateur> {

    @Mapping(source = "agence.id", target = "agenceId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    UtilisateurDTO toDto(Utilisateur utilisateur);

    @Mapping(source = "agenceId", target = "agence")
    @Mapping(source = "userId", target = "user")
    Utilisateur toEntity(UtilisateurDTO utilisateurDTO);

    default Utilisateur fromId(Long id) {
        if (id == null) {
            return null;
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(id);
        return utilisateur;
    }
}
