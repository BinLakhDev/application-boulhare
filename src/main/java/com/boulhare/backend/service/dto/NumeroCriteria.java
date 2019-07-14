package com.boulhare.backend.service.dto;

import com.boulhare.backend.domain.enumeration.Status;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the Numero entity. This class is used in NumeroResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /numeros?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NumeroCriteria implements Serializable {
    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter numero;

    private StatusFilter statuts;

    private LongFilter agenceId;

    private LongFilter utilisateurId;

    private LongFilter clientInstantaneId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNumero() {
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public StatusFilter getStatuts() {
        return statuts;
    }

    public void setStatuts(StatusFilter statuts) {
        this.statuts = statuts;
    }

    public LongFilter getAgenceId() {
        return agenceId;
    }

    public void setAgenceId(LongFilter agenceId) {
        this.agenceId = agenceId;
    }

    public LongFilter getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(LongFilter utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public LongFilter getClientInstantaneId() {
        return clientInstantaneId;
    }

    public void setClientInstantaneId(LongFilter clientInstantaneId) {
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
        final NumeroCriteria that = (NumeroCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(statuts, that.statuts) &&
            Objects.equals(agenceId, that.agenceId) &&
            Objects.equals(utilisateurId, that.utilisateurId) &&
            Objects.equals(clientInstantaneId, that.clientInstantaneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        numero,
        statuts,
        agenceId,
        utilisateurId,
        clientInstantaneId
        );
    }

    @Override
    public String toString() {
        return "NumeroCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (statuts != null ? "statuts=" + statuts + ", " : "") +
                (agenceId != null ? "agenceId=" + agenceId + ", " : "") +
                (utilisateurId != null ? "utilisateurId=" + utilisateurId + ", " : "") +
                (clientInstantaneId != null ? "clientInstantaneId=" + clientInstantaneId + ", " : "") +
            "}";
    }

}
