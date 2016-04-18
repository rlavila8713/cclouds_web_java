package com.xedrux.cclouds.web.controllers;

import com.xedrux.cclouds.web.dao.EmpresaDAO;
import com.xedrux.cclouds.web.dao.SubEmpresaDAO;
import com.xedrux.cclouds.web.entities.CcloudsEmpresa;
import com.xedrux.cclouds.web.entities.CcloudsSubEmpresa;
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
@RequestMapping("/admin/subempresa")
public class SubEmpresaController {

    @Autowired
    SubEmpresaDAO subEmpresaDAO;
    @Autowired
    EmpresaDAO empresaDAO;

    public void setSubEmpresaDAO(SubEmpresaDAO subEmpresaDAO) {
        this.subEmpresaDAO = subEmpresaDAO;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getAllSubEmpresas() {
        Collection<CcloudsSubEmpresa> subEmpresas = subEmpresaDAO.getAllSubEmpresas();
        HashMap<String, Object> response = new HashMap<>();
        response.put("subempresas", subEmpresas);
        return response;
    }

    @RequestMapping(value = "/from_empresa={id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getSubEmpresasFrom(@ModelAttribute("id") Long id)
            throws EntityNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        List<CcloudsSubEmpresa> subEmpresas = subEmpresaDAO.getAllSubEmpresasFrom(id);
        if (subEmpresas != null) {
            response.put("subempresas", subEmpresas);

        } else {
            throw new EntityNotFoundException("There is not any subEmpresa with id: "
                    + id);
        }
        return response;
    }

    @RequestMapping(value = "/id={id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getSubEmpresas(@ModelAttribute("id") long id)
            throws EntityNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        CcloudsSubEmpresa subEmpresa = subEmpresaDAO.getSubEmpresa(id);
        if (subEmpresa != null) {
            response.put("subempresa", subEmpresa);
        } else {
            throw new EntityNotFoundException("subEmpresa not found");
        }
        return response;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> insertSubEmpresa(
            @RequestBody @Valid CcloudsSubEmpresa subEmpresa, BindingResult result)
            throws UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            throw new UnableToCreateEntityException("Unable to create subEmpresa. "
                    + "There are wrong fields.",
                    result.getFieldErrors());
        } else {
            long id = subEmpresaDAO.insertSubEppresa(subEmpresa);
            response.put("id", id);
        }
        return response;
    }

    @RequestMapping(value = "/{Id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> updateSubEmpresa(
            @ModelAttribute("Id") int id,
            @RequestBody @Valid CcloudsSubEmpresa subEmpresa,
            BindingResult result)
            throws EntityNotFoundException, UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            throw new UnableToCreateEntityException("Unable to create subEmpresa."
                    + " There are wrong fields.",
                    result.getFieldErrors());
        } else {
            subEmpresa.setIdSubEmpresa(id);
            if (subEmpresaDAO.updateSubEmpresa(subEmpresa)) {
                response.put("success", true);
            } else {
                throw new EntityNotFoundException("SubEmpresa not found.");
            }
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteSubEmpresa(@ModelAttribute("id") long id) throws
            EntityNotFoundException {
        if (!subEmpresaDAO.deleteSubEmpresa(id)) {
            throw new EntityNotFoundException(
                    "Couldn't delete. There is no subEmpresa with id:" + id);
        }
    }

    @RequestMapping(value = "/detailed/id={id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getSubEmpresaAndEmpresa(
            @ModelAttribute("id") Long id)
            throws EntityNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        CcloudsSubEmpresa subEmpresa = subEmpresaDAO.getSubEmpresa(id);
        if (subEmpresa != null) {
            CcloudsEmpresa empresa = empresaDAO.getEmpresa(subEmpresa.getIdEmpresa());
            List<CcloudsEmpresa> empresas = empresaDAO.getAllEmpresas();

            response.put("empresa", empresa);
            response.put("subempresa", subEmpresa);
            response.put("empresas", empresas);
        } else {
            throw new EntityNotFoundException(MESSAGE + id);
        }
        return response;
    }

    final String MESSAGE = "No hay subEmpresas con id: ";

}
