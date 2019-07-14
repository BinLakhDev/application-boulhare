package com.boulhare.backend.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the Utilisateur entity. This class is used in UtilisateurResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /utilisateurs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UtilisateurCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullname;

    private StringFilter username;

    private StringFilter password;

    private StringFilter iban;

    private StringFilter numero;

    private StringFilter email;

    private LongFilter agenceId;

    private LongFilter userId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFullname() {
        return fullname;
    }

    public void setFullname(StringFilter fullname) {
        this.fullname = fullname;
    }

    public StringFilter getUsername() {
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getPassword() {
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public StringFilter getIban() {
        return iban;
    }

    public void setIban(StringFilter iban) {
        this.iban = iban;
    }

    public StringFilter getNumero() {
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public LongFilter getAgenceId() {
        return agenceId;
    }

    public void setAgenceId(LongFilter agenceId) {
        this.agenceId = agenceId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UtilisateurCriteria that = (UtilisateurCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fullname, that.fullname) &&
            Objects.equals(username, that.username) &&
            Objects.equals(password, that.password) &&
            Objects.equals(iban, that.iban) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(email, that.email) &&
            Objects.equals(agenceId, that.agenceId) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fullname,
        username,
        password,
        iban,
        numero,
        email,
        agenceId,
        userId
        );
    }

    @Override
    public String toString() {
        return "UtilisateurCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fullname != null ? "fullname=" + fullname + ", " : "") +
                (username != null ? "username=" + username + ", " : "") +
                (password != null ? "password=" + password + ", " : "") +
                (iban != null ? "iban=" + iban + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (agenceId != null ? "agenceId=" + agenceId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
