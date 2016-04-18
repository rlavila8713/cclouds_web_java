/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xedrux.cclouds.web.exceptions;

import java.util.List;
import org.springframework.validation.FieldError;

/**
 *
 * @author Admin
 */
public class UnableToCreateEntityException extends Exception{

    List<FieldError> list;

    public UnableToCreateEntityException(String message, List<FieldError> list) {
        super(message);
        this.list=list;
    }
    
    public List<FieldError> getList() {
        return list;
    }

    public UnableToCreateEntityException(String message) {
        super(message);
    }
}
