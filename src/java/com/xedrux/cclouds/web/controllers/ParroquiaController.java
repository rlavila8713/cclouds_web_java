package com.xedrux.cclouds.web.controllers;

import com.xedrux.cclouds.web.dao.CityDAO;
import com.xedrux.cclouds.web.dao.CountryDAO;
import com.xedrux.cclouds.web.dao.ParroquiaDAO;
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
@RequestMapping("/admin/parroquia")
public class ParroquiaController {

    @Autowired
    ParroquiaDAO parroquiaDAO;
    @Autowired
    CityDAO cityDAO;
    @Autowired
    ProvinceDAO provinceDAO;
    @Autowired
    CountryDAO countryDAO;

    public void setParroquiaDAO(ParroquiaDAO parroquiaDAO) {
        this.parroquiaDAO = parroquiaDAO;
    }

    public void setCityDAO(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    public void setProvinceDAO(ProvinceDAO provinceDAO) {
        this.provinceDAO = provinceDAO;
    }

    public void setCountryDAO(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getAllParroquias() {
        Collection<CcloudsParroquia> parroquias = parroquiaDAO.getAllParroquias();
        HashMap<String, Object> response = new HashMap<>();
        response.put("parroquias", parroquias);
        return response;
    }

    @RequestMapping(value = "/id={id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getParroquia(@ModelAttribute("id") Long id)
            throws EntityNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        CcloudsParroquia parroquia = parroquiaDAO.getParroquia(id);
        if (parroquia != null) {
            response.put("parroquia", parroquia);
        } else {
            throw new EntityNotFoundException(MESSAGE + id);
        }
        return response;
    }

    @RequestMapping(value = "/detailed/id={id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getParroquiaCityProvinceAndCountry(
            @ModelAttribute("id") Long id)
            throws EntityNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        CcloudsParroquia parroquia = parroquiaDAO.getParroquia(id);
        if (parroquia != null) {
            CcloudsCity city = cityDAO.getCity(parroquia.getIdCity());
            List<CcloudsParroquia> parroquias
                    = parroquiaDAO.getAllParroquiasFrom(city.getIdCity());
            CcloudsProvince province
                    = provinceDAO.getProvince(city.getIdProvince());
            List<CcloudsCity> cities
                    = cityDAO.getAllCitiesFrom(province.getIdProvince());
            CcloudsCountry country
                    = countryDAO.getCountry(province.getIdCountry());
            List<CcloudsProvince> provinces
                    = provinceDAO.getAllProvincesFrom(country.getIdCountry());
            response.put("country", country);
            response.put("province", province);
            response.put("city", city);
            response.put("parroquia", parroquia);
            response.put("parroquias", parroquias);
            response.put("cities", cities);
            response.put("provinces", provinces);
            response.put("countries", countryDAO.getAllCountries());
        } else {
            throw new EntityNotFoundException(MESSAGE + id);
        }
        return response;
    }

    @RequestMapping(value = "/from_city={id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getParroquiasFrom(@ModelAttribute("id") Long city)
            throws EntityNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        List<CcloudsParroquia> parroquias
                = parroquiaDAO.getAllParroquiasFrom(city);
        response.put("parroquias", parroquias);
        return response;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> insertParroquia(
            @RequestBody @Valid CcloudsParroquia parroquia, BindingResult result)
            throws UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            throw new UnableToCreateEntityException("Unable to create parroquia. "
                    + "There are wrong fields.",
                    result.getFieldErrors());
        } else {
            long id = parroquiaDAO.insertParroquia(parroquia);
            response.put("id", id);
        }
        return response;
    }

    @RequestMapping(value = "/{Id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> updateParroquia(
            @ModelAttribute("Id") long id,
            @RequestBody @Valid CcloudsParroquia parroquia,
            BindingResult result)
            throws EntityNotFoundException, UnableToCreateEntityException {
        HashMap<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            throw new UnableToCreateEntityException("Unable to create parroquia."
                    + " There are wrong fields.",
                    result.getFieldErrors());
        } else {
            parroquia.setIdParroquia(id);
            if (parroquiaDAO.updateParroquia(parroquia)) {
                response.put("success", true);
            } else {
                throw new EntityNotFoundException("Parroquia not found.");
            }
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteParroquia(@ModelAttribute("id") long id) throws
            EntityNotFoundException {
        if (!parroquiaDAO.deleteParroquia(id)) {
            throw new EntityNotFoundException(
                    "Couldn't delete. " + MESSAGE + id);
        }
    }

    final String MESSAGE = "No hay parroquia con id: ";
}
