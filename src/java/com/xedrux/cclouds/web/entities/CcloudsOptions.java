package com.xedrux.cclouds.web.entities;

import java.io.Serializable;
import javax.validation.constraints.Size;

/**
 *
 * @author Isidro Rodríguez Gamez
 */
public class CcloudsOptions implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idOption;
    private String name;
    @Size(max = 50)
    private String descriptionOption;
    private long idMenu;

    public CcloudsOptions() {
    }

    public CcloudsOptions(Long idOption, String name, long idMenu, String descriptionOption) {
        this.idOption = idOption;
        this.name = name;
        this.descriptionOption = descriptionOption;
        this.idMenu = idMenu;
    }

    public Long getIdOption() {
        return idOption;
    }

    public void setIdOption(Long idOption) {
        this.idOption = idOption;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescriptionOption() {
        return descriptionOption;
    }

    public void setDescriptionOption(String descriptionOption) {
        this.descriptionOption = descriptionOption;
    }

    public long getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(long idMenu) {
        this.idMenu = idMenu;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOption != null ? idOption.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CcloudsOptions)) {
            return false;
        }
        CcloudsOptions other = (CcloudsOptions) object;
        if ((this.idOption == null && other.idOption != null) || (this.idOption != null && !this.idOption.equals(other.idOption))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.xedrux.cclouds.web.entities.CcloudsOptions[ idOption=" + idOption + " ]";
    }
    
}
