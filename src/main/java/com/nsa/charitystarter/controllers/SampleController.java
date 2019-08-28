package com.nsa.charitystarter.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @RequestMapping(value = "/sample", method = RequestMethod.GET)
    public String sampleResponse(){
        return "This is a sample response";
    }
}
