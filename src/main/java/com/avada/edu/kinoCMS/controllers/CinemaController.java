package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.*;
import com.avada.edu.kinoCMS.repo.CinemaRepo;
import com.avada.edu.kinoCMS.repo.SeoRepo;
import com.avada.edu.kinoCMS.servicies.CinemaService;
import com.avada.edu.kinoCMS.servicies.HallService;
import com.avada.edu.kinoCMS.servicies.PictureGalleryService;
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
@RequestMapping("/cinemas")
public class CinemaController {

    @Value("${upload.path}")
    private String uploadPath;

    private final CinemaService cinemaService;
    private final HallService hallService;
    private final SeoRepo seoRepo;
    private final CinemaRepo cinemaRepo;
    private final PictureGalleryService pictureGalleryService;

    public CinemaController(CinemaService cinemaService,
                            HallService hallService,
                            SeoRepo seoRepo,
                            CinemaRepo cinemaRepo, PictureGalleryService pictureGalleryService) {
        this.cinemaService = cinemaService;
        this.hallService = hallService;
        this.seoRepo = seoRepo;
        this.cinemaRepo = cinemaRepo;
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

    @PostMapping("/add/admin")
    public String addCinema(@ModelAttribute("cinema") Cinema cinema,
                            @RequestParam("gallery") List<MultipartFile> pictureGalleries,
                            @RequestParam("logo") MultipartFile logo,
                            @RequestParam("banner") MultipartFile banner
    ) throws IOException {
        if(cinema != null){ //TODO: ифы не работют создаётся пустой объект от ModelAttribute

            cinema.setLogo_picture(file(logo));

            cinema.setBanner_picture(file(banner));

            if (cinema.getSeo() != null) {
                seoRepo.save(cinema.getSeo());
            }

            Cinema cinema1 = cinemaService.save(cinema);

            for(MultipartFile multipartFile: pictureGalleries){
                PictureGallery pictureGallery = new PictureGallery();
                pictureGallery.setPicture(file(multipartFile));
                pictureGallery.setCinema(cinema1);
                pictureGalleryService.save(pictureGallery);
            }

        }
        return "redirect:/cinemas/admin";
    }

    @GetMapping("/admin")
    public String getCinema(@ModelAttribute("cinema") Cinema cinema,
                            @ModelAttribute("hall") Hall hall,
                            Model model){
        List<Cinema> cinemas = cinemaService.findAll();
        List<Hall> halls = hallService.findAll();
        model.addAttribute("halls",halls);
        model.addAttribute("cinemas",cinemas);
        return "UI/kinozal";

    }

    @GetMapping("/{id}/edit/admin")
    public String edit(Model model, @PathVariable("id") Long id) {
        List<Hall> halls = hallService.findAll();
        model.addAttribute("halls",halls);
        model.addAttribute("cinema",cinemaService.findById(id));
        return "UI/cinema_edit";
    }

    @PostMapping("/{id}/edit/admin")
    public String update(@PathVariable("id") Long id,@ModelAttribute("cinema") Cinema cinema,
                         @RequestParam("gallery") List<MultipartFile> pictureGalleries,
                         @RequestParam("logo") MultipartFile logo,
                         @RequestParam("banner") MultipartFile banner
    ) throws IOException {

        Cinema cinema1 = cinemaService.findById(id);
        cinema.setId(cinema1.getId());
        cinema.getSeo().setId(cinema1.getSeo().getId());

        cinema.setLogo_picture(file(logo));

        cinema.setBanner_picture(file(banner));

        if (cinema.getSeo() != null) {
            seoRepo.save(cinema.getSeo());
        }

        Cinema cinema2 = cinemaService.save(cinema);

        for(MultipartFile multipartFile: pictureGalleries){
            PictureGallery pictureGallery = new PictureGallery();
            pictureGallery.setPicture(file(multipartFile));
            pictureGallery.setCinema(cinema2);
            pictureGalleryService.save(pictureGallery);
        }

        return "redirect:/cinemas/admin";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cinema", cinemaService.findById(id));
        return "UI/cinema_index";
    }

    @PostMapping("/delete/{id}/admin")
    public String delete(@PathVariable("id") Long id) {
        Cinema cinema = cinemaService.findById(id);
        seoRepo.deleteById(cinema.getSeo().getId());
        cinemaRepo.deleteById(id);//todo: доделать удаление залов
        return "redirect:/cinemas/admin";
    }


}
