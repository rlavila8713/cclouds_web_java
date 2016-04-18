package com.xedrux.cclouds.web.controllers;

import com.xedrux.cclouds.web.dao.EmpresaDAO;
import com.xedrux.cclouds.web.dao.SubEmpresaDAO;
import com.xedrux.cclouds.web.dao.SucursalDAO;
import com.xedrux.cclouds.web.entities.CcloudsEmpresa;
import com.xedrux.cclouds.web.entities.CcloudsSubEmpresa;
import com.xedrux.cclouds.web.entities.CcloudsSucursal;
import com.xedrux.cclouds.web.exceptions.EntityNotFoundException;
import com.xedrux.cclouds.web.exceptions.UnableToCreateEntityException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Reinier
 */
@RestController
@RequestMapping("/admin/sucursal")
public class SucursalController {

    @Autowired
    SucursalDAO sucursalDAO;
    @Autowired
    SubEmpresaDAO subEmpresaDAO;
    @Autowired
    EmpresaDAO empresaDAO;

    public void setSucursalDAO(SucursalDAO sucursalDAO) {
        this.sucursalDAO = sucursalDAO;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getAllSucursales() {
        Collection<CcloudsSucursal> sucursales = sucursalDAO.getAllSucursales();
        HashMap<String, Object> response = new HashMap<>();
        response.put("sucursales", sucursales);
        return response;
    }

    @RequestMapping(value = "/id={id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getSucursal(@ModelAttribute("id") Long id)
            throws EntityNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        CcloudsSucursal sucursal = sucursalDAO.getSucursal(id);
        if (sucursal != null) {
            response.put("sucursal", sucursal);
        } else {
            throw new EntityNotFoundException("There is not any sucursal with id: "
                    + id);
        }
        return response;
    }

    @RequestMapping(value = "/from_sub_empresa={id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getSucursalesFrom(@ModelAttribute("id") Long subEmpresa)
            throws EntityNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        List<CcloudsSucursal> sucursales = sucursalDAO.getAllSucursalFrom(subEmpresa);
        response.put("sucursales", sucursales);
        return response;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> insertSucursal(
            @RequestBody @Valid CcloudsSucursal sucursal, BindingResult result)
            throws UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            throw new UnableToCreateEntityException("Unable to create sucursal. "
                    + "There are wrong fields.",
                    result.getFieldErrors());
        } else {
            long id = sucursalDAO.insertSucursal(sucursal);
            response.put("id", id);
        }
        return response;
    }

    @RequestMapping(value = "/{Id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> updateSucursal(
            @ModelAttribute("Id") int id,
            @RequestBody @Valid CcloudsSucursal sucursal,
            BindingResult result)
            throws EntityNotFoundException, UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            throw new UnableToCreateEntityException("Unable to update sucursal."
                    + " There are wrong fields.",
                    result.getFieldErrors());
        } else {
            sucursal.setIdSucursal(id);
            if (sucursalDAO.updateSucursal(sucursal)) {
                response.put("success", true);
            } else {
                throw new EntityNotFoundException("Sucursal not found.");
            }
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteSucursal(@ModelAttribute("id") long id) throws
            EntityNotFoundException {
        if (!sucursalDAO.deleteSucursal(id)) {
            throw new EntityNotFoundException(
                    "Couldn't delete. There is no sucursal with id:" + id);
        }
    }

    @RequestMapping(value = "/detailed/id={id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getSucursalSubEmpresaAndEmpresa(
            @ModelAttribute("id") Long id)
            throws EntityNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        CcloudsSucursal sucursal = sucursalDAO.getSucursal(id);
        if (sucursal != null) {
            CcloudsSubEmpresa subEmpresa = subEmpresaDAO.getSubEmpresa(sucursal.getIdSubEmpresa());
            List<CcloudsEmpresa> empresas = empresaDAO.getAllEmpresas();
            List<CcloudsSucursal> sucursales = sucursalDAO.getAllSucursalFrom(subEmpresa.getIdSubEmpresa());
            CcloudsEmpresa empresa = empresaDAO.getEmpresa(subEmpresa.getIdEmpresa());
            List<CcloudsSubEmpresa> subEmpresas = subEmpresaDAO.getAllSubEmpresasFrom(empresa.getIdEmpresa());
            response.put("empresa", empresa);
            response.put("subempresa", subEmpresa);
            response.put("sucursal", sucursal);
            response.put("sucursales", sucursales);
            response.put("subempresas", subEmpresas);
            response.put("empresas", empresaDAO.getAllEmpresas());
        } else {
            throw new EntityNotFoundException(MESSAGE + id);
        }
        return response;
    }
    final String MESSAGE = "No hay sucursal con id: ";
}
