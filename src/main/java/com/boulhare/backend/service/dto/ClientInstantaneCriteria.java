package com.boulhare.backend.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the ClientInstantane entity. This class is used in ClientInstantaneResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /client-instantanes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClientInstantaneCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter phone;

    private InstantFilter date;

    private LongFilter agenceId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public InstantFilter getDate() {
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public LongFilter getAgenceId() {
        return agenceId;
    }

    public void setAgenceId(LongFilter agenceId) {
        this.agenceId = agenceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClientInstantaneCriteria that = (ClientInstantaneCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(date, that.date) &&
            Objects.equals(agenceId, that.agenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        phone,
        date,
        agenceId
        );
    }

    @Override
    public String toString() {
        return "ClientInstantaneCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (agenceId != null ? "agenceId=" + agenceId + ", " : "") +
            "}";
    }

}
