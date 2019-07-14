package com.boulhare.backend.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Agence.
 */
@Entity
@Table(name = "agence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Agence implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code_agence", nullable = false)
    private String codeAgence;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private String longitude;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private String latitude;

    @NotNull
    @Column(name = "adresse_agence", nullable = false)
    private String adresseAgence;

    @NotNull
    @Column(name = "tel_siege", nullable = false)
    private String telSiege;

    @ManyToOne
    @JsonIgnoreProperties("agences")
    private Banque banque;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public Agence codeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
        return this;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getLongitude() {
        return longitude;
    }

    public Agence longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public Agence latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAdresseAgence() {
        return adresseAgence;
    }

    public Agence adresseAgence(String adresseAgence) {
        this.adresseAgence = adresseAgence;
        return this;
    }

    public void setAdresseAgence(String adresseAgence) {
        this.adresseAgence = adresseAgence;
    }

    public String getTelSiege() {
        return telSiege;
    }

    public Agence telSiege(String telSiege) {
        this.telSiege = telSiege;
        return this;
    }

    public void setTelSiege(String telSiege) {
        this.telSiege = telSiege;
    }

    public Banque getBanque() {
        return banque;
    }

    public Agence banque(Banque banque) {
        this.banque = banque;
        return this;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
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
        Agence agence = (Agence) o;
        if (agence.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agence.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Agence{" +
            "id=" + getId() +
            ", codeAgence='" + getCodeAgence() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", adresseAgence='" + getAdresseAgence() + "'" +
            ", telSiege='" + getTelSiege() + "'" +
            "}";
    }
}
