package com.xedrux.cclouds.web.controllers;

import com.xedrux.cclouds.web.dao.SexDAO;
import com.xedrux.cclouds.web.entities.CcloudsSex;
import com.xedrux.cclouds.web.exceptions.EntityNotFoundException;
import com.xedrux.cclouds.web.exceptions.UnableToCreateEntityException;
import java.util.Collection;
import java.util.HashMap;
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
 * @author Isidro
 */
@RestController
@RequestMapping("/admin/sex")
public class SexController {
    @Autowired
    SexDAO sexDAO;

    public void setSexDAO(SexDAO DAO) {
        this.sexDAO = DAO;
    }

    
    @RequestMapping(value = "/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getAllSexs() {
        Collection<CcloudsSex> sexes = sexDAO.getAllSexs();
        HashMap<String, Object> response = new HashMap<>();
        response.put("sexes", sexes);
        return response;
    }

    @RequestMapping(value = "/id={id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getSex(@ModelAttribute("id") Long id) throws EntityNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        CcloudsSex sex = sexDAO.getSex(id);
        if (sex != null) 
            response.put("sex", sex);
        else
            throw new EntityNotFoundException("No hay sexo con id " + id);
        return response;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> insertSex(
            @RequestBody @Valid CcloudsSex sex, BindingResult result)
            throws UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            throw new UnableToCreateEntityException("Unable to create sex. "
                    + "There are wrong fields.",
                    result.getFieldErrors());
        } else {
            long id = sexDAO.insertSex(sex);
            response.put("id", id);
        }
        return response;
    }

    @RequestMapping(value = "/{Id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> updateSex(
            @ModelAttribute("Id") long id,
            @RequestBody @Valid CcloudsSex sex,
            BindingResult result)
            throws EntityNotFoundException, UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            throw new UnableToCreateEntityException("Unable to update sex.",
                    result.getFieldErrors());
        } else {
            sex.setIdSex(id);
            if (sexDAO.updateSex(sex)) {
                response.put("success", true);
            } else {
                throw new EntityNotFoundException("Can't update. Sex not found.");
            }
        }
        return response;
    }
     
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteSex(@ModelAttribute("id") long id) throws
            EntityNotFoundException {
        if (!sexDAO.deleteSex(id)) {
            throw new EntityNotFoundException(
                    "Couldn't delete."+ MESSAGE + id);
        }
    }
    
        private String MESSAGE = "There is no sex with id: ";

}
