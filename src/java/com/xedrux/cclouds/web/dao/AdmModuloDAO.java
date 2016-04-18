package com.xedrux.cclouds.web.dao;

import com.xedrux.cclouds.web.entities.AdmModulo;
import com.xedrux.cclouds.web.entities.CcloudsOptions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Reinier
 */
public class AdmModuloDAO {

    AdmModuloDAO instance;
    private JdbcTemplate dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new JdbcTemplate(dataSource);
    }

    public AdmModuloDAO AdmModuloDAO() {
        if (instance == null) {
            instance = new AdmModuloDAO();
        }
        return instance;
    }
    
    public List<AdmModulo> getAllModules() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY id";
        try {
            List<AdmModulo> admModulo = dataSource.query(sql,
                    new AdmModuloDAO.AdmModuloMapper());
            return admModulo;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }
    
    public AdmModulo findOptionByName(String name) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + " = ?";
        Object[] args = {name};
        try {
            AdmModulo option = dataSource.queryForObject(sql, args,
                    new AdmModuloMapper());
            return option;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    

    private final class AdmModuloMapper implements RowMapper<AdmModulo> {

        @Override
        public AdmModulo mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdmModulo modulo = null;
            modulo = new AdmModulo(
                    rs.getLong(ID),
                    rs.getString(NAME),                   
                    rs.getString(DESCRIPTION),
                    rs.getLong(ID_PADRE),
                    rs.getBoolean(IS_LEAF)
            );
            return modulo;
        }
    }
    final String TABLE_NAME = "adm_modulo";
    final String ID = "id";
    final String NAME = "name";
    final String ID_PADRE = "id_padre";
    final String DESCRIPTION = "descripcion";
    final String IS_LEAF = "is_leaf";
}
