package com.xedrux.cclouds.web.controllers;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Isidro Rodríguez Gamez
 */
@Controller
public class LoginController {
    
    @RequestMapping({"/mylogin"})
    @ResponseStatus(HttpStatus.MULTI_STATUS)
    public String showLogin(Map<String, Object> model){
        return "login";
    }
    
}
