package com.xedrux.cclouds.web.dao;

import com.xedrux.cclouds.web.entities.CcloudsSucursal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author Reinier
 */
public class SucursalDAO {

    SucursalDAO instance;
    private JdbcTemplate dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new JdbcTemplate(dataSource);
    }

    public SucursalDAO SucursalDAO() {
        if (instance == null) {
            instance = new SucursalDAO();
        }
        return instance;
    }

    public List<CcloudsSucursal> getAllSucursales() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        try {
            List<CcloudsSucursal> sucursal = dataSource.query(sql,
                    new SucursalDAO.SucursalMapper());
            return sucursal;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }

    public CcloudsSucursal getSucursal(long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        Object[] args = {id};
        try {
            CcloudsSucursal sucursal = dataSource.queryForObject(sql, args,
                    new SucursalMapper());
            return sucursal;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<CcloudsSucursal> getAllSucursalFrom(long subEmpresa) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_SUB_EMPRESA + "= ? ;";
        Object[] args = {subEmpresa};
        try {
            List<CcloudsSucursal> sucursal = dataSource.query(sql, args,
                    new SucursalMapper());
            return sucursal;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }

    public long insertSucursal(CcloudsSucursal sucursal) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String template = "INSERT INTO %s (%s, %s, %s, %s)"
                + "VALUES (?, ?, ?, ?);";
        String INSERT_SQL = String.format(template, TABLE_NAME, ID_SUB_EMPRESA, NAME,
                OBSERVACION, REPRESENTATNTE1);
        dataSource.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL,
                    new String[]{ID});
            ps.setObject(1, sucursal.getIdSubEmpresa());
            ps.setObject(2, sucursal.getNombreSucursal());
            ps.setObject(3, sucursal.getObservacionSucursal());
            ps.setObject(4, sucursal.getIdRepresentanteSucursal());
          
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean updateSucursal(CcloudsSucursal sucursal) {
        String sql = "UPDATE %s SET "
                + "%s=?, %s=?, %s=?, %s=? WHERE "
                + "%s = ?;";
        String UPDATE_SQL = String.format(sql, TABLE_NAME, ID_SUB_EMPRESA, NAME,
                OBSERVACION, REPRESENTATNTE1, ID);
        return (dataSource.update(UPDATE_SQL, sucursal.getIdSubEmpresa(),
                sucursal.getNombreSucursal(), sucursal.getObservacionSucursal(),
                sucursal.getIdRepresentanteSucursal(),
                sucursal.getIdSucursal()) > 0);
    }

    public Boolean deleteSucursal(long id) {
        String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
        return (dataSource.update(DELETE_SQL, id) > 0);
    }

    private final class SucursalMapper implements RowMapper<CcloudsSucursal> {

        @Override
        public CcloudsSucursal mapRow(ResultSet rs, int rowNum) throws SQLException {
            CcloudsSucursal sucursal = null;
            sucursal = new CcloudsSucursal(
                    rs.getInt(ID),
                    rs.getString(NAME),
                    rs.getString(OBSERVACION),
                    rs.getInt(REPRESENTATNTE1),
                    rs.getLong(ID_SUB_EMPRESA)
            );
            return sucursal;
        }
    }
    final String TABLE_NAME = "cclouds_sucursal";
    final String ID = "id_sucursal";
    final String ID_SUB_EMPRESA = "id_sub_empresa";
    final String NAME = "nombre_sucursal";
    final String OBSERVACION = "observacion_sucursal";
    final String REPRESENTATNTE1 = "id_representante_sucursal";
}
