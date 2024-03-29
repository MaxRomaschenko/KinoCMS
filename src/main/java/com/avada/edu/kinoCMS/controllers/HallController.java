package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.Hall;
import com.avada.edu.kinoCMS.model.Page;
import com.avada.edu.kinoCMS.model.PictureGallery;
import com.avada.edu.kinoCMS.repo.SeoRepo;
import com.avada.edu.kinoCMS.servicies.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller()
@RequestMapping
public class HallController {

    @Value("${upload.path}")
    private String uploadPath;
    private final SeoRepo seoRepo;
    private final HallService hallService;
    private final CinemaService cinemaService;
    private final PictureGalleryService pictureGalleryService;
    private final PageService pageService;
    private final BannerService bannerService;

    public HallController(SeoRepo seoRepo,
                          HallService hallService,
                          CinemaService cinemaService,
                          PictureGalleryService pictureGalleryService, PageService pageService, BannerService bannerService) {
        this.seoRepo = seoRepo;
        this.hallService = hallService;
        this.cinemaService = cinemaService;
        this.pictureGalleryService = pictureGalleryService;
        this.pageService = pageService;
        this.bannerService = bannerService;
    }

    private String file(MultipartFile multipartFile) throws IOException {

        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + multipartFile.getOriginalFilename();
        multipartFile.transferTo(new File(uploadPath + "/" + resultFileName));

        return resultFileName;
    }

    @PostMapping("/{id}/hall/admin")
    private String addHall(@PathVariable("id") Long id,
                           @ModelAttribute("hall") Hall hall,
                           @RequestParam("gallery") List<MultipartFile> pictureGalleries,
                           @RequestParam("logo") MultipartFile logo,
                           @RequestParam("banner") MultipartFile banner
    ) throws IOException {
        hall.setId(null);
        hall.setBanner_picture(file(banner));
        hall.setHall_layout_picture(file(logo));
        Date date = new Date();
        hall.setCreated_at(new Timestamp(date.getTime()));
        hall.setCinema(cinemaService.findById(id));

        if (hall.getSeo() != null) {
            seoRepo.save(hall.getSeo());
        }

        Hall hall1 = hallService.save(hall);

        for (MultipartFile multipartFile : pictureGalleries) {
            PictureGallery pictureGallery = new PictureGallery();
            pictureGallery.setPicture(file(multipartFile));
            pictureGallery.setHall(hall1);
            pictureGalleryService.save(pictureGallery);
        }

        return "redirect:/cinemas/"+ id + "/edit/admin";
    }

    @GetMapping("/hall/{id}")
    public String getHallCard(@PathVariable("id") Long id,
                              Model model) {
        model.addAttribute("hall", hallService.findById(id));
        model.addAttribute("gallery",pictureGalleryService.findAllByHallId(id));
        List<Page> pages = pageService.findAllByIs_active(true);
        model.addAttribute("pages",pages);
        model.addAttribute("bannerBackground", bannerService.findById(6L));
        return "UI/hall_card";
    }


}
