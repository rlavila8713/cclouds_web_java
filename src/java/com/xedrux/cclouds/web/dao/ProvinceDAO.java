package com.xedrux.cclouds.web.dao;

import com.xedrux.cclouds.web.entities.CcloudsProvince;
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
public class ProvinceDAO {

    ProvinceDAO instance;
    private JdbcTemplate dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new JdbcTemplate(dataSource);
    }

    public ProvinceDAO ProvinceDAO() {
        if (instance == null) {
            instance = new ProvinceDAO();
        }
        return instance;
    }

    public List<CcloudsProvince> getAllProvinces() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        try {
            List<CcloudsProvince> provinces = dataSource.query(sql,
                    new ProvinceDAO.ProvinceMapper());
            return provinces;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }

    public CcloudsProvince getProvince(long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        Object[] args = {id};
        try {
            CcloudsProvince province = dataSource.queryForObject(sql, args,
                    new ProvinceMapper());
            return province;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public long insertProvince(CcloudsProvince province) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String template = "INSERT INTO %s (%s, %s, %s, %s)"
                + "VALUES (?, ?, ?, ?);";
        String INSERT_SQL = String.format(template, TABLE_NAME, ID_COUNTRY,
                CODE, NAME, DESCRIPTION);
        dataSource.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL,
                    new String[]{ID});
            ps.setObject(1, province.getIdCountry());
            ps.setObject(2, province.getCodeProvince());
            ps.setObject(3, province.getNameProvince());
            ps.setObject(4, province.getDescriptionProvince());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean updateProvince(CcloudsProvince province) {
        String sql = "UPDATE %s SET "
                + "%s=?, %s=?, %s=?, %s=? WHERE "
                + "%s = ?;";
        String UPDATE_SQL = String.format(sql, TABLE_NAME, ID_COUNTRY, CODE,
                NAME, DESCRIPTION, ID);
        return (dataSource.update(UPDATE_SQL, province.getIdCountry(),
                province.getCodeProvince(), province.getNameProvince(),
                province.getDescriptionProvince(),
                province.getIdProvince()) > 0);
    }

    public List<CcloudsProvince> getAllProvincesFrom(long country) {
        String sql = "select * from " + TABLE_NAME + " WHERE " + ID_COUNTRY + "= ? ;";
        Object[] args = {country};
        try {
            List<CcloudsProvince> pronvinces = dataSource.query(sql, args,
                    new ProvinceMapper());
            return pronvinces;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }

    public Boolean deleteProvince(long id) {
        String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
        return (dataSource.update(DELETE_SQL, id) > 0);
    }

    private final class ProvinceMapper implements RowMapper<CcloudsProvince> {

        @Override
        public CcloudsProvince mapRow(ResultSet rs, int rowNum) throws SQLException {
            CcloudsProvince province = null;
            province = new CcloudsProvince(
                    rs.getLong(ID),
                    rs.getLong(ID_COUNTRY),
                    rs.getInt(CODE),
                    rs.getString(NAME),
                    rs.getString(DESCRIPTION)
            );

            return province;
        }
    }
    final String TABLE_NAME = "cclouds_province";
    final String ID = "id_province";
    final String ID_COUNTRY = "id_country";
    final String CODE = "code_province";
    final String NAME = "name_province";
    final String DESCRIPTION = "description_province";
}
