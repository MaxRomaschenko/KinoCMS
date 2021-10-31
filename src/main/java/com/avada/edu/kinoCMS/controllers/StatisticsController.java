package com.avada.edu.kinoCMS.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller()
@RequestMapping("/statistics")
public class StatisticsController {

    @GetMapping("/admin")
    public String getStarterPage(){
        return "old/index";
    }

}
