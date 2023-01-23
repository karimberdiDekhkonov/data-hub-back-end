package com.datahub.datahub.controller;

import com.datahub.datahub.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @PostMapping
    public HttpEntity<?> addCompany (@RequestParam String name){
        companyService.addCompany(name);

        return ResponseEntity.status();
    }
}
