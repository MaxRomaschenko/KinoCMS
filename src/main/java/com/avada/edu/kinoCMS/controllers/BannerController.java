package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.Banner;
import com.avada.edu.kinoCMS.servicies.BannerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/banners")
public class BannerController {

    @Value("${upload.path}")
    private String uploadPath;

    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping
    public String getBanner(@ModelAttribute("banner")Banner banner){
        return "banners";
    }

    @PostMapping
    public String add(@ModelAttribute("banner")Banner banner,
                      @RequestParam("file") MultipartFile file) throws IOException {
        if(banner != null){
            File uploadDir = new File(uploadPath);

            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            banner.setPhoto(resultFileName);
        }
        bannerService.saveBanner(banner);
        return "banners";
    }
}
