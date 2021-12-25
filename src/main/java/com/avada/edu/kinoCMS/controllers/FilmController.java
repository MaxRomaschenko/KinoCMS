package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.*;
import com.avada.edu.kinoCMS.repo.FilmRepo;
import com.avada.edu.kinoCMS.repo.SeoRepo;
import com.avada.edu.kinoCMS.servicies.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller()
@RequestMapping("/films")
public class FilmController {

    @Value("${upload.path}")
    private String uploadPath;
    private final SeoRepo seoRepo;
    private final FilmRepo filmRepo;
    private final FilmTypeService filmTypeService;
    private final FilmService filmService;
    private final PictureGalleryService pictureGalleryService;
    private final BannerService bannerService;
    private final CinemaService cinemaService;
    private final PageService pageService;

    public FilmController(SeoRepo seoRepo, FilmRepo filmRepo, FilmTypeService filmTypeService, FilmService filmService, PictureGalleryService pictureGalleryService, BannerService bannerService, CinemaService cinemaService, PageService pageService) {
        this.seoRepo = seoRepo;
        this.filmRepo = filmRepo;
        this.filmTypeService = filmTypeService;
        this.filmService = filmService;
        this.pictureGalleryService = pictureGalleryService;
        this.bannerService = bannerService;
        this.cinemaService = cinemaService;
        this.pageService = pageService;
    }

    private String file(MultipartFile multipartFile) throws IOException {

        File uploadDir = new File(uploadPath);

        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + multipartFile.getOriginalFilename();
        multipartFile.transferTo(new File(uploadPath + "/" + resultFileName));

        return resultFileName;
    }

    @GetMapping("/admin")
    public String getFilm(@ModelAttribute("film")Film film,
                          Model model){
        List<FilmType> filmTypeArrayList =  filmTypeService.findAll();
        model.addAttribute("filmTypeList1",filmTypeArrayList);
        List<Film> filmsActual = filmService.findAllByActual(false);
        model.addAttribute("filmsActual",filmsActual);
        List<Film> filmsSoon = filmService.findAllByActual(true);
        model.addAttribute("filmSoon",filmsSoon);

        return "UI/films";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("film") Film film,
                      @RequestParam("gallery") List<MultipartFile> pictureGalleries,
                      @RequestParam("file") MultipartFile multipartFile
    ) throws IOException {

        film.setMainPicture(file(multipartFile));

        if (film.getSeo() != null) {
            seoRepo.save(film.getSeo());
        }

        Film film1 = filmRepo.save(film);

        for(MultipartFile file: pictureGalleries){
            PictureGallery pictureGallery = new PictureGallery();
            pictureGallery.setPicture(file(file));
            pictureGallery.setFilm(film1);
            pictureGalleryService.save(pictureGallery);
        }

        return "redirect:/films/admin";
    }

    @GetMapping("/{id}/edit/admin")
    public String edit(Model model, @PathVariable("id") Long id) {
        List<FilmType> filmTypeArrayList =  filmTypeService.findAll();
        model.addAttribute("filmTypeList1",filmTypeArrayList);
        model.addAttribute("film",filmService.findById(id));
        model.addAttribute("gallery",pictureGalleryService.findAllByFilmId(id));
        return "UI/film_edit";
    }


    @PostMapping("/{id}/edit/admin")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("film") Film film,
                         @RequestParam("gallery") List<MultipartFile> pictureGalleries,
                         @RequestParam("file") MultipartFile multipartFile
    ) throws IOException {

        Film film1 = filmService.findById(id);

        film.setId(film1.getId());
        film.getSeo().setId(film1.getSeo().getId());

        if(!multipartFile.isEmpty()){
            film.setMainPicture(file(multipartFile));
        }else {
            film.setMainPicture(film1.getMainPicture());
        }

        if (film.getSeo() != null) {
            seoRepo.save(film.getSeo());
        }

        Film film2 = filmRepo.save(film);

        int i = 0;
        for(MultipartFile file: pictureGalleries){
            if(!file.isEmpty()) {
                List<PictureGallery> pictureGallery1 = pictureGalleryService.findAllByFilmId(id);
                pictureGallery1.get(i).setPicture(file(file));
                pictureGallery1.get(i).setFilm(film2);
                pictureGalleryService.save(pictureGallery1.get(i));
            }
            i++;
        }
        return "redirect:/films/admin";
    }

     @PostMapping("/delete/{id}/admin")
    public String delete(@PathVariable("id") Long id) {
        Film film =  filmService.findById(id);
        List<PictureGallery> pictureGalleries = pictureGalleryService.findAllByFilmId(id);
        pictureGalleryService.deleteAll(pictureGalleries);
        seoRepo.deleteById(film.getSeo().getId());
        filmRepo.deleteById(id);
        return "redirect:/films/admin";
    }

    @GetMapping("/{id}")
    public String getFilmIndexPage(@ModelAttribute("film") Film film,
                                   @PathVariable("id") Long id,
                                   Model model) {
        model.addAttribute("cinemas",cinemaService.findAll());

        Film film1 = filmService.findById(id);
        model.addAttribute("testFilm", film1);

        List<PictureGallery> pictureGalleries = pictureGalleryService.findAllByFilmId(id);
        model.addAttribute("pictureGalleries", pictureGalleries);

        List<Film> filmList = filmService.findAll();
        model.addAttribute("films", filmList);
        List<Page> pages = pageService.findAllByIs_active(true);
        model.addAttribute("pages",pages);
        model.addAttribute("bannerBackground", bannerService.findById(6L));
        return "UI/film_index";
    }


}
