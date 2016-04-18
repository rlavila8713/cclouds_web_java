package com.xedrux.cclouds.web.dao;

import com.xedrux.cclouds.web.entities.CcloudsRol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
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
 * @author Isidro Rodríguez Gamez
 */
public class RolDAO {

    RolDAO instance;
    private JdbcTemplate dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new JdbcTemplate(dataSource);
    }

    public RolDAO RolDAO() {
        if (instance == null) {
            instance = new RolDAO();
        }
        return instance;
    }

    public long insertRol(CcloudsRol rol) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String template = "INSERT INTO %s (%s,"
                + " %s )"
                + "VALUES (?, ?);";
        String INSERT_SQL = String.format(template, TABLE_NAME, NAME, DESCRIPTION);

        dataSource.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{ID_ROL});
            ps.setObject(1, rol.getName());
            ps.setObject(2, rol.getDescription());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Collection<CcloudsRol> getAllRols() {
        String sql = "select * from " + TABLE_NAME;
        try {
            List<CcloudsRol> rols = dataSource.query(sql,
                    new RolMapper());
            return rols;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }
    }

    public boolean updateRol(CcloudsRol rol) {
        String sql = "UPDATE %s SET "
                + "%s=?, %s=? WHERE "
                + "%s = ?;";
        String UPDATE_SQL = String.format(sql, TABLE_NAME, NAME, DESCRIPTION, ID_ROL);
        return (dataSource.update(UPDATE_SQL, rol.getName(),
                rol.getDescription(), rol.getIdRol()) > 0);
    }

    public boolean exists(long id) {
        String sql = "SELECT count(*) FROM " + TABLE_NAME + " WHERE " + ID_ROL
                + " = ?";
        Object[] args = {id};
        long count = dataSource.queryForObject(sql, args, new RowMapper<Long>() {

            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getLong(1);
            }
        });
        return count > 0;
    }

    public CcloudsRol getRol(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_ROL + " = ?";
        Object[] args = {id};
        try {
            CcloudsRol rol = dataSource.queryForObject(sql, args,
                    new RolMapper());
            return rol;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public CcloudsRol findRolByName(String name) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + " = ?";
        Object[] args = {name};
        try {
            CcloudsRol rol = dataSource.queryForObject(sql, args,
                    new RolMapper());
            return rol;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Boolean deleteRol(long id) {
        String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_ROL + "=?;";
        return (dataSource.update(DELETE_SQL, id) > 0);
    }

    private final class RolMapper implements RowMapper<CcloudsRol> {

        @Override
        public CcloudsRol mapRow(ResultSet rs, int rowNum) throws SQLException {
            CcloudsRol rol;
            rol = new CcloudsRol(
                    rs.getLong(ID_ROL),
                    rs.getString(NAME),
                    rs.getString(DESCRIPTION)
            );
            return rol;
        }
    }
    String TABLE_NAME = "cclouds_rol";
    String ID_ROL = "id_rol";
    String NAME = "name";
    String DESCRIPTION = "description";
}
