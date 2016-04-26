package com.xedrux.cclouds.web.dao;

import com.xedrux.cclouds.web.entities.CcloudsSubEmpresa;
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
public class SubEmpresaDAO {

    SubEmpresaDAO instance;
    private JdbcTemplate dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new JdbcTemplate(dataSource);
    }

    public SubEmpresaDAO SubEmpresaDAO() {
        if (instance == null) {
            instance = new SubEmpresaDAO();
        }
        return instance;
    }

    public List<CcloudsSubEmpresa> getAllSubEmpresas() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        try {
            List<CcloudsSubEmpresa> subEmpresas = dataSource.query(sql,
                    new SubEmpresaDAO.SubEmpresaMapper());
            return subEmpresas;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }

    public CcloudsSubEmpresa getSubEmpresa(long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        Object[] args = {id};
        try {
            CcloudsSubEmpresa subEmpresa = dataSource.queryForObject(sql, args,
                    new SubEmpresaMapper());
            return subEmpresa;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<CcloudsSubEmpresa> getAllSubEmpresasFrom(long empresa) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_EMPRESA + "= ? ;";
        Object[] args = {empresa};
        try {
            List<CcloudsSubEmpresa> subEmpresas = dataSource.query(sql, args,
                    new SubEmpresaMapper());
            return subEmpresas;
        } catch (EmptyResultDataAccessException e) {
            return new LinkedList<>();
        }

    }

    public long insertSubEppresa(CcloudsSubEmpresa subEmpresa) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String template = "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String INSERT_SQL = String.format(template, TABLE_NAME, ID_EMPRESA, NAME,
                OBSERVACION, TIPO_NEGOCIO, REPRESENTATNTE1, REPRESENTATNTE2, FECHA, ESLOGAN, IMAGEN);
        dataSource.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL,
                    new String[]{ID});
            ps.setObject(1, subEmpresa.getIdEmpresa());
            ps.setObject(2, subEmpresa.getNombreSubEmpresa());
            ps.setObject(3, subEmpresa.getObservacionSubEmpresa());
            ps.setObject(4, subEmpresa.getIdTipoNegocioSubEmpresa());
            ps.setObject(5, subEmpresa.getIdRepresentante1SubEmpresa());
            ps.setObject(6, subEmpresa.getIdRepresentante2SubEmpresa());
            ps.setObject(7, subEmpresa.getFechaConstitucionSubEmpresa());
            ps.setObject(8, subEmpresa.getEsloganSubEmpresa());
            ps.setObject(9, subEmpresa.getImagenLogoSubEmpresa());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean updateSubEmpresa(CcloudsSubEmpresa subEmpresa) {
        String sql = "UPDATE %s SET "
                + "%s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE "
                + "%s = ?;";
        String UPDATE_SQL = String.format(sql, TABLE_NAME, ID_EMPRESA, NAME,
                OBSERVACION, TIPO_NEGOCIO, REPRESENTATNTE1, REPRESENTATNTE2, FECHA, ESLOGAN, IMAGEN, ID);
        return (dataSource.update(UPDATE_SQL, subEmpresa.getIdEmpresa(),
                subEmpresa.getNombreSubEmpresa(), subEmpresa.getObservacionSubEmpresa(),
                subEmpresa.getIdTipoNegocioSubEmpresa(), subEmpresa.getIdRepresentante1SubEmpresa(),
                subEmpresa.getIdRepresentante2SubEmpresa(),subEmpresa.getFechaConstitucionSubEmpresa(),
                subEmpresa.getEsloganSubEmpresa(),subEmpresa.getImagenLogoSubEmpresa(),
                subEmpresa.getIdSubEmpresa()) > 0);
    }

    public Boolean deleteSubEmpresa(long id) {
        String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
        return (dataSource.update(DELETE_SQL, id) > 0);
    }

    private final class SubEmpresaMapper implements RowMapper<CcloudsSubEmpresa> {

        @Override
        public CcloudsSubEmpresa mapRow(ResultSet rs, int rowNum) throws SQLException {
            CcloudsSubEmpresa subEmpresa = null;
            subEmpresa = new CcloudsSubEmpresa(
                    rs.getInt(ID),
                    rs.getString(NAME),
                    rs.getString(OBSERVACION),
                    rs.getInt(TIPO_NEGOCIO),
                    rs.getInt(REPRESENTATNTE1),
                    rs.getInt(REPRESENTATNTE2),
                    rs.getDate(FECHA),
                    rs.getString(ESLOGAN),
                    rs.getString(IMAGEN),
                    rs.getLong(ID_EMPRESA)
            );
            return subEmpresa;
        }
    }
    final String TABLE_NAME = "cclouds_sub_empresa";
    final String ID = "id_sub_empresa";
    final String ID_EMPRESA = "id_empresa";
    final String NAME = "nombre_sub_empresa";
    final String OBSERVACION = "observacion_sub_empresa";
    final String TIPO_NEGOCIO = "id_tipo_negocio_sub_empresa";
    final String REPRESENTATNTE1 = "id_representante_1_sub_empresa";
    final String REPRESENTATNTE2 = "id_representante_2_sub_empresa";
    final String FECHA = "fecha_constitucion_sub_empresa";
    final String ESLOGAN = "eslogan_sub_empresa";
    final String IMAGEN = "imagen_logo_sub_empresa";
}
