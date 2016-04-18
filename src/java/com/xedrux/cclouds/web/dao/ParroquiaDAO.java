package com.xedrux.cclouds.web.dao;

import com.xedrux.cclouds.web.entities.CcloudsParroquia;
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
 * @author Isidro Rodríguez Gamez
 */
public class ParroquiaDAO {

    ParroquiaDAO instance;
    private JdbcTemplate dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new JdbcTemplate(dataSource);
    }

    public ParroquiaDAO ParroquiaDAO() {
        if (instance == null) {
            instance = new ParroquiaDAO();
        }
        return instance;
    }

    public List<CcloudsParroquia> getAllParroquias() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        try {
            List<CcloudsParroquia> parroquias = dataSource.query(sql,
                    new ParroquiaDAO.ParroquiaMapper());
            return parroquias;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }

    public CcloudsParroquia getParroquia(long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        Object[] args = {id};
        try {
            CcloudsParroquia parroquia = dataSource.queryForObject(sql, args,
                    new ParroquiaMapper());
            return parroquia;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<CcloudsParroquia> getAllParroquiasFrom(long city) {
        String sql = "select * from " + TABLE_NAME + " WHERE " + ID_CITY + "= ? ;";
        Object[] args = {city};
        try {
            List<CcloudsParroquia> parroquias = dataSource.query(sql, args,
                    new ParroquiaMapper());
            return parroquias;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }
    }

    public long insertParroquia(CcloudsParroquia parroquia) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String template = "INSERT INTO %s (%s, %s, %s, %s)"
                + "VALUES (?, ?, ?, ?);";
        String INSERT_SQL = String.format(template, TABLE_NAME, ID_CITY,
                CODE, NAME, DESCRIPTION);
        dataSource.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL,
                    new String[]{ID});
            ps.setObject(1, parroquia.getIdCity());
            ps.setObject(2, parroquia.getCodeParroquia());
            ps.setObject(3, parroquia.getNameParroquia());
            ps.setObject(4, parroquia.getDescriptionParroquia());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean updateParroquia(CcloudsParroquia parroquia) {
        String sql = "UPDATE %s SET "
                + "%s=?, %s=?, %s=?, %s=? WHERE "
                + "%s = ?;";
        String UPDATE_SQL = String.format(sql, TABLE_NAME, ID_CITY, CODE,
                NAME, DESCRIPTION, ID);
        return (dataSource.update(UPDATE_SQL, parroquia.getIdCity(),
                parroquia.getCodeParroquia(), parroquia.getNameParroquia(),
                parroquia.getDescriptionParroquia(),
                parroquia.getIdParroquia()) > 0);
    }

    public Boolean deleteParroquia(long id) {
        String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
        return (dataSource.update(DELETE_SQL, id) > 0);
    }

    private final class ParroquiaMapper implements RowMapper<CcloudsParroquia> {

        @Override
        public CcloudsParroquia mapRow(ResultSet rs, int rowNum) throws SQLException {
            CcloudsParroquia parroquia = null;
            parroquia = new CcloudsParroquia(
                    rs.getLong(ID),
                    rs.getLong(ID_CITY),
                    rs.getInt(CODE),
                    rs.getString(NAME),
                    rs.getString(DESCRIPTION)
            );
            return parroquia;
        }
    }
    final String TABLE_NAME = "cclouds_parroquia";
    final String ID = "id_parroquia";
    final String ID_CITY = "id_city";
    final String CODE = "code_parroquia";
    final String NAME = "name_parroquia";
    final String DESCRIPTION = "description_parroquia";
}
