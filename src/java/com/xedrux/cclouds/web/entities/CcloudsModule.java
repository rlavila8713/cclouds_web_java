package com.xedrux.cclouds.web.entities;

import java.io.Serializable;

/**
 *
 * @author Isidro Rodríguez Gamez
 */

public class CcloudsModule implements Serializable {
    private Integer idModule;
    private String name;
    private String description;

    public CcloudsModule() {
    }

    public CcloudsModule(Integer idModule, String name, String description) {
        this.idModule = idModule;
        this.name = name;
        this.description = description;
    }
    
    public CcloudsModule(Integer idModule) {
        this.idModule = idModule;
    }

    public Integer getIdModule() {
        return idModule;
    }

    public void setIdModule(Integer idModule) {
        this.idModule = idModule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModule != null ? idModule.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CcloudsModule)) {
            return false;
        }
        CcloudsModule other = (CcloudsModule) object;
        if ((this.idModule == null && other.idModule != null) || (this.idModule != null && !this.idModule.equals(other.idModule))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.xedrux.cclouds.web.entities.CcloudsModule[ idModule=" + idModule + " ]";
    }
    
}
