package com.xedrux.cclouds.web.entities;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Admin
 */

public class CcloudsCity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idCity;
    @NotNull
    private int codeCity;
    @NotNull
    @Size(min = 1, max = 50)
    private String nameCity;
    @Size(max = 50)
    private String descriptionCity;
    private long idProvince;

    public CcloudsCity() {
    }

    public CcloudsCity(Long idCity) {
        this.idCity = idCity;
    }

    public CcloudsCity(Long idCity, long idProvince, int codeCity,
            String nameCity, String descriptionCity) {
        this.idCity = idCity;
        this.idProvince = idProvince;
        this.codeCity = codeCity;
        this.nameCity = nameCity;
        this.descriptionCity = descriptionCity;
    }

    public void setIdCity(Long idCity) {
        this.idCity = idCity;
    }

    public void setIdProvince(long idProvince) {
        this.idProvince = idProvince;
    }

    public Long getIdCity() {
        return idCity;
    }

    public long getIdProvince() {
        return idProvince;
    }


    public int getCodeCity() {
        return codeCity;
    }

    public void setCodeCity(int codeCity) {
        this.codeCity = codeCity;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getDescriptionCity() {
        return descriptionCity;
    }

    public void setDescriptionCity(String descriptionCity) {
        this.descriptionCity = descriptionCity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCity != null ? idCity.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CcloudsCity)) {
            return false;
        }
        CcloudsCity other = (CcloudsCity) object;
        if ((this.idCity == null && other.idCity != null) || (this.idCity != null && !this.idCity.equals(other.idCity))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.xedrux.cclouds.web.entities.CcloudsCity[ idCity=" + idCity + " ]";
    }
    
}
