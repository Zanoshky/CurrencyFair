package com.zanoshky.currencyfair.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String greeting() {
        return "greeting";
    }

    @RequestMapping("/index")
    public String getIndex() {
        return "index";
    }

}
