package com.avada.edu.kinoCMS.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/statistics")
public class StatisticsController {

    @GetMapping("/admin")
    public String getStarterPage(){
        return "UI/index";
    }

}
