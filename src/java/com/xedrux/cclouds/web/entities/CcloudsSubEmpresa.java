package com.xedrux.cclouds.web.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.constraints.Size;

/**
 *
 * @author Admin
 */
public class CcloudsSubEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idSubEmpresa;
    @Size(max = 255)
    private String nombreSubEmpresa;
    @Size(max = 500)
    private String observacionSubEmpresa;
    private Integer idTipoNegocioSubEmpresa;
    private Integer idRepresentante1SubEmpresa;
    private Integer idRepresentante2SubEmpresa;
    private Date fechaConstitucionSubEmpresa;
    @Size(max = 500)
    private String esloganSubEmpresa;
    @Size(max = 500)
    private String imagenLogoSubEmpresa;
    private long idEmpresa;
    
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");

    public CcloudsSubEmpresa() {
    }

    public CcloudsSubEmpresa(Integer idSubEmpresa) {
        this.idSubEmpresa = idSubEmpresa;
    }

    public CcloudsSubEmpresa(Integer idSubEmpresa, String nombreSubEmpresa, String observacionSubEmpresa, Integer idTipoNegocioSubEmpresa, Integer idRepresentante1SubEmpresa, Integer idRepresentante2SubEmpresa, Date fechaConstitucionSubEmpresa, String esloganSubEmpresa, String imagenLogoSubEmpresa, long idEmpresa) {
        this.idSubEmpresa = idSubEmpresa;
        this.nombreSubEmpresa = nombreSubEmpresa;
        this.observacionSubEmpresa = observacionSubEmpresa;
        this.idTipoNegocioSubEmpresa = idTipoNegocioSubEmpresa;
        this.idRepresentante1SubEmpresa = idRepresentante1SubEmpresa;
        this.idRepresentante2SubEmpresa = idRepresentante2SubEmpresa;
        this.fechaConstitucionSubEmpresa = fechaConstitucionSubEmpresa;
        this.esloganSubEmpresa = esloganSubEmpresa;
        this.imagenLogoSubEmpresa = imagenLogoSubEmpresa;
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdSubEmpresa() {
        return idSubEmpresa;
    }

    public void setIdSubEmpresa(Integer idSubEmpresa) {
        this.idSubEmpresa = idSubEmpresa;
    }

    public String getNombreSubEmpresa() {
        return nombreSubEmpresa;
    }

    public void setNombreSubEmpresa(String nombreSubEmpresa) {
        this.nombreSubEmpresa = nombreSubEmpresa;
    }

    public String getObservacionSubEmpresa() {
        return observacionSubEmpresa;
    }

    public void setObservacionSubEmpresa(String observacionSubEmpresa) {
        this.observacionSubEmpresa = observacionSubEmpresa;
    }

    public Integer getIdTipoNegocioSubEmpresa() {
        return idTipoNegocioSubEmpresa;
    }

    public void setIdTipoNegocioSubEmpresa(Integer idTipoNegocioSubEmpresa) {
        this.idTipoNegocioSubEmpresa = idTipoNegocioSubEmpresa;
    }

    public Integer getIdRepresentante1SubEmpresa() {
        return idRepresentante1SubEmpresa;
    }

    public void setIdRepresentante1SubEmpresa(Integer idRepresentante1SubEmpresa) {
        this.idRepresentante1SubEmpresa = idRepresentante1SubEmpresa;
    }

    public Integer getIdRepresentante2SubEmpresa() {
        return idRepresentante2SubEmpresa;
    }

    public void setIdRepresentante2SubEmpresa(Integer idRepresentante2SubEmpresa) {
        this.idRepresentante2SubEmpresa = idRepresentante2SubEmpresa;
    }

    public Date getFechaConstitucionSubEmpresa() {
        return fechaConstitucionSubEmpresa;
    }

    public void setFechaConstitucionSubEmpresa(Date fechaConstitucionSubEmpresa) {
        this.fechaConstitucionSubEmpresa = fechaConstitucionSubEmpresa;
    }

    public String getEsloganSubEmpresa() {
        return esloganSubEmpresa;
    }

    public void setEsloganSubEmpresa(String esloganSubEmpresa) {
        this.esloganSubEmpresa = esloganSubEmpresa;
    }

    public String getImagenLogoSubEmpresa() {
        return imagenLogoSubEmpresa;
    }

    public void setImagenLogoSubEmpresa(String imagenLogoSubEmpresa) {
        this.imagenLogoSubEmpresa = imagenLogoSubEmpresa;
    }

    public long getIdEmpresa() {
        return idEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubEmpresa != null ? idSubEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CcloudsSubEmpresa)) {
            return false;
        }
        CcloudsSubEmpresa other = (CcloudsSubEmpresa) object;
        if ((this.idSubEmpresa == null && other.idSubEmpresa != null) || (this.idSubEmpresa != null && !this.idSubEmpresa.equals(other.idSubEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.xedrux.cclouds.web.entities.CcloudsSubEmpresa[ idSubEmpresa=" + idSubEmpresa + " ]";
    }

}
