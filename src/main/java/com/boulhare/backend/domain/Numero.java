package com.boulhare.backend.domain;


import com.boulhare.backend.domain.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Numero.
 */
@Entity
@Table(name = "numero")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Numero implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "statuts")
    private Status statuts;

    @ManyToOne
    @JsonIgnoreProperties("numeros")
    private Agence agence;

    @ManyToOne
    @JsonIgnoreProperties("numeros")
    private Utilisateur utilisateur;

    @ManyToOne
    @JsonIgnoreProperties("numeros")
    private ClientInstantane clientInstantane;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Numero numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Status getStatuts() {
        return statuts;
    }

    public Numero statuts(Status statuts) {
        this.statuts = statuts;
        return this;
    }

    public void setStatuts(Status statuts) {
        this.statuts = statuts;
    }

    public Agence getAgence() {
        return agence;
    }

    public Numero agence(Agence agence) {
        this.agence = agence;
        return this;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public Numero utilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        return this;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public ClientInstantane getClientInstantane() {
        return clientInstantane;
    }

    public Numero clientInstantane(ClientInstantane clientInstantane) {
        this.clientInstantane = clientInstantane;
        return this;
    }

    public void setClientInstantane(ClientInstantane clientInstantane) {
        this.clientInstantane = clientInstantane;
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
        Numero numero = (Numero) o;
        if (numero.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), numero.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Numero{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", statuts='" + getStatuts() + "'" +
            "}";
    }
}
