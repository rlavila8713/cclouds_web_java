package com.xedrux.cclouds.web.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.constraints.Size;

/**
 *
 * @author Admin
 */
public class CcloudsEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idEmpresa;
    @Size(max = 255)
    private String nombreEmpresa;
    @Size(max = 500)
    private String observacionEmpresa;
    private Integer idRepresentante;
    @Size(max = 255)
    private String rupEmpresa;
    private Date fechaConstitucionEmpresa;
    @Size(max = 500)
    private String esloganEmpresa;
    @Size(max = 500)
    private String imagenLogoEmpresa;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");

    public CcloudsEmpresa() {
    }

    public CcloudsEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public CcloudsEmpresa(Integer idEmpresa, String nombreEmpresa, String observacionEmpresa, Integer idRepresentante, String rupEmpresa, Date fechaConstitucionEmpresa, String esloganEmpresa, String imagenLogoEmpresa) {
        this.idEmpresa = idEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.observacionEmpresa = observacionEmpresa;
        this.idRepresentante = idRepresentante;
        this.rupEmpresa = rupEmpresa;
        this.fechaConstitucionEmpresa = fechaConstitucionEmpresa;
        this.esloganEmpresa = esloganEmpresa;
        this.imagenLogoEmpresa = imagenLogoEmpresa;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getObservacionEmpresa() {
        return observacionEmpresa;
    }

    public void setObservacionEmpresa(String observacionEmpresa) {
        this.observacionEmpresa = observacionEmpresa;
    }

    public Integer getIdRepresentante() {
        return idRepresentante;
    }

    public void setIdRepresentante(Integer idRepresentante) {
        this.idRepresentante = idRepresentante;
    }

    public String getRupEmpresa() {
        return rupEmpresa;
    }

    public void setRupEmpresa(String rupEmpresa) {
        this.rupEmpresa = rupEmpresa;
    }

    public String getFechaConstitucionEmpresa() {
        return dateFormatter.format(fechaConstitucionEmpresa);
    }

    public void setFechaConstitucionEmpresa(Date fechaConstitucionEmpresa) {
        this.fechaConstitucionEmpresa = fechaConstitucionEmpresa;
    }

    public String getEsloganEmpresa() {
        return esloganEmpresa;
    }

    public void setEsloganEmpresa(String esloganEmpresa) {
        this.esloganEmpresa = esloganEmpresa;
    }

    public String getImagenLogoEmpresa() {
        return imagenLogoEmpresa;
    }

    public void setImagenLogoEmpresa(String imagenLogoEmpresa) {
        this.imagenLogoEmpresa = imagenLogoEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpresa != null ? idEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CcloudsEmpresa)) {
            return false;
        }
        CcloudsEmpresa other = (CcloudsEmpresa) object;
        if ((this.idEmpresa == null && other.idEmpresa != null) || (this.idEmpresa != null && !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.xedrux.cclouds.web.entities.CcloudsEmpresa[ idEmpresa=" + idEmpresa + " ]";
    }

}
