package com.xedrux.cclouds.web.controllers;

import com.xedrux.cclouds.web.dao.LogDAO;
import com.xedrux.cclouds.web.entities.CcloudsLogs;
import java.util.Collection;
import java.util.HashMap;
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
@RequestMapping("/admin/logs")
public class LogsController {
    @Autowired
    LogDAO logDAO;

    public void setLogDAO(LogDAO logDAO) {
        this.logDAO = logDAO;
    }
//    @RequestMapping(value = "/", method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<HashMap<String, Object>> insertLog(@Valid CcloudsLogs log,
//            BindingResult result) throws UnableToCreateEntityException  {
//        HashMap<String, Object> response = new HashMap<>();
//        if (result.hasErrors()) {
//            throw new UnableToCreateEntityException("Unable to insert log.",
//                    result.getFieldErrors());
//        } else {
//            long id = logDAO.insertLog(log);
//            if(id<0){
//                response.put("error", "something went wrong");
//                return new ResponseEntity<>(response,HttpStatus.CONFLICT);
//            }else
//                response.put("Id", id);
//        }
//        return new ResponseEntity<>(response,HttpStatus.CREATED);
//    }
    @RequestMapping(value = "/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getAllLogs() {
        Collection<CcloudsLogs> modules = logDAO.getAllogs();
        HashMap<String, Object> response = new HashMap<>();
        response.put("logs", modules);
        return response;
    }
}
