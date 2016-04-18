package com.xedrux.cclouds.web.entities;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Isidro Rodríguez Gamez
 */

public class CcloudsAgencia implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idAgencia;
    @Size(max = 255)
    @NotNull
    private String nombreAgencia;
    @Size(max = 500)
    private String observacionAgencia;
    private Integer idRepresentanteAgencia;
    @NotNull
    private long idSucursal;

    public CcloudsAgencia() {
    }

    public CcloudsAgencia(Long idAgencia, String nombreAgencia, String observacionAgencia, Integer idRepresentanteAgencia, long idSucursal) {
        this.idAgencia = idAgencia;
        this.nombreAgencia = nombreAgencia;
        this.observacionAgencia = observacionAgencia;
        this.idRepresentanteAgencia = idRepresentanteAgencia;
        this.idSucursal = idSucursal;
    }

    public Long getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(Long idAgencia) {
        this.idAgencia = idAgencia;
    }

    
    public String getNombreAgencia() {
        return nombreAgencia;
    }

    public void setNombreAgencia(String nombreAgencia) {
        this.nombreAgencia = nombreAgencia;
    }

    public String getObservacionAgencia() {
        return observacionAgencia;
    }

    public void setObservacionAgencia(String observacionAgencia) {
        this.observacionAgencia = observacionAgencia;
    }

    public Integer getIdRepresentanteAgencia() {
        return idRepresentanteAgencia;
    }

    public void setIdRepresentanteAgencia(Integer idRepresentanteAgencia) {
        this.idRepresentanteAgencia = idRepresentanteAgencia;
    }

    public long getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(long idSucursal) {
        this.idSucursal = idSucursal;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAgencia != null ? idAgencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CcloudsAgencia)) {
            return false;
        }
        CcloudsAgencia other = (CcloudsAgencia) object;
        if ((this.idAgencia == null && other.idAgencia != null) || (this.idAgencia != null && !this.idAgencia.equals(other.idAgencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.xedrux.cclouds.web.entities.CcloudsAgencia[ idAgencia=" + idAgencia + " ]";
    }
    
}
