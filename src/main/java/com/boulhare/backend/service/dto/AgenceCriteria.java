package com.boulhare.backend.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the Agence entity. This class is used in AgenceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /agences?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AgenceCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codeAgence;

    private StringFilter longitude;

    private StringFilter latitude;

    private StringFilter adresseAgence;

    private StringFilter telSiege;

    private LongFilter banqueId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCodeAgence() {
        return codeAgence;
    }

    public void setCodeAgence(StringFilter codeAgence) {
        this.codeAgence = codeAgence;
    }

    public StringFilter getLongitude() {
        return longitude;
    }

    public void setLongitude(StringFilter longitude) {
        this.longitude = longitude;
    }

    public StringFilter getLatitude() {
        return latitude;
    }

    public void setLatitude(StringFilter latitude) {
        this.latitude = latitude;
    }

    public StringFilter getAdresseAgence() {
        return adresseAgence;
    }

    public void setAdresseAgence(StringFilter adresseAgence) {
        this.adresseAgence = adresseAgence;
    }

    public StringFilter getTelSiege() {
        return telSiege;
    }

    public void setTelSiege(StringFilter telSiege) {
        this.telSiege = telSiege;
    }

    public LongFilter getBanqueId() {
        return banqueId;
    }

    public void setBanqueId(LongFilter banqueId) {
        this.banqueId = banqueId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AgenceCriteria that = (AgenceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(codeAgence, that.codeAgence) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(adresseAgence, that.adresseAgence) &&
            Objects.equals(telSiege, that.telSiege) &&
            Objects.equals(banqueId, that.banqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        codeAgence,
        longitude,
        latitude,
        adresseAgence,
        telSiege,
        banqueId
        );
    }

    @Override
    public String toString() {
        return "AgenceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (codeAgence != null ? "codeAgence=" + codeAgence + ", " : "") +
                (longitude != null ? "longitude=" + longitude + ", " : "") +
                (latitude != null ? "latitude=" + latitude + ", " : "") +
                (adresseAgence != null ? "adresseAgence=" + adresseAgence + ", " : "") +
                (telSiege != null ? "telSiege=" + telSiege + ", " : "") +
                (banqueId != null ? "banqueId=" + banqueId + ", " : "") +
            "}";
    }

}
