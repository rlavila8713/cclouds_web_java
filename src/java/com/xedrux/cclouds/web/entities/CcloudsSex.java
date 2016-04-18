package com.xedrux.cclouds.web.entities;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Isidro
 */
public class CcloudsSex implements Serializable {
    private Long idSex;
    @NotNull
    @Size(min = 1, max = 255)
    private String descriptionSex;
    
    @NotNull
    @Size(min = 1, max = 1)
    private String letter;
    

    public CcloudsSex() {
    }

    public CcloudsSex(Long idSex) {
        this.idSex = idSex;
    }

    public CcloudsSex(Long idSex, String descriptionSex, String letter) {
        this.idSex = idSex;
        this.descriptionSex = descriptionSex;
        this.letter = letter;
    }

    public Long getIdSex() {
        return idSex;
    }

    public void setIdSex(Long idSex) {
        this.idSex = idSex;
    }

    public String getDescriptionSex() {
        return descriptionSex;
    }

    public void setDescriptionSex(String descriptionSex) {
        this.descriptionSex = descriptionSex;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSex != null ? idSex.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CcloudsSex)) {
            return false;
        }
        CcloudsSex other = (CcloudsSex) object;
        if ((this.idSex == null && other.idSex != null) || (this.idSex != null && !this.idSex.equals(other.idSex))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.xedrux.cclouds.web.entities.CcloudsSex[ idSex=" + idSex + " ]";
    }
    
}
