package com.xedrux.cclouds.web.dao;

import com.xedrux.cclouds.web.entities.CcloudsOptions;
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
public class OptionDAO {
    OptionDAO instance;
    private JdbcTemplate dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new JdbcTemplate(dataSource);
    }

    public OptionDAO OptionDAO() {
        if (instance == null) {
            instance = new OptionDAO();
        }
        return instance;
    }
        
    
    public Collection<CcloudsOptions> getAllOptionss(){
        String sql = "select * from " + TABLE_NAME;
        try {
            List<CcloudsOptions> options = dataSource.query(sql,
                    new OptionMapper());
            return options;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }
    
    public long insertOptions(CcloudsOptions option) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String template = "INSERT INTO %s (%s,"
                + " %s , %s )"
                + "VALUES (?, ?, ?);";
        String INSERT_SQL = String.format(template, TABLE_NAME, NAME,ID_MENU,
                DESCRIPTION);
        
        dataSource.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{ID});
            ps.setObject(1, option.getName());
            ps.setObject(2, option.getIdMenu());
            ps.setObject(3, option.getDescriptionOption());
            return ps;
        }, keyHolder);
        
        return keyHolder.getKey().longValue();
    }
    public boolean updateOption(CcloudsOptions option) {
        String sql = "UPDATE %s SET "
                + "%s=?, %s=?, %s=? WHERE "
                + "%s = ?;";
        String UPDATE_SQL = String.format(sql, TABLE_NAME,NAME,ID_MENU,
                DESCRIPTION,ID);
        return (dataSource.update(UPDATE_SQL,  option.getName(), 
                option.getIdMenu(), option.getDescriptionOption(),
                option.getIdOption()) > 0);
    }
    
    public CcloudsOptions getOption(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        Object[] args = {id};
        try {
            CcloudsOptions module = dataSource.queryForObject(sql, args,
                    new OptionMapper());
            return module;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public CcloudsOptions findOptionByName(String name) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + " = ?";
        Object[] args = {name};
        try {
            CcloudsOptions option = dataSource.queryForObject(sql, args,
                    new OptionMapper());
            return option;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    public Boolean deleteOption(long id) {
        String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
        return (dataSource.update(DELETE_SQL, id) > 0);
    }
    private  final class OptionMapper  implements RowMapper<CcloudsOptions>{

        public CcloudsOptions mapRow(ResultSet rs, int rowNum) throws SQLException {
            CcloudsOptions module;
                            module = new CcloudsOptions(
                                    rs.getLong(ID),
                                    rs.getString(NAME),
                                    rs.getLong(ID_MENU),
                                    rs.getString(DESCRIPTION)
                            );
                        return module;
        }
    }
    String TABLE_NAME = "cclouds_options";
    String ID = "id_option";
    String NAME = "name";
    String ID_MENU = "id_menu";
    String DESCRIPTION = "description_option";
}
