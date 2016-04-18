package com.xedrux.cclouds.web.controllers;

import com.xedrux.cclouds.web.dao.CountryDAO;
import com.xedrux.cclouds.web.dao.ProvinceDAO;
import com.xedrux.cclouds.web.entities.CcloudsCity;
import com.xedrux.cclouds.web.entities.CcloudsCountry;
import com.xedrux.cclouds.web.entities.CcloudsParroquia;
import com.xedrux.cclouds.web.entities.CcloudsProvince;
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
 * @author Isidro Rodríguez Gamez
 */
@RestController
@RequestMapping("/admin/province")
public class ProvinceController {

    @Autowired
    ProvinceDAO provinceDAO;
    @Autowired
    CountryDAO countryDAO;

    public void setProvinceDAO(ProvinceDAO provinceDAO) {
        this.provinceDAO = provinceDAO;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getAllProvinces() {
        Collection<CcloudsProvince> provinces = provinceDAO.getAllProvinces();
        HashMap<String, Object> response = new HashMap<>();
        response.put("provinces", provinces);
        return response;
    }

    @RequestMapping(value = "/from_country={id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getProvincesFrom(@ModelAttribute("id") Long id)
            throws EntityNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        List<CcloudsProvince> provinces = provinceDAO.getAllProvincesFrom(id);
        if (provinces != null) {
            response.put("provinces", provinces);

        } else {
            throw new EntityNotFoundException("There is not any country with id: "
                    + id);
        }
        return response;
    }

    @RequestMapping(value = "/id={id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getProvince(@ModelAttribute("id") long id)
            throws EntityNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        CcloudsProvince province = provinceDAO.getProvince(id);
        if (province != null) {
            response.put("province", province);
        } else {
            throw new EntityNotFoundException("Province not found");
        }
        return response;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> insertProvince(
            @RequestBody @Valid CcloudsProvince province, BindingResult result)
            throws UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            throw new UnableToCreateEntityException("Unable to create province. "
                    + "There are wrong fields.",
                    result.getFieldErrors());
        } else {
            long id = provinceDAO.insertProvince(province);
            response.put("id", id);
        }
        return response;
    }

    @RequestMapping(value = "/{Id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> updateProvince(
            @ModelAttribute("Id") long id,
            @RequestBody @Valid CcloudsProvince province,
            BindingResult result)
            throws EntityNotFoundException, UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            throw new UnableToCreateEntityException("Unable to create province."
                    + " There are wrong fields.",
                    result.getFieldErrors());
        } else {
            province.setIdProvince(id);
            if (provinceDAO.updateProvince(province)) {
                response.put("success", true);
            } else {
                throw new EntityNotFoundException("Province not found.");
            }
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteProvince(@ModelAttribute("id") long id) throws
            EntityNotFoundException {
        if (!provinceDAO.deleteProvince(id)) {
            throw new EntityNotFoundException(
                    "Couldn't delete. There is no province with id:" + id);
        }
    }
    
    @RequestMapping(value = "/detailed/id={id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getProvinceAndCountry(
            @ModelAttribute("id") Long id)
            throws EntityNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        CcloudsProvince province = provinceDAO.getProvince(id);
        if (province != null) {
            CcloudsCountry country = countryDAO.getCountry(province.getIdCountry());
            List<CcloudsCountry> countries = countryDAO.getAllCountries();
            
            response.put("country", country);
            response.put("province", province);
            response.put("countries", countries);
        } else {
            throw new EntityNotFoundException(MESSAGE + id);
        }
        return response;
    }
    
    final String MESSAGE = "No hay provincias con id: ";    

}
