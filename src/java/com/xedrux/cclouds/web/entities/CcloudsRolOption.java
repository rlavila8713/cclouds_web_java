package com.xedrux.cclouds.web.entities;

import javax.validation.constraints.NotNull;

/**
 *
 * @author Isidro Rodríguez Gamez
 */
public class CcloudsRolOption {

    private long id;
    @NotNull
    private long idOption;
    @NotNull
    private long idRol;

    public CcloudsRolOption() {
    }

    public CcloudsRolOption(long id, long idOption, long idRol) {
        this.id = id;
        this.idOption = idOption;
        this.idRol = idRol;
    }

    public CcloudsRolOption(long idOption, long idRol) {
        this.idOption = idOption;
        this.idRol = idRol;
    }

    public long getId() {
        return id;
    }

    public long getIdOption() {
        return idOption;
    }

    public long getIdRol() {
        return idRol;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdOption(long idOption) {
        this.idOption = idOption;
    }

    public void setIdRol(long idRol) {
        this.idRol = idRol;
    }

    
}
