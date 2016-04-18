package com.xedrux.cclouds.web.dao;

import com.xedrux.cclouds.web.entities.CcloudsCountry;
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
public class CountryDAO {
    CountryDAO instance;
    private JdbcTemplate dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new JdbcTemplate(dataSource);
    }

    public CountryDAO CountryDAO() {
        if (instance == null) {
            instance = new CountryDAO();
        }
        return instance;
    }

    public long insertCountry(CcloudsCountry country) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String template = "INSERT INTO %s (%s, %s, %s)"
                + "VALUES (?, ?, ?);";
        String INSERT_SQL = String.format(template, TABLE_NAME, CODE,NAME,
                DESCRIPTION);
        dataSource.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{ID});
            ps.setObject(1, country.getCodeCountry());
            ps.setObject(2, country.getNameCountry());
            ps.setObject(3, country.getDescriptionCountry());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean updateCountry(CcloudsCountry country) {
        String sql = "UPDATE %s SET "
                + "%s=?, %s=?, %s=? WHERE "
                + "%s = ?;";
        String UPDATE_SQL = String.format(sql, TABLE_NAME, CODE,NAME,
                                            DESCRIPTION, ID);
        return (dataSource.update(UPDATE_SQL, country.getCodeCountry(),
                country.getNameCountry(), country.getDescriptionCountry(),
                country.getIdCountry()) > 0);
    }

    public List<CcloudsCountry> getAllCountries() {
        String sql = "select * from " + TABLE_NAME;
        try {
            List<CcloudsCountry> countries = dataSource.query(sql,
                    new CountryMapper());
            return countries;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }

    public CcloudsCountry getCountry(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        Object[] args = {id};
        try {
            CcloudsCountry country = dataSource.queryForObject(sql, args,
                    new CountryMapper());
            return country;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public CcloudsCountry findCountryByCode(String code) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + CODE + " = ?";
        Object[] args = {code};
        try {
            CcloudsCountry country = dataSource.queryForObject(sql, args,
                    new CountryMapper());
            return country;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Boolean deleteCountry(long id) {
        String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
        return (dataSource.update(DELETE_SQL, id) > 0);
    }

    private  final class CountryMapper  implements RowMapper<CcloudsCountry>{

        @Override
        public CcloudsCountry mapRow(ResultSet rs, int rowNum) throws SQLException {
            CcloudsCountry country = null;
                            country = new CcloudsCountry(
                                    rs.getLong(ID),
                                    rs.getString(CODE),
                                    rs.getString(NAME),
                                    rs.getString(DESCRIPTION)
                            );
                        
                        return country;
        }
    }
    final String TABLE_NAME = "cclouds_country";
    final String ID = "id_country";
    final String CODE = "code_country";
    final String NAME = "name_country";
    final String DESCRIPTION = "description_country";

}
