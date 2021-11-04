package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.Film;
import com.avada.edu.kinoCMS.model.FilmType;
import com.avada.edu.kinoCMS.model.PictureGallery;
import com.avada.edu.kinoCMS.model.Seo;
import com.avada.edu.kinoCMS.repo.FilmRepo;
import com.avada.edu.kinoCMS.repo.FilmTypeRepo;
import com.avada.edu.kinoCMS.repo.SeoRepo;
import com.avada.edu.kinoCMS.servicies.FilmService;
import com.avada.edu.kinoCMS.servicies.FilmTypeService;
import com.avada.edu.kinoCMS.servicies.PictureGalleryService;
import com.avada.edu.kinoCMS.servicies.SeoService;
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
    private final SeoService seoService;
    private final FilmTypeRepo filmTypeRepo;
    private final FilmTypeService filmTypeService;
    private final FilmService filmService;
    private final PictureGalleryService pictureGalleryService;

    public FilmController(SeoRepo seoRepo, FilmRepo filmRepo, SeoService seoService, FilmTypeRepo filmTypeRepo, FilmTypeService filmTypeService, FilmService filmService, PictureGalleryService pictureGalleryService) {
        this.seoRepo = seoRepo;
        this.filmRepo = filmRepo;
        this.seoService = seoService;
        this.filmTypeRepo = filmTypeRepo;
        this.filmTypeService = filmTypeService;
        this.filmService = filmService;
        this.pictureGalleryService = pictureGalleryService;
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
        List<Film> films = filmService.findAll();
        model.addAttribute("films",films);

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
        return "UI/film_edit";
    }


    @PostMapping("/{id}/edit/admin")
    public String update(@PathVariable("id") Long id,@ModelAttribute("film") Film film,
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

        for(MultipartFile file: pictureGalleries){
            PictureGallery pictureGallery = new PictureGallery();
            pictureGallery.setPicture(file(file));
            pictureGallery.setFilm(film2);
            pictureGalleryService.save(pictureGallery);
        }
        return "redirect:/films/admin";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") Long id, Model model) {
        model.addAttribute("film", filmService.findById(id));
        return "UI/film_index";
    }

    @PostMapping("/delete/{id}/admin")
    public String delete(@PathVariable("id") Long id) {
        Film film =  filmService.findById(id);
        seoRepo.deleteById(film.getSeo().getId());
        filmRepo.deleteById(id);
        return "redirect:/films/admin";
    }


}
