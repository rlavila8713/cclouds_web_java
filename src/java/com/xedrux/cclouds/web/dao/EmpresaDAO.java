package com.xedrux.cclouds.web.dao;

import com.xedrux.cclouds.web.entities.CcloudsEmpresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;
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
public class EmpresaDAO {

    EmpresaDAO instance;
    private JdbcTemplate dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new JdbcTemplate(dataSource);
    }

    public EmpresaDAO EmpresaDAO() {
        if (instance == null) {
            instance = new EmpresaDAO();
        }
        return instance;
    }

    public List<CcloudsEmpresa> getAllEmpresas() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        try {
            List<CcloudsEmpresa> empresas = dataSource.query(sql,
                    new EmpresaDAO.EmpresaMapper());
            return empresas;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }

    public CcloudsEmpresa getEmpresa(long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        Object[] args = {id};
        try {
            CcloudsEmpresa empresa = dataSource.queryForObject(sql, args,
                    new EmpresaMapper());
            return empresa;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public long insertEmpresa(CcloudsEmpresa empresa) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String template = "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?);";
        String INSERT_SQL = String.format(template, TABLE_NAME, NAME, OBSERVACION,
                REPRESENTANTE, RUP, FECHA, ESOLOGAN, IMAGEN_LOGO);
        dataSource.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL,
                    new String[]{ID});
            ps.setObject(1, empresa.getNombreEmpresa());
            ps.setObject(2, empresa.getObservacionEmpresa());
            ps.setObject(3, empresa.getIdRepresentante());
            ps.setObject(4, empresa.getRupEmpresa());
            ps.setObject(5, empresa.getFechaConstitucionEmpresa());
            ps.setObject(6, empresa.getEsloganEmpresa());
            ps.setObject(7, empresa.getImagenLogoEmpresa());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean updateEmpresa(CcloudsEmpresa empresa) {
        String sql = "UPDATE %s SET "
                + "%s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE "
                + "%s = ?;";
        String UPDATE_SQL = String.format(sql, TABLE_NAME, NAME, OBSERVACION,
                REPRESENTANTE, RUP, FECHA, ESOLOGAN, IMAGEN_LOGO);
        return (dataSource.update(UPDATE_SQL, empresa.getNombreEmpresa(),
                empresa.getObservacionEmpresa(), empresa.getIdRepresentante(),
                empresa.getRupEmpresa(), new Date(empresa.getFechaConstitucionEmpresa()),
                empresa.getEsloganEmpresa(), empresa.getImagenLogoEmpresa(),
                empresa.getIdEmpresa()) > 0);
    }

    public Boolean deleteEmpresa(long id) {
        String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
        return (dataSource.update(DELETE_SQL, id) > 0);
    }

    private final class EmpresaMapper implements RowMapper<CcloudsEmpresa> {

        @Override
        public CcloudsEmpresa mapRow(ResultSet rs, int rowNum) throws SQLException {
            CcloudsEmpresa empresa = null;
            empresa = new CcloudsEmpresa(
                    rs.getInt(ID),
                    rs.getString(NAME),
                    rs.getString(OBSERVACION),
                    rs.getInt(REPRESENTANTE),
                    rs.getString(RUP),
                    rs.getDate(FECHA),
                    rs.getString(ESOLOGAN),
                    rs.getString(IMAGEN_LOGO)
            );
            return empresa;
        }
    }
    final String TABLE_NAME = "cclouds_empresa";
    final String ID = "id_empresa";
    final String NAME = "nombre_empresa";
    final String OBSERVACION = "observacion_empresa";
    final String REPRESENTANTE = "id_representante";
    final String RUP = "rup_empresa";
    final String FECHA = "fecha_constitucion_empresa";
    final String ESOLOGAN = "eslogan_empresa";
    final String IMAGEN_LOGO = "imagen_logo_empresa";

}
