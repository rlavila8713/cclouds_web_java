package com.xedrux.cclouds.web.dao;

import com.xedrux.cclouds.web.entities.CcloudsModule;
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
 * @author Isidro Rdríguez Gamez
 */
public class ModuleDAO {
ModuleDAO instance;
    private JdbcTemplate dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new JdbcTemplate(dataSource);
    }

    public ModuleDAO ModuleDAO() {
        if (instance == null) {
            instance = new ModuleDAO();
        }
        return instance;
    }
        
    
    public Collection<CcloudsModule> getAllModules(){
        String sql = "select * from " + TABLE_NAME;
        try {
            List<CcloudsModule> modules = dataSource.query(sql,
                    new ModuleMapper());
            return modules;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }
    
    public long insertModule(CcloudsModule module) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String template = "INSERT INTO %s (%s,"
                + " %s )"
                + "VALUES (?, ?);";
        String INSERT_SQL = String.format(template, TABLE_NAME, NAME,DESCRIPTION);
        dataSource.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{ID_MODULE});
            ps.setObject(1, module.getName());
            ps.setObject(2, module.getDescription());
            return ps;
        }, keyHolder);
        
        return keyHolder.getKey().longValue();
    }
    
    public boolean updateModule(CcloudsModule module) {
        String sql = "UPDATE %s SET "
                + "%s=?, %s=? WHERE "
                + "%s = ?;";
        String UPDATE_SQL = String.format(sql, TABLE_NAME,NAME,DESCRIPTION,ID_MODULE);
        return (dataSource.update(UPDATE_SQL, module.getName(), module.getDescription(),
                module.getIdModule()) > 0);
    }
    
    public CcloudsModule getModule(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_MODULE + " = ?";
        Object[] args = {id};
        try {
            CcloudsModule module = dataSource.queryForObject(sql, args,
                    new ModuleMapper());
            return module;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public CcloudsModule findModuleByName(String name) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + " = ?";
        Object[] args = {name};
        try {
            CcloudsModule module = dataSource.queryForObject(sql, args,
                    new ModuleMapper());
            return module;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    public Boolean deleteModule(long id) {
        String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_MODULE + "=?;";
        return (dataSource.update(DELETE_SQL, id) > 0);
    }
    private  final class ModuleMapper  implements RowMapper<CcloudsModule>{

        public CcloudsModule mapRow(ResultSet rs, int rowNum) throws SQLException {
            CcloudsModule module;
                            module = new CcloudsModule(
                                    rs.getInt(ID_MODULE),
                                    rs.getString(NAME),
                                    rs.getString(DESCRIPTION)
                            );
                        return module;
        }
    }
    String TABLE_NAME = "cclouds_module";
    String ID_MODULE = "id_module";
    String NAME = "name";
    String DESCRIPTION = "description";
}
