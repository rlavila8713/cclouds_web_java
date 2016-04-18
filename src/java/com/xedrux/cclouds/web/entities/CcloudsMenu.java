package com.xedrux.cclouds.web.entities;

import java.io.Serializable;
import javax.validation.constraints.Size;

/**
 *
 * @author Admin
 */
public class CcloudsMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer idMenu;
    @Size(max = 50)
    private String descriptionMenu;

    public CcloudsMenu() {
    }

    public CcloudsMenu(Integer idMenu) {
        this.idMenu = idMenu;
    }

    public Integer getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Integer idMenu) {
        this.idMenu = idMenu;
    }

    public String getDescriptionMenu() {
        return descriptionMenu;
    }

    public void setDescriptionMenu(String descriptionMenu) {
        this.descriptionMenu = descriptionMenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMenu != null ? idMenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CcloudsMenu)) {
            return false;
        }
        CcloudsMenu other = (CcloudsMenu) object;
        if ((this.idMenu == null && other.idMenu != null) || (this.idMenu != null && !this.idMenu.equals(other.idMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.xedrux.cclouds.web.entities.CcloudsMenu[ idMenu=" + idMenu + " ]";
    }
    
}
