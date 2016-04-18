package com.xedrux.cclouds.web.controllers;

import com.xedrux.cclouds.web.dao.AdmModuloDAO;
import com.xedrux.cclouds.web.entities.AdmModulo;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Isidro Rodríguez Gamez
 */
@RestController
@RequestMapping("/admin/admmodulos")
public class AdmModuloController {

    @Autowired
    AdmModuloDAO admModuloDAO;

    public void setAdmModuloDAO(AdmModuloDAO admModuloDAO) {
        this.admModuloDAO = admModuloDAO;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getAllModules() {
        List<AdmModulo> modulos = admModuloDAO.getAllModules();
        AdmModulo tree = appendChildNodes(modulos.get(0), modulos);
        HashMap<String, Object> response = new HashMap<>();
        //response.put("modulos", modulos);
        response.put("tree", tree);
        return response;
    }
    @RequestMapping(value = "/raw/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getListOfModules() {
        List<AdmModulo> modulos = admModuloDAO.getAllModules();
//        AdmModulo tree = appendChildNodes(modulos.get(0), modulos);
        HashMap<String, Object> response = new HashMap<>();
        response.put("modules", modulos);
//        response.put("tree", tree);
        return response;
    }

    public AdmModulo appendChildNodes(AdmModulo node, List<AdmModulo> modulos) {
        List<AdmModulo> ramas = new LinkedList<>();
        for (AdmModulo modulo : modulos) {
            if (Objects.equals(modulo.getIdPadre(), node.getId()) && !Objects.equals(modulo.getId(), modulo.getIdPadre())) {
                ramas.add(appendChildNodes(modulo, modulos));
            }
        }
        node.setText(node.getDescripcion());
        
        if(ramas.isEmpty())
        {
            node.setIcon("glyphicon glyphicon-link");
        }else
        {
            node.setChildren(ramas);
            node.setTags(ramas.isEmpty()?1:ramas.size());
        }       
        return node;
    }
}
