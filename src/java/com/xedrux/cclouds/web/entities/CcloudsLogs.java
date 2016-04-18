package com.xedrux.cclouds.web.entities;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Isidro Rodríguez Gamez
 */
public class CcloudsLogs implements Serializable {
    private Long idLog;
    @NotNull
    private Date lastDate;
    @Size(max = 255)
    private String accion;
    @Size(max = 255)
    private String tableName;
    private long idUser;
    public CcloudsLogs() {
    }

    public CcloudsLogs(Long idLog,long idUser, Date lastDate, String accion, String tableName) {
        this.idLog = idLog;
        this.idUser = idUser;
        this.lastDate = lastDate;
        this.accion = accion;
        this.tableName = tableName;
    }

    public Long getIdLog() {
        return idLog;
    }

    public void setIdLog(Long idLog) {
        this.idLog = idLog;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }


    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLog != null ? idLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CcloudsLogs)) {
            return false;
        }
        CcloudsLogs other = (CcloudsLogs) object;
        if ((this.idLog == null && other.idLog != null) || (this.idLog != null && !this.idLog.equals(other.idLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.xedrux.cclouds.web.entities.CcloudsLogs[ idLog=" + idLog + " ]";
    }
    
}
