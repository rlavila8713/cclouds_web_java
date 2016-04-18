package com.xedrux.cclouds.web.dao;

import com.xedrux.cclouds.web.entities.CcloudsRolOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author Isidro Rodríguez Gamez
 */
public class RolOptionDAO {

    RolOptionDAO instance;
    private JdbcTemplate dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new JdbcTemplate(dataSource);
    }

    public RolOptionDAO RolOptionDAO() {
        if (instance == null) {
            instance = new RolOptionDAO();
        }
        return instance;
    }
    public long insertRolOption(CcloudsRolOption roloption) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String template = "INSERT INTO %s (%s,"
                + " %s )"
                + "VALUES (?, ?);";
        String INSERT_SQL = String.format(template, TABLE_NAME, ID_ROL,
                ID_OPTIONS);
        
        dataSource.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{ID});
            ps.setObject(1, roloption.getIdRol());
            ps.setObject(2, roloption.getIdOption());
            return ps;
        }, keyHolder);
        
        return keyHolder.getKey().longValue();
    }
    
    public Collection<CcloudsRolOption> getAllRolOptions(){
        String sql = "select * from " + TABLE_NAME;
        try {
            List<CcloudsRolOption> rol_module = dataSource.query(sql,
                    new RolModuleMapper());
            return rol_module;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }
    
    
    public Collection<Long> getRols4Option(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_OPTIONS + " = ?";
        Object[] args = {id};
        try {
            List<Long> rols = dataSource.query(sql,args, (ResultSet rs, int rowNum) -> rs.getLong(ID_ROL));
            return rols;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    
    public Collection<Long> getOptions4Rol(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_ROL + " = ?";
        Object[] args = {id};
        try {
            List<Long> options = dataSource.query(sql,args, (ResultSet rs, int rowNum) -> rs.getLong(ID_OPTIONS));
            return options;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    
    public boolean update(CcloudsRolOption rolmodule) {
        String sql = "UPDATE %s SET "
                + "%s=?, %s=? WHERE "
                + "%s = ?;";
        String UPDATE_SQL = String.format(sql, TABLE_NAME,ID_ROL,ID_OPTIONS,ID);
        return (dataSource.update(UPDATE_SQL, rolmodule.getIdRol(), 
                rolmodule.getIdOption(), rolmodule.getId()) > 0);
    }
    public Boolean deleteRolOption(long id) {
        String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
        return (dataSource.update(DELETE_SQL, id) > 0);
    }
    public Boolean deleteRolOption(long idRol, long idOption) {
        String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_ROL 
                + "=? AND "+ID_OPTIONS+"=?;";
        return (dataSource.update(DELETE_SQL, idRol,idOption) > 0);
    }
    public Boolean removeOpiotionFromRol(long idOption,long idRol) {
        String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_ROL +
                "=? AND "+ID_OPTIONS+"=?;";
        return (dataSource.update(DELETE_SQL, idRol,idOption) > 0);
    }
    private final class RolModuleMapper implements RowMapper<CcloudsRolOption> {

        public CcloudsRolOption mapRow(ResultSet rs, int rowNum) throws SQLException {
            CcloudsRolOption rm;
            rm = new CcloudsRolOption(
                    rs.getInt(ID),
                    rs.getInt(ID_OPTIONS),
                    rs.getInt(ID_ROL)
            );
            return rm;
        }
    }
    String TABLE_NAME = "cclouds_rol_adm";
    String ID = "id";
    String ID_ROL = "id_rol";
    String ID_OPTIONS = "id_options";
}
