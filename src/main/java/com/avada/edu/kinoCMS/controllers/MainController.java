package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.Film;
import com.avada.edu.kinoCMS.servicies.FilmService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;
    private final FilmService filmService;

    public MainController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/kino")
    public String getStarterPage(@ModelAttribute("film") Film film,
                                 Model model){
        List<Film> filmList = filmService.findAll();
        model.addAttribute("films",filmList);
        return "UI/main";
    }
}
