package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.Banner;
import com.avada.edu.kinoCMS.model.Film;
import com.avada.edu.kinoCMS.model.Page;
import com.avada.edu.kinoCMS.servicies.*;
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


    private final FilmService filmService;
    private final BannerService bannerService;
    private final PageService pageService;
    private final CinemaService cinemaService;
    private final PictureGalleryService pictureGalleryService;

    public MainController(FilmService filmService, BannerService bannerService, PageService pageService, CinemaService cinemaService, PictureGalleryService pictureGalleryService) {
        this.filmService = filmService;
        this.bannerService = bannerService;
        this.pageService = pageService;
        this.cinemaService = cinemaService;
        this.pictureGalleryService = pictureGalleryService;
    }

    @GetMapping("/kino")
    public String getStarterPage(@ModelAttribute("film") Film film,
                                 Model model) {

        model.addAttribute("SeoText", pageService.findById(1L));

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
        model.addAttribute("bannerMainOwl", bannerMainOwl);

        Banner background = bannerService.findById(6L);
        model.addAttribute("bannerBackground", background);

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
        model.addAttribute("filmsActual", filmList);
        List<Film> filmSoon = filmService.findAllByActual(true);
        model.addAttribute("filmSoon", filmSoon);

        List<Page> pages = pageService.findAllByIs_active(true);
        model.addAttribute("pages",pages);
        return "UI/main";
    }

    @GetMapping("/afisha")
    public String getAfishaPage(@ModelAttribute("film") Film film,
                                Model model) {
        Banner background = bannerService.findById(6L);
        model.addAttribute("bannerBackground", background);

        List<Film> filmList = filmService.findAllByActual(false);
        model.addAttribute("films", filmList);
        List<Page> pages = pageService.findAllByIs_active(true);
        model.addAttribute("pages",pages);
        return "UI/afisha";
    }

    @GetMapping("/soon")
    public String getSoonPage(@ModelAttribute("film") Film film,
                              Model model) {
        Banner background = bannerService.findById(6L);
        model.addAttribute("bannerBackground", background);

        List<Film> filmList = filmService.findAllByActual(true);
        model.addAttribute("films", filmList);
        List<Page> pages = pageService.findAllByIs_active(true);
        model.addAttribute("pages",pages);
        return "UI/skoro";
    }

    @GetMapping("/timetable")
    private String getTimetable(Model model){
        model.addAttribute("bannerBackground", bannerService.findById(6L));
        model.addAttribute("cinemas",cinemaService.findAll());
        model.addAttribute("films",filmService.findAll());
        List<Page> pages = pageService.findAllByIs_active(true);
        model.addAttribute("pages",pages);
        return "UI/timetable";
    }

    @GetMapping("/cafe")
    private String getCafe(Model model){
        model.addAttribute("bannerBackground", bannerService.findById(6L));
        List<Page> pages = pageService.findAllByIs_active(true);
        model.addAttribute("pages",pages);
        model.addAttribute("pag",pageService.findById(2L));
        return "UI/cafe";
    }

    @GetMapping("/contacts")
    private String getContacts(Model model){
        model.addAttribute("bannerBackground", bannerService.findById(6L));
        List<Page> pages = pageService.findAllByIs_active(true);
        model.addAttribute("pages",pages);
        Page page = pageService.findById(3L);
        model.addAttribute("pag",page);

        String googMaps = "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2743.018773809206!2d"+ page.getTelephone_second() + "!3d" + page.getTelephone_first() + "!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x40c634a75a0d09e3%3A0xe4cb19d654428aad!2z0JfQvtC70L7RgtC-0Lkg0JTRjtC6!5e0!3m2!1sru!2sua!4v1638869877490!5m2!1sru!2sua";
        model.addAttribute("googMaps",googMaps);
        return "UI/contacts_index";
    }


}
