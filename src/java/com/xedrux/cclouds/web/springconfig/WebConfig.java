/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xedrux.cclouds.web.springconfig;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.CustomValidatorBean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author Admin
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        Boolean present = false;
        for (HttpMessageConverter<?> conv : converters) {
            if (conv instanceof MappingJackson2HttpMessageConverter) {
                present = true;
                System.out.println("present");
                MappingJackson2HttpMessageConverter c = (MappingJackson2HttpMessageConverter) conv;
                configureObjectMapper(c.getObjectMapper());
            }
        }
        if (!present) {
            System.out.println("not present");
            final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            final ObjectMapper objectMapper = new ObjectMapper();
            configureObjectMapper(objectMapper);
            converter.setObjectMapper(objectMapper);
            converters.add(converter);
        }

        super.configureMessageConverters(converters);

    }

    private void configureObjectMapper(ObjectMapper objectMapper) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy/MM/dd"));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

}
