package com.example.project.direction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FormController {

    @GetMapping("/")
    public String main() {
        return "main";
    }
}
