package com.xedrux.cclouds.web.entities;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Admin
 */

public class CcloudsCountry implements Serializable {
    
    private Long idCountry;
    @NotNull
    @Size(min = 1, max = 10)
    private String codeCountry;
    @NotNull
    @Size(min = 1, max = 50)
    private String nameCountry;
    @Size(max = 50)
    private String descriptionCountry;

    public CcloudsCountry() {
    }

    public CcloudsCountry(Long idCountry) {
        this.idCountry = idCountry;
    }

    public CcloudsCountry(Long idCountry, String codeCountry, String nameCountry, String descriptionCountry) {
        this.idCountry = idCountry;
        this.codeCountry = codeCountry;
        this.nameCountry = nameCountry;
        this.descriptionCountry = descriptionCountry;
    }

    public void setIdCountry(Long idCountry) {
        this.idCountry = idCountry;
    }

    public Long getIdCountry() {
        return idCountry;
    }


    public String getCodeCountry() {
        return codeCountry;
    }

    public void setCodeCountry(String codeCountry) {
        this.codeCountry = codeCountry;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    public String getDescriptionCountry() {
        return descriptionCountry;
    }

    public void setDescriptionCountry(String descriptionCountry) {
        this.descriptionCountry = descriptionCountry;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCountry != null ? idCountry.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CcloudsCountry)) {
            return false;
        }
        CcloudsCountry other = (CcloudsCountry) object;
        if ((this.idCountry == null && other.idCountry != null) || (this.idCountry != null && !this.idCountry.equals(other.idCountry))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.xedrux.cclouds.web.entities.CcloudsCountry[ idCountry=" + idCountry + " ]";
    }
    
}
