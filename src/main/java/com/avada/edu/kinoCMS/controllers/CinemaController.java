package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.*;
import com.avada.edu.kinoCMS.repo.CinemaRepo;
import com.avada.edu.kinoCMS.repo.SeoRepo;
import com.avada.edu.kinoCMS.servicies.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    private final BannerService bannerService;
    private final PageService pageService;

    public CinemaController(CinemaService cinemaService,
                            HallService hallService,
                            SeoRepo seoRepo,
                            CinemaRepo cinemaRepo,
                            PictureGalleryService pictureGalleryService,
                            BannerService bannerService, PageService pageService) {
        this.cinemaService = cinemaService;
        this.hallService = hallService;
        this.seoRepo = seoRepo;
        this.cinemaRepo = cinemaRepo;
        this.pictureGalleryService = pictureGalleryService;
        this.bannerService = bannerService;
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
    public String edit(Model model,
                       @PathVariable("id") Long id,
                       @ModelAttribute("hall") Hall hall) {
        List<Hall> halls = hallService.findAllByCinemaId(id);
        model.addAttribute("halls",halls);
        model.addAttribute("cinema",cinemaService.findById(id));
        model.addAttribute("gallery",pictureGalleryService.findAllByCinemaId(id));
        return "UI/cinema_edit";
    }

    @PostMapping("/{id}/edit/admin")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("cinema") Cinema cinema,
                         @RequestParam("gallery") List<MultipartFile> pictureGalleries,
                         @RequestParam("logo") MultipartFile logo,
                         @RequestParam("banner") MultipartFile banner
    ) throws IOException {

        Cinema cinema1 = cinemaService.findById(id);
        cinema.setId(cinema1.getId());
        cinema.getSeo().setId(cinema1.getSeo().getId());

        if(!logo.isEmpty()){
            cinema.setLogo_picture(file(logo));
        }else{
            cinema.setLogo_picture(cinema1.getLogo_picture());
        }

        if(!logo.isEmpty()){
            cinema.setBanner_picture(file(banner));
        }else{
            cinema.setBanner_picture(cinema1.getBanner_picture());
        }


        if (cinema.getSeo() != null) {
            seoRepo.save(cinema.getSeo());
        }

        Cinema cinema2 = cinemaService.save(cinema);

        int i = 0;
        for(MultipartFile file: pictureGalleries){
            if(!file.isEmpty()) {
                List<PictureGallery> pictureGallery1 = pictureGalleryService.findAllByFilmId(id);
                pictureGallery1.get(i).setPicture(file(file));
                pictureGallery1.get(i).setCinema(cinema2);
                pictureGalleryService.save(pictureGallery1.get(i));
            }
            i++;
        }

        return "redirect:/cinemas/admin";
    }

    @PostMapping("/delete/{id}/admin")
    public String delete(@PathVariable("id") Long id) {
        Cinema cinema = cinemaService.findById(id);
        seoRepo.deleteById(cinema.getSeo().getId());
        List<PictureGallery> pictureGalleries = pictureGalleryService.findAllByCinemaId(id);
        pictureGalleryService.deleteAll(pictureGalleries);
        cinemaRepo.deleteById(id);
        return "redirect:/cinemas/admin";
    }

    @GetMapping()
    public String cinemas_page(Model model){
        List<Cinema> cinemas = cinemaService.findAll();
        model.addAttribute("cinemas",cinemas);

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
        List<Page> pages = pageService.findAllByIs_active(true);
        model.addAttribute("pages",pages);
        model.addAttribute("bannerBackground", bannerService.findById(6L));
        return "UI/cinemas_main";
    }

    @GetMapping("/card/{id}")
    public String cinemaCard(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cinema", cinemaService.findById(id));
        model.addAttribute("gallery",pictureGalleryService.findAllByCinemaId(id));
        List<Page> pages = pageService.findAllByIs_active(true);
        model.addAttribute("pages",pages);
        model.addAttribute("bannerBackground", bannerService.findById(6L));
        return "UI/cinema_card";
    }

}
