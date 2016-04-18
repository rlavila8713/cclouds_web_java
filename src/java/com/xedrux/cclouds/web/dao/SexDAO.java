package com.xedrux.cclouds.web.dao;

import com.xedrux.cclouds.web.entities.CcloudsSex;
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
public class SexDAO {
    SexDAO instance;
    private JdbcTemplate dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new JdbcTemplate(dataSource);
    }

    public SexDAO SexDAO() {
        if (instance == null) {
            instance = new SexDAO();
        }
        return instance;
    }
    public long insertSex(CcloudsSex sex) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String template = "INSERT INTO %s (%s,"
                + " %s )"
                + "VALUES (?, ?);";
        String INSERT_SQL = String.format(template, TABLE_NAME, DESCRIPTION, LETTER);
        
        dataSource.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{ID});
            ps.setObject(1, sex.getDescriptionSex());
            ps.setObject(2, sex.getLetter());
            return ps;
        }, keyHolder);
        
        return keyHolder.getKey().longValue();
    }

    public Collection<CcloudsSex> getAllSexs(){
        String sql = "select * from " + TABLE_NAME;
        try {
            List<CcloudsSex> sexs = dataSource.query(sql,
                    new SexMapper());
            return sexs;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }
    }
    
    public boolean updateSex(CcloudsSex sex) {
        String sql = "UPDATE %s SET "
                + "%s=?, %s=? WHERE "
                + "%s = ?;";
        String UPDATE_SQL = String.format(sql, TABLE_NAME,DESCRIPTION,LETTER,ID );
        return (dataSource.update(UPDATE_SQL, sex.getDescriptionSex(), 
                sex.getIdSex()) > 0);
    }
    
    public boolean exists(long id){
        String sql = "SELECT count(*) FROM " + TABLE_NAME + " WHERE " + ID 
                + " = ?";
        Object[] args = {id};
        long count = dataSource.queryForObject(sql, args, new RowMapper<Long>() {

            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getLong(1);
            }
        });
        return count>0;
    }
    
    public CcloudsSex getSex(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        Object[] args = {id};
        try {
            CcloudsSex sex = dataSource.queryForObject(sql, args,
                    new SexMapper());
            return sex;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    public Boolean deleteSex(long id) {
        String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
        return (dataSource.update(DELETE_SQL, id) > 0);
    }
    private  final class SexMapper  implements RowMapper<CcloudsSex>{

        @Override
        public CcloudsSex mapRow(ResultSet rs, int rowNum) throws SQLException {
            CcloudsSex sex;
                            sex = new CcloudsSex(
                                    rs.getLong(ID),
                                    rs.getString(DESCRIPTION),
                                    rs.getString(LETTER)
                            );
                        return sex;
        }
    }
String TABLE_NAME="cclouds_sex";
String ID="id_sex";
String DESCRIPTION="description_sex";    
String LETTER="letter";    
}
