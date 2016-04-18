package com.xedrux.cclouds.web.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Admin
 */
public class CcloudsSucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull
    private Integer idSucursal;
    @Size(max = 255)
    private String nombreSucursal;
    @Size(max = 500)
    private String observacionSucursal;
    private Integer idRepresentanteSucursal;
    private long idSubEmpresa;

    public CcloudsSucursal() {
    }

    public CcloudsSucursal(Integer idSucursal, String nombreSucursal, String observacionSucursal, Integer idRepresentanteSucursal, long idSubEmpresa) {
        this.idSucursal = idSucursal;
        this.nombreSucursal = nombreSucursal;
        this.observacionSucursal = observacionSucursal;
        this.idRepresentanteSucursal = idRepresentanteSucursal;
        this.idSubEmpresa = idSubEmpresa;
    }

    public CcloudsSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getObservacionSucursal() {
        return observacionSucursal;
    }

    public long getIdSubEmpresa() {
        return idSubEmpresa;
    }
    

    public void setObservacionSucursal(String observacionSucursal) {
        this.observacionSucursal = observacionSucursal;
    }

    public Integer getIdRepresentanteSucursal() {
        return idRepresentanteSucursal;
    }

    public void setIdRepresentanteSucursal(Integer idRepresentanteSucursal) {
        this.idRepresentanteSucursal = idRepresentanteSucursal;
    }

    public void setIdSubEmpresa(long idSubEmpresa) {
        this.idSubEmpresa = idSubEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSucursal != null ? idSucursal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CcloudsSucursal)) {
            return false;
        }
        CcloudsSucursal other = (CcloudsSucursal) object;
        if ((this.idSucursal == null && other.idSucursal != null) || (this.idSucursal != null && !this.idSucursal.equals(other.idSucursal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.xedrux.cclouds.web.entities.CcloudsSucursal[ idSucursal=" + idSucursal + " ]";
    }

}
