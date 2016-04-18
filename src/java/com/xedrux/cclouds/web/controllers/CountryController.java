package com.xedrux.cclouds.web.controllers;

import com.xedrux.cclouds.web.dao.CountryDAO;
import com.xedrux.cclouds.web.entities.CcloudsCountry;
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
 * @author Isidro Rodríguez Gamez
 */
@RestController
@RequestMapping("/admin/country")
public class CountryController {

    @Autowired
    CountryDAO countryDAO;

    public void setCountryDAO(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getAllCountries() {
        Collection<CcloudsCountry> countries = countryDAO.getAllCountries();
        HashMap<String, Object> response = new HashMap<>();
        response.put("countries", countries);
        return response;
    }

    @RequestMapping(value = "/id={id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getCountry(@ModelAttribute("id") Long id)
            throws EntityNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        CcloudsCountry country = countryDAO.getCountry(id);
        if (country != null) {
            response.put("country", country);
        } else {
            throw new EntityNotFoundException(MESSAGE + id);
        }
        return response;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> insertCountry(
            @RequestBody @Valid CcloudsCountry country,
            BindingResult result) throws UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            throw new UnableToCreateEntityException("Unable to create country.",
                    result.getFieldErrors());
        } else {
            long id = countryDAO.insertCountry(country);
            response.put("id", id);
        }
        return response;
    }
    
    @RequestMapping(value = "/{Id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> updateCountry(
            @ModelAttribute("Id") long id,
            @RequestBody @Valid CcloudsCountry country,
            BindingResult result)
            throws EntityNotFoundException, UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            throw new UnableToCreateEntityException("Unable to instantiate country.",
                    result.getFieldErrors());
        } else {
            country.setIdCountry(id);
            if (countryDAO.updateCountry(country)) {
                response.put("success", true);
            } else {
                throw new EntityNotFoundException(MESSAGE+id);
            }
        }
        return response;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteCountry(@ModelAttribute("id") long id) throws
            EntityNotFoundException {
        if (!countryDAO.deleteCountry(id)) {
            throw new EntityNotFoundException(
                    "Couldn't delete. " +MESSAGE+ id);
        }
    }
    
    private String MESSAGE = "There is not any country with id: ";

}
