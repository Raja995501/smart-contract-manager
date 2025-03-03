package com.scm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Testing {
@GetMapping("/test")
@ResponseBody
    public String test(){
        return "Ram Ram";
    }

}
