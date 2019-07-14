package com.boulhare.backend.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Banque.
 */
@Entity
@Table(name = "banque")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Banque implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name_banque", nullable = false)
    private String nameBanque;

    @Column(name = "adresse_siege")
    private String adresseSiege;

    @Column(name = "tel_siege")
    private String telSiege;

    @Column(name = "code_banque")
    private String codeBanque;

    
    @Lob
    @Column(name = "logo", nullable = false)
    private byte[] logo;

    @Column(name = "logo_content_type", nullable = false)
    private String logoContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameBanque() {
        return nameBanque;
    }

    public Banque nameBanque(String nameBanque) {
        this.nameBanque = nameBanque;
        return this;
    }

    public void setNameBanque(String nameBanque) {
        this.nameBanque = nameBanque;
    }

    public String getAdresseSiege() {
        return adresseSiege;
    }

    public Banque adresseSiege(String adresseSiege) {
        this.adresseSiege = adresseSiege;
        return this;
    }

    public void setAdresseSiege(String adresseSiege) {
        this.adresseSiege = adresseSiege;
    }

    public String getTelSiege() {
        return telSiege;
    }

    public Banque telSiege(String telSiege) {
        this.telSiege = telSiege;
        return this;
    }

    public void setTelSiege(String telSiege) {
        this.telSiege = telSiege;
    }

    public String getCodeBanque() {
        return codeBanque;
    }

    public Banque codeBanque(String codeBanque) {
        this.codeBanque = codeBanque;
        return this;
    }

    public void setCodeBanque(String codeBanque) {
        this.codeBanque = codeBanque;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Banque logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Banque logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Banque banque = (Banque) o;
        if (banque.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), banque.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Banque{" +
            "id=" + getId() +
            ", nameBanque='" + getNameBanque() + "'" +
            ", adresseSiege='" + getAdresseSiege() + "'" +
            ", telSiege='" + getTelSiege() + "'" +
            ", codeBanque='" + getCodeBanque() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            "}";
    }
}
