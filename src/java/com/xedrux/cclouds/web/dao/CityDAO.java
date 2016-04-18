package com.xedrux.cclouds.web.dao;

import com.xedrux.cclouds.web.entities.CcloudsCity;
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
 * @author Isidro Rdríguez Gamez
 */
public class CityDAO {

    CityDAO instance;
    private JdbcTemplate dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new JdbcTemplate(dataSource);
    }

    public CityDAO CityDAO() {
        if (instance == null) {
            instance = new CityDAO();
        }
        return instance;
    }
    
    public List<CcloudsCity> getAllCities() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        try {
            List<CcloudsCity> cities = dataSource.query(sql,
                    new CityDAO.CityMapper());
            return cities;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }

    public CcloudsCity getCity(long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        Object[] args = {id};
        try {
            CcloudsCity city = dataSource.queryForObject(sql, args,
                    new CityMapper());
            return city;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<CcloudsCity> getAllCitiesFrom(long province) {
        String sql = "select * from " + TABLE_NAME + " WHERE " + ID_PROVINCE + "= ? ;";
        Object[] args = {province};
        try {
            List<CcloudsCity> cities = dataSource.query(sql, args,
                    new CityMapper());
            return cities;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }

    public long insertCity(CcloudsCity city) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String template = "INSERT INTO %s (%s, %s, %s, %s)"
                + "VALUES (?, ?, ?, ?);";
        String INSERT_SQL = String.format(template, TABLE_NAME, ID_PROVINCE,
                CODE, NAME, DESCRIPTION);
        dataSource.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL,
                    new String[]{ID});
            ps.setObject(1, city.getIdProvince());
            ps.setObject(2, city.getCodeCity());
            ps.setObject(3, city.getNameCity());
            ps.setObject(4, city.getDescriptionCity());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean updateCity(CcloudsCity city) {
        String sql = "UPDATE %s SET "
                + "%s=?, %s=?, %s=?, %s=? WHERE "
                + "%s = ?;";
        String UPDATE_SQL = String.format(sql, TABLE_NAME, ID_PROVINCE, CODE,
                NAME, DESCRIPTION, ID);
        return (dataSource.update(UPDATE_SQL, city.getIdProvince(),
                city.getCodeCity(), city.getNameCity(),
                city.getDescriptionCity(),
                city.getIdCity()) > 0);
    }
    
    public Boolean deleteCity(long id) {
        String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
        return (dataSource.update(DELETE_SQL, id) > 0);
    }

    private final class CityMapper implements RowMapper<CcloudsCity> {

        @Override
        public CcloudsCity mapRow(ResultSet rs, int rowNum) throws SQLException {
            CcloudsCity city = null;
            city = new CcloudsCity(
                    rs.getLong(ID),
                    rs.getLong(ID_PROVINCE),
                    rs.getInt(CODE),
                    rs.getString(NAME),
                    rs.getString(DESCRIPTION)
            );
            return city;
        }
    }
    final String TABLE_NAME = "cclouds_city";
    final String ID = "id_city";
    final String ID_PROVINCE = "id_province";
    final String CODE = "code_city";
    final String NAME = "name_city";
    final String DESCRIPTION = "description_city";
}
