package com.boulhare.backend.service.dto;

import com.boulhare.backend.domain.enumeration.Status;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Numero entity.
 */
public class NumeroDTO implements Serializable {

    private Long id;

    @NotNull
    private String numero;

    private Status statuts;


    private Long agenceId;

    private Long utilisateurId;

    private String utilisateurFullname;

    private Long clientInstantaneId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Status getStatuts() {
        return statuts;
    }

    public void setStatuts(Status statuts) {
        this.statuts = statuts;
    }

    public Long getAgenceId() {
        return agenceId;
    }

    public void setAgenceId(Long agenceId) {
        this.agenceId = agenceId;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getUtilisateurFullname() {
        return utilisateurFullname;
    }

    public void setUtilisateurFullname(String utilisateurFullname) {
        this.utilisateurFullname = utilisateurFullname;
    }

    public Long getClientInstantaneId() {
        return clientInstantaneId;
    }

    public void setClientInstantaneId(Long clientInstantaneId) {
        this.clientInstantaneId = clientInstantaneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NumeroDTO numeroDTO = (NumeroDTO) o;
        if (numeroDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), numeroDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NumeroDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", statuts='" + getStatuts() + "'" +
            ", agence=" + getAgenceId() +
            ", utilisateur=" + getUtilisateurId() +
            ", utilisateur='" + getUtilisateurFullname() + "'" +
            ", clientInstantane=" + getClientInstantaneId() +
            "}";
    }
}
