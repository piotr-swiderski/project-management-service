package com.managementservice.projectmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    public static final String INDEX_PAGE = "index";


    @GetMapping("/")
    public String getIndexPage() {
        return INDEX_PAGE;
    }

}
