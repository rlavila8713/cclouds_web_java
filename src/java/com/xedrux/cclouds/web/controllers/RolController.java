package com.xedrux.cclouds.web.controllers;

import com.xedrux.cclouds.web.dao.RolDAO;
import com.xedrux.cclouds.web.dao.RolOptionDAO;
import com.xedrux.cclouds.web.entities.CcloudsRol;
import com.xedrux.cclouds.web.entities.CcloudsRolOption;
import com.xedrux.cclouds.web.exceptions.EntityNotFoundException;
import com.xedrux.cclouds.web.exceptions.UnableToCreateEntityException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
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
 * @author Isidro Rodríguez Gamez
 */
@RestController
@RequestMapping("/admin/rol")
public class RolController {

    @Autowired
    RolDAO rolDAO;
    @Autowired
    RolOptionDAO rolOptionDAO;

    public void setRolDAO(RolDAO DAO) {
        this.rolDAO = DAO;
    }

    public void setRolOptionDAO(RolOptionDAO rolOptionDAO) {
        this.rolOptionDAO = rolOptionDAO;
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getAllRols() {
        Collection<CcloudsRol> rols = rolDAO.getAllRols();
        HashMap<String, Object> response = new HashMap<>();
        response.put("rols", rols);
        return response;
    }

    @RequestMapping(value = "/id={id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getRol(@ModelAttribute("id") Long id) throws EntityNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        CcloudsRol rol = rolDAO.getRol(id);
        if (rol != null) 
            response.put("rol", rol);
        else
            throw new EntityNotFoundException("No hay rol con id " + id);
        return response;
    }

    @RequestMapping(value = "/name={name}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getRolByName(@ModelAttribute("name") String name) throws EntityNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        CcloudsRol rol = rolDAO.findRolByName(name);
        if (rol != null) {
            response.put("rol", rol);

        } else {
            throw new EntityNotFoundException("No hay rol con nombre " + name);

        }
        return response;
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> insertRol(
            @RequestBody @Valid CcloudsRol rol, BindingResult result)
            throws UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            throw new UnableToCreateEntityException("Unable to create rol. "
                    + "There are wrong fields.",
                    result.getFieldErrors());
        } else {
            long id = rolDAO.insertRol(rol);
            response.put("id", id);
        }
        return response;
    }

    @RequestMapping(value = "/{Id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> updateRol(
            @ModelAttribute("Id") long id,
            @RequestBody @Valid CcloudsRol rol,
            BindingResult result)
            throws EntityNotFoundException, UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            throw new UnableToCreateEntityException("Unable to update rol.",
                    result.getFieldErrors());
        } else {
            rol.setIdRol(id);
            if (rolDAO.updateRol(rol)) {
                response.put("success", true);
            } else {
                throw new EntityNotFoundException("Can't update. Rol not found.");
            }
        }
        return response;
    }
    
    @RequestMapping(value = "/{id}/options/", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> addOptionsToRol(
            @ModelAttribute("id") long id,
            @RequestBody LinkedList<Long> options,
            BindingResult result)
            throws EntityNotFoundException, UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        LinkedList<Long> ids = new LinkedList<>();
        long new_id;
        if(!rolDAO.exists(id))
            throw new EntityNotFoundException(MESSAGE+id);
        for (long option : options) {
            new_id = rolOptionDAO.insertRolOption(
                    new CcloudsRolOption(option, id));
            if(new_id>0){
                ids.add(new_id);
            }
        }
        response.put("id_list",ids);
        return response;
    }
    
    @RequestMapping(value = "/{id}/options/{id_op}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> deleteOptionsOfRol(
            @ModelAttribute("id") long id,
            @ModelAttribute("id_op") long option,
            BindingResult result)
            throws EntityNotFoundException, UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        LinkedList<Long> ids = new LinkedList<>();
        if(!rolDAO.exists(id))
            throw new EntityNotFoundException(MESSAGE+id);
        response.put("success",rolOptionDAO.deleteRolOption(id, option));
        return response;
    }
    
    @RequestMapping(value = "/{id}/options/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getOptionsOfRol(
            @ModelAttribute("id") long id,
            BindingResult result)
            throws EntityNotFoundException, UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        if(!rolDAO.exists(id))
            throw new EntityNotFoundException(MESSAGE+id);
        response.put("options",rolOptionDAO.getOptions4Rol(id));
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteRol(@ModelAttribute("id") long id) throws
            EntityNotFoundException {
        if (!rolDAO.deleteRol(id)) {
            throw new EntityNotFoundException(
                    "Couldn't delete."+ MESSAGE + id);
        }
    }
    
        private String MESSAGE = "There is no rol with id:";

}
