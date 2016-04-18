package com.xedrux.cclouds.web.entities;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Admin
 */
public class CcloudsProvince implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idProvince;
    @NotNull
    private int codeProvince;
    @NotNull
    @Size(min = 1, max = 50)
    private String nameProvince;
    @NotNull
    @Size(min = 1, max = 50)
    private String descriptionProvince;
    private long idCountry;

    public CcloudsProvince() {
    }

    public CcloudsProvince(Long idProvince) {
        this.idProvince = idProvince;
    }

    public CcloudsProvince(Long idProvince, long idCountry, int codeProvince, String nameProvince, String descriptionProvince) {
        this.idProvince = idProvince;
        this.idCountry = idCountry;
        this.codeProvince = codeProvince;
        this.nameProvince = nameProvince;
        this.descriptionProvince = descriptionProvince;
    }

   
    public int getCodeProvince() {
        return codeProvince;
    }

    public void setCodeProvince(int codeProvince) {
        this.codeProvince = codeProvince;
    }

    public String getNameProvince() {
        return nameProvince;
    }

    public void setNameProvince(String nameProvince) {
        this.nameProvince = nameProvince;
    }

    public String getDescriptionProvince() {
        return descriptionProvince;
    }

    public void setDescriptionProvince(String descriptionProvince) {
        this.descriptionProvince = descriptionProvince;
    }

    public void setIdProvince(Long idProvince) {
        this.idProvince = idProvince;
    }

    public void setIdCountry(long idCountry) {
        this.idCountry = idCountry;
    }


    public Long getIdProvince() {
        return idProvince;
    }

    public long getIdCountry() {
        return idCountry;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProvince != null ? idProvince.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CcloudsProvince)) {
            return false;
        }
        CcloudsProvince other = (CcloudsProvince) object;
        if ((this.idProvince == null && other.idProvince != null) || (this.idProvince != null && !this.idProvince.equals(other.idProvince))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.xedrux.cclouds.web.entities.CcloudsProvince[ idProvince=" + idProvince + " ]";
    }
    
}
