package com.boulhare.backend.service.dto;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Banque entity.
 */
public class BanqueDTO implements Serializable {

    private Long id;

    @NotNull
    private String nameBanque;

    private String adresseSiege;

    private String telSiege;

    private String codeBanque;

    
    @Lob
    private byte[] logo;

    private String logoContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameBanque() {
        return nameBanque;
    }

    public void setNameBanque(String nameBanque) {
        this.nameBanque = nameBanque;
    }

    public String getAdresseSiege() {
        return adresseSiege;
    }

    public void setAdresseSiege(String adresseSiege) {
        this.adresseSiege = adresseSiege;
    }

    public String getTelSiege() {
        return telSiege;
    }

    public void setTelSiege(String telSiege) {
        this.telSiege = telSiege;
    }

    public String getCodeBanque() {
        return codeBanque;
    }

    public void setCodeBanque(String codeBanque) {
        this.codeBanque = codeBanque;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BanqueDTO banqueDTO = (BanqueDTO) o;
        if (banqueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), banqueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BanqueDTO{" +
            "id=" + getId() +
            ", nameBanque='" + getNameBanque() + "'" +
            ", adresseSiege='" + getAdresseSiege() + "'" +
            ", telSiege='" + getTelSiege() + "'" +
            ", codeBanque='" + getCodeBanque() + "'" +
            ", logo='" + getLogo() + "'" +
            "}";
    }
}
