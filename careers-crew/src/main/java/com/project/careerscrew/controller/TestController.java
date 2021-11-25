package com.project.careerscrew.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// TODO to be removed
@Controller
public class TestController {

    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "Hello! World";
    }

}
