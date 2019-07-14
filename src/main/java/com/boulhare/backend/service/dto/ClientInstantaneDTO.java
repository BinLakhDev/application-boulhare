package com.boulhare.backend.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the ClientInstantane entity.
 */
public class ClientInstantaneDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String phone;

    private Instant date;


    private Long agenceId;

    private String agenceCodeAgence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getAgenceId() {
        return agenceId;
    }

    public void setAgenceId(Long agenceId) {
        this.agenceId = agenceId;
    }

    public String getAgenceCodeAgence() {
        return agenceCodeAgence;
    }

    public void setAgenceCodeAgence(String agenceCodeAgence) {
        this.agenceCodeAgence = agenceCodeAgence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientInstantaneDTO clientInstantaneDTO = (ClientInstantaneDTO) o;
        if (clientInstantaneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientInstantaneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientInstantaneDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", phone='" + getPhone() + "'" +
            ", date='" + getDate() + "'" +
            ", agence=" + getAgenceId() +
            ", agence='" + getAgenceCodeAgence() + "'" +
            "}";
    }
}
