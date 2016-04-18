package com.xedrux.cclouds.web.dao;

import com.xedrux.cclouds.web.entities.CcloudsUsuario;
import com.xedrux.cclouds.web.exceptions.UnableToCreateEntityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class UserDAO {

    UserDAO instance;
    private JdbcTemplate dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new JdbcTemplate(dataSource);
    }

    public UserDAO UserDAO() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public long insertUser(CcloudsUsuario user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String template = "INSERT INTO %s (%s,"
                + " %s, %s, %s,%s,"
                + " %s, %s, %s, %s,"
                + " %s,%s, %s, %s,"
                + " %s)"
                + "VALUES (?, ?, ?, ?,?, ?, ?, ?, ?, CAST(? AS date),?, ?, ?,"
                + " ?);";
        String INSERT_SQL = String.format(template, TABLE_NAME, ROL, USERNAME, PASSWORD, TOKEN, PHONE_NUMBER,
                USER_EMAIL, FIRST_NAME, LAST_NAME, SEX, BIRTH_DATE, DB_HASH,
                PLAIN_TEXT_PASSWORD,  PARROQUIA, ENABLED);
        dataSource.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{ID});
            ps.setObject(1, user.getIdRol());
            ps.setObject(2, user.getUsername());
            ps.setObject(3, user.getPassword());
            ps.setObject(4, user.getPasswordResetToken());
            ps.setObject(5, user.getPhoneNumber());
            ps.setObject(6, user.getUserEmail());
            ps.setObject(7, user.getFirstName());
            ps.setObject(8, user.getLastName());
            ps.setObject(9, user.getSex());
            ps.setString(10, user.getDateBirth());
            ps.setObject(11, user.getDbHash());
            ps.setObject(12, user.getPlainTextPassword());
            ps.setObject(13, user.getParroquia());
            ps.setObject(14, user.isEnabled());
            return ps;
        }, keyHolder);
        
        return keyHolder.getKey().longValue();
    }

    public boolean updateUser(CcloudsUsuario user){
        if(user.getPassword()==null)
            return updateUserWithoutPassword(user);
        else 
            return updateUserWithPassword(user);
    }
    
    private boolean updateUserWithPassword(CcloudsUsuario user) {
        String sql = "UPDATE %s SET "
                + "%s=?, %s=?, %s=?, %s=?,"
                + " %s=?, %s=?, %s=?,"
                + " %s=?, %s=?, %s=?, %s=?, "
                + "%s=?, %s=?, %s=? WHERE "
                + "%s = ?;";
        String UPDATE_SQL = String.format(sql, TABLE_NAME, ROL, USERNAME, PASSWORD, TOKEN,
                PHONE_NUMBER, USER_EMAIL, FIRST_NAME, LAST_NAME, SEX, BIRTH_DATE,
                DB_HASH, PLAIN_TEXT_PASSWORD, PARROQUIA, ENABLED,ID);
        return (dataSource.update(UPDATE_SQL, user.getIdRol(), user.getUsername(),
                user.getPassword(), user.getPasswordResetToken(),
                user.getPhoneNumber(), user.getUserEmail(), user.getFirstName(),
                user.getLastName(), user.getSex(), new Date(user.getDateBirth()),//TODO: reomve this once message converter is properly configured
                user.getDbHash(), user.getPlainTextPassword(),
                user.getParroquia(), user.isEnabled(), user.getIdUser()) > 0);
    }

    private boolean updateUserWithoutPassword(CcloudsUsuario user) {
        String sql = "UPDATE %s SET "
                + "%s=?,  %s=?, %s=?,"
                + " %s=?, %s=?, %s=?,"
                + " %s=?, %s=?, %s=?, %s=?, "
                + "%s=?, %s=?, %s=? WHERE "
                + "%s = ?;";
        String UPDATE_SQL = String.format(sql, TABLE_NAME, ROL, USERNAME,  TOKEN,
                PHONE_NUMBER, USER_EMAIL, FIRST_NAME, LAST_NAME, SEX, BIRTH_DATE,
                DB_HASH, PLAIN_TEXT_PASSWORD, PARROQUIA, ENABLED,ID);
        return (dataSource.update(UPDATE_SQL, user.getIdRol(), user.getUsername(),
                user.getPasswordResetToken(),
                user.getPhoneNumber(), user.getUserEmail(), user.getFirstName(),
                user.getLastName(), user.getSex(), new Date(user.getDateBirth()),//TODO: reomve this once message converter is properly
                user.getDbHash(), user.getPlainTextPassword(),
                user.getParroquia(), user.isEnabled(), user.getIdUser()) > 0);
    }
    
    public List<CcloudsUsuario> getAllUsers() {
        String sql = "select * from " + TABLE_NAME;
        try {
            List<CcloudsUsuario> users = dataSource.query(sql,
                    new UserMapper());
            return users;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }

    public CcloudsUsuario getUsuario(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        Object[] args = {id};
        try {
            CcloudsUsuario user = dataSource.queryForObject(sql, args,
                    new UserMapper());
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public CcloudsUsuario findUsuarioByUsername(String username) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME + " = ?";
        Object[] args = {username};
        try {
            CcloudsUsuario user = dataSource.queryForObject(sql, args,
                    new UserMapper());
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Boolean deleteUser(long id) {
        String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
        return (dataSource.update(DELETE_SQL, id) > 0);
    }

    private  final class UserMapper  implements RowMapper<CcloudsUsuario>{

        @Override
        public CcloudsUsuario mapRow(ResultSet rs, int rowNum) throws SQLException {
            CcloudsUsuario u = null;
                        try {
                            u = new CcloudsUsuario(
                                    rs.getLong(ID),
                                    rs.getLong(ROL),
                                    rs.getString(USERNAME),
                                    null,//password can't be send to anyone
//                                    rs.getString(PASSWORD),
                                    rs.getString(TOKEN),
                                    rs.getString(PHONE_NUMBER),
                                    rs.getString(USER_EMAIL),
                                    rs.getString(FIRST_NAME),
                                    rs.getString(LAST_NAME),
                                    rs.getString(SEX),
                                    rs.getDate(BIRTH_DATE),
                                    rs.getString(DB_HASH),
                                    rs.getString(PLAIN_TEXT_PASSWORD),
                                    rs.getLong(PARROQUIA),
                                    rs.getBoolean(ENABLED)
                            );
                        } catch (UnableToCreateEntityException ex) {
                            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                            return new CcloudsUsuario();
                        }
                        return u;
        }
    }
    final String TABLE_NAME = "cclouds_usuario";
    final String ID = "id_user";
    final String ROL = "id_rol";
    final String USERNAME = "username";
    final String PASSWORD = "password";
    final String TOKEN = "password_reset_token";
    final String PHONE_NUMBER = "phone_number";
    final String USER_EMAIL = "user_email";
    final String FIRST_NAME = "first_name";
    final String LAST_NAME = "last_name";
    final String SEX = "sex";
    final String BIRTH_DATE = "date_birth";
    final String DB_HASH = "db_hash";
    final String PLAIN_TEXT_PASSWORD = "plain_text_password";
    final String PARROQUIA = "parroquia";
    final String ENABLED = "enabled";

}
