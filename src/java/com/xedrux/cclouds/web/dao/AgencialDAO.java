package com.xedrux.cclouds.web.dao;

import com.xedrux.cclouds.web.entities.CcloudsAgencia;
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
public class AgencialDAO {

    AgencialDAO instance;
    private JdbcTemplate dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new JdbcTemplate(dataSource);
    }

    public AgencialDAO AgenciaDAO() {
        if (instance == null) {
            instance = new AgencialDAO();
        }
        return instance;
    }

    public List<CcloudsAgencia> getAllAgencias() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        try {
            List<CcloudsAgencia> agencia = dataSource.query(sql,
                    new AgencialDAO.AgenciaMapper());
            return agencia;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }

    public CcloudsAgencia getAgencia(long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        Object[] args = {id};
        try {
            CcloudsAgencia agencia = dataSource.queryForObject(sql, args,
                    new AgenciaMapper());
            return agencia;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<CcloudsAgencia> getAllAgenciasFrom(long sucursal) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_SUCURSAL + "= ? ;";
        Object[] args = {sucursal};
        try {
            List<CcloudsAgencia> agencia = dataSource.query(sql, args,
                    new AgenciaMapper());
            return agencia;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }

    public long insertAgencia(CcloudsAgencia agencia) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String template = "INSERT INTO %s (%s, %s, %s, %s)"
                + "VALUES (?, ?, ?, ?);";
        String INSERT_SQL = String.format(template, TABLE_NAME, ID_SUCURSAL, NAME,
                OBSERVACION, REPRESENTATNTE1);
        dataSource.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL,
                    new String[]{ID});
            ps.setObject(1, agencia.getIdSucursal());
            ps.setObject(2, agencia.getNombreAgencia());
            ps.setObject(3, agencia.getObservacionAgencia());
            ps.setObject(4, agencia.getIdRepresentanteAgencia());
          
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean updateAgencia(CcloudsAgencia agencia) {
        String sql = "UPDATE %s SET "
                + "%s=?, %s=?, %s=?, %s=? WHERE "
                + "%s = ?;";
        String UPDATE_SQL = String.format(sql, TABLE_NAME, ID_SUCURSAL, NAME,
                OBSERVACION, REPRESENTATNTE1, ID);
        return (dataSource.update(UPDATE_SQL, agencia.getIdSucursal(),
                agencia.getNombreAgencia(), agencia.getObservacionAgencia(),
                agencia.getIdRepresentanteAgencia(),
                agencia.getIdSucursal()) > 0);
    }

    public Boolean deleteAgencia(long id) {
        String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
        return (dataSource.update(DELETE_SQL, id) > 0);
    }

    private final class AgenciaMapper implements RowMapper<CcloudsAgencia> {

        @Override
        public CcloudsAgencia mapRow(ResultSet rs, int rowNum) throws SQLException {
            CcloudsAgencia agencia = null;
            agencia = new CcloudsAgencia(
                    rs.getLong(ID),
                    rs.getString(NAME),
                    rs.getString(OBSERVACION),
                    rs.getInt(REPRESENTATNTE1),
                    rs.getLong(ID_SUCURSAL)
            );
            return agencia;
        }
    }
    final String TABLE_NAME = "cclouds_agencia";
    final String ID = "id_agencia";
    final String ID_SUCURSAL = "id_sucursal";
    final String NAME = "nombre_agencia";
    final String OBSERVACION = "observacion_agencia";
    final String REPRESENTATNTE1 = "id_representante_agencia";
}
