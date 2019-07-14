package com.boulhare.backend.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Agence entity.
 */
public class AgenceDTO implements Serializable {

    private Long id;

    @NotNull
    private String codeAgence;

    @NotNull
    private String longitude;

    @NotNull
    private String latitude;

    @NotNull
    private String adresseAgence;

    @NotNull
    private String telSiege;


    private Long banqueId;

    private String banqueNameBanque;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAdresseAgence() {
        return adresseAgence;
    }

    public void setAdresseAgence(String adresseAgence) {
        this.adresseAgence = adresseAgence;
    }

    public String getTelSiege() {
        return telSiege;
    }

    public void setTelSiege(String telSiege) {
        this.telSiege = telSiege;
    }

    public Long getBanqueId() {
        return banqueId;
    }

    public void setBanqueId(Long banqueId) {
        this.banqueId = banqueId;
    }

    public String getBanqueNameBanque() {
        return banqueNameBanque;
    }

    public void setBanqueNameBanque(String banqueNameBanque) {
        this.banqueNameBanque = banqueNameBanque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AgenceDTO agenceDTO = (AgenceDTO) o;
        if (agenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AgenceDTO{" +
            "id=" + getId() +
            ", codeAgence='" + getCodeAgence() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", adresseAgence='" + getAdresseAgence() + "'" +
            ", telSiege='" + getTelSiege() + "'" +
            ", banque=" + getBanqueId() +
            ", banque='" + getBanqueNameBanque() + "'" +
            "}";
    }
}
