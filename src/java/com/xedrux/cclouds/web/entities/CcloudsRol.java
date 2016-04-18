package com.xedrux.cclouds.web.entities;

import javax.validation.constraints.Size;

/**
 *
 * @author Isidro Rodríguez Gamez
 */
public class CcloudsRol {
    private Long idRol;
    @Size(max=255)
    private String name;
    @Size(max=255)
    private String description;

    public CcloudsRol() {
    }

    public CcloudsRol(Long idRol, String name, String description){
        this.idRol = idRol;
        this.name = name;
        this.description = description;

    }
    
    public CcloudsRol(Long idRol) {
        this.idRol = idRol;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "com.xedrux.cclouds.web.entities.CcloudsRol[ idRol=" + idRol + " ]";
    }
    
}
