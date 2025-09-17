package com.financetracker.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Hidden
public class SwaggerController {

    @GetMapping("/")
    public String redirectToSwagger() {
        return "redirect:/swagger-ui/index.html";
    }

    @GetMapping("/docs")
    public String redirectToSwaggerDocs() {
        return "redirect:/swagger-ui/index.html";
    }
}
