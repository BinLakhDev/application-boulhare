package com.boulhare.backend.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the Banque entity. This class is used in BanqueResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /banques?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BanqueCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nameBanque;

    private StringFilter adresseSiege;

    private StringFilter telSiege;

    private StringFilter codeBanque;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNameBanque() {
        return nameBanque;
    }

    public void setNameBanque(StringFilter nameBanque) {
        this.nameBanque = nameBanque;
    }

    public StringFilter getAdresseSiege() {
        return adresseSiege;
    }

    public void setAdresseSiege(StringFilter adresseSiege) {
        this.adresseSiege = adresseSiege;
    }

    public StringFilter getTelSiege() {
        return telSiege;
    }

    public void setTelSiege(StringFilter telSiege) {
        this.telSiege = telSiege;
    }

    public StringFilter getCodeBanque() {
        return codeBanque;
    }

    public void setCodeBanque(StringFilter codeBanque) {
        this.codeBanque = codeBanque;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BanqueCriteria that = (BanqueCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nameBanque, that.nameBanque) &&
            Objects.equals(adresseSiege, that.adresseSiege) &&
            Objects.equals(telSiege, that.telSiege) &&
            Objects.equals(codeBanque, that.codeBanque);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nameBanque,
        adresseSiege,
        telSiege,
        codeBanque
        );
    }

    @Override
    public String toString() {
        return "BanqueCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nameBanque != null ? "nameBanque=" + nameBanque + ", " : "") +
                (adresseSiege != null ? "adresseSiege=" + adresseSiege + ", " : "") +
                (telSiege != null ? "telSiege=" + telSiege + ", " : "") +
                (codeBanque != null ? "codeBanque=" + codeBanque + ", " : "") +
            "}";
    }

}
