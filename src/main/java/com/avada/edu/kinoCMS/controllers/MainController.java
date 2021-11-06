package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.Banner;
import com.avada.edu.kinoCMS.model.Film;
import com.avada.edu.kinoCMS.servicies.BannerService;
import com.avada.edu.kinoCMS.servicies.FilmService;
import com.avada.edu.kinoCMS.servicies.PageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;
    private final FilmService filmService;
    private final BannerService bannerService;
    private final PageService pageService;

    public MainController(FilmService filmService, BannerService bannerService, PageService pageService) {
        this.filmService = filmService;
        this.bannerService = bannerService;
        this.pageService = pageService;
    }

    @GetMapping("/kino")
    public String getStarterPage(@ModelAttribute("film") Film film,
                                 Model model){

        model.addAttribute("SeoText",pageService.findById(1L));

        Banner banner1 = bannerService.findById(1L);
        Banner banner2 = bannerService.findById(2L);
        Banner banner3 = bannerService.findById(3L);
        Banner banner4 = bannerService.findById(4L);
        Banner banner5 = bannerService.findById(5L);

        List<Banner> bannerMainOwl = new ArrayList<>();
        bannerMainOwl.add(banner1);
        bannerMainOwl.add(banner2);
        bannerMainOwl.add(banner3);
        bannerMainOwl.add(banner4);
        bannerMainOwl.add(banner5);
        model.addAttribute("bannerMainOwl",bannerMainOwl);

        Banner background = bannerService.findById(6L);
        model.addAttribute("bannerBackground",background);

        Banner banner7 = bannerService.findById(7L);
        Banner banner8 = bannerService.findById(8L);
        Banner banner9 = bannerService.findById(9L);
        Banner banner10 = bannerService.findById(10L);
        Banner banner11 = bannerService.findById(11L);
        List<Banner> bannerNews = new ArrayList<>();
        bannerNews.add(banner7);
        bannerNews.add(banner8);
        bannerNews.add(banner9);
        bannerNews.add(banner10);
        bannerNews.add(banner11);
        model.addAttribute("bannerNews", bannerNews);

        List<Film> filmList = filmService.findAllByActual(false);
        model.addAttribute("filmsActual",filmList);
        List<Film> filmSoon = filmService.findAllByActual(true);
        model.addAttribute("filmSoon",filmSoon);
        return "UI/main";
    }
}
