package com.xedrux.cclouds.web.entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Reinier
 */
public class AdmModulo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    @NotNull
    @Size(min = 1, max = 255)
    private String name;
    @Size(max = 255)
    private String descripcion;
    private List<AdmModulo> admModuloList;
    private Long idPadre;
    
    private  String text;
    private  String icon;
    private  Integer tags;
    private  boolean leaf;
    private  List<AdmModulo>children;

    public AdmModulo() {
    }

    public AdmModulo(Long id) {
        this.id = id;
    }

    public AdmModulo(Long id, String name, String descripcion, Long idPadre, 
            boolean leaf ) {
        this.id = id;
        this.name = name;
        this.descripcion = descripcion;
        this.idPadre = idPadre;
        this.text = "";
        this.icon = "";
        this.tags = 0;
        this.leaf = leaf;   
        this.children = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<AdmModulo> getAdmModuloList() {
        return admModuloList;
    }

    public void setAdmModuloList(List<AdmModulo> admModuloList) {
        this.admModuloList = admModuloList;
    }

    public Long getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getTags() {
        return tags;
    }

    public void setTags(Integer tags) {
        this.tags = tags;
    }

    public List<AdmModulo> getChildren() {
        return children;
    }

    public void setChildren(List<AdmModulo> children) {
        this.children = children;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isLeaf() {
        return leaf;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmModulo)) {
            return false;
        }
        AdmModulo other = (AdmModulo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.xedrux.cclouds.web.entities.AdmModulo[ id=" + id + " ]";
    }
    
}
