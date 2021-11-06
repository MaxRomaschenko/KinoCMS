package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.Banner;
import com.avada.edu.kinoCMS.servicies.BannerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
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

    @GetMapping
    public String getBanner(
            @ModelAttribute("bannerMainOwl1") Banner bannerMainOwl1,
            @ModelAttribute("bannerMainOwl2") Banner bannerMainOwl2,
            @ModelAttribute("bannerMainOwl3") Banner bannerMainOwl3,
            @ModelAttribute("bannerMainOwl4") Banner bannerMainOwl4,
            @ModelAttribute("bannerMainOwl5") Banner bannerMainOwl5,
            @ModelAttribute("bannerNews1") Banner bannerNews1,
            @ModelAttribute("bannerNews2") Banner bannerNews2,
            @ModelAttribute("bannerNews3") Banner bannerNews3,
            @ModelAttribute("bannerNews4") Banner bannerNews4,
            @ModelAttribute("bannerNews5") Banner bannerNews5,
            @ModelAttribute("bannerBackground") Banner bannerBackground,
            Model model) {
        return "UI/banners";
    }

    @PostMapping("/add/bannerMainOwl")
    public String addBannerMainOwl(@ModelAttribute("bannerMainOwl1") Banner bannerMainOwl1,
                                   @ModelAttribute("bannerMainOwl2") Banner bannerMainOwl2,
                                   @ModelAttribute("bannerMainOwl3") Banner bannerMainOwl3,
                                   @ModelAttribute("bannerMainOwl4") Banner bannerMainOwl4,
                                   @ModelAttribute("bannerMainOwl5") Banner bannerMainOwl5,
                                   @RequestParam(value = "banner", required = false) String[] banners,
                                   @RequestParam("file1") MultipartFile file1,
                                   @RequestParam("file2") MultipartFile file2,
                                   @RequestParam("file3") MultipartFile file3,
                                   @RequestParam("file4") MultipartFile file4,
                                   @RequestParam("file5") MultipartFile file5

    ) throws IOException { //TODO: переделать говнокод
        bannerMainOwl1.setId(1L);
        bannerMainOwl1.setPhoto(file(file1));
        bannerMainOwl1.setUrl(banners[0]);
        bannerMainOwl1.setTextMessage(banners[1]);

        bannerMainOwl2.setId(2L);
        bannerMainOwl2.setPhoto(file(file2));
        bannerMainOwl2.setUrl(banners[2]);
        bannerMainOwl2.setTextMessage(banners[3]);


        bannerMainOwl3.setId(3L);
        bannerMainOwl3.setPhoto(file(file3));
        bannerMainOwl3.setUrl(banners[4]);
        bannerMainOwl3.setTextMessage(banners[5]);

        bannerMainOwl4.setId(4L);
        bannerMainOwl4.setPhoto(file(file4));
        bannerMainOwl4.setUrl(banners[6]);
        bannerMainOwl4.setTextMessage(banners[7]);

        bannerMainOwl5.setId(5L);
        bannerMainOwl5.setPhoto(file(file5));
        bannerMainOwl5.setUrl(banners[8]);
        bannerMainOwl5.setTextMessage(banners[9]);

        if (bannerMainOwl1.getTextMessage() != null) {
            bannerService.saveBanner(bannerMainOwl1);
        }
        if (bannerMainOwl2.getTextMessage() != null) {
            bannerService.saveBanner(bannerMainOwl2);
        }
        if (bannerMainOwl3.getTextMessage() != null) {
            bannerService.saveBanner(bannerMainOwl3);
        }
        if (bannerMainOwl4.getTextMessage() != null) {
            bannerService.saveBanner(bannerMainOwl4);
        }
        if (bannerMainOwl5.getTextMessage() != null) {
            bannerService.saveBanner(bannerMainOwl5);
        }


        return "redirect:/banners";
    }

    @PostMapping("/add/bannerBackground")
    public String addBannerBackground(
            @ModelAttribute("bannerBackground") Banner bannerBackground,
            @RequestParam("background") MultipartFile background
    ) throws IOException {
        bannerBackground.setId(6L);
        if (!bannerBackground.getTrueFalse()) {
            bannerBackground.setPhoto(null);
        } else if (bannerBackground == null) { //TODO: юзлес, не забыть добавить проверку пришёл ли файл
            bannerBackground.setPhoto(file(background));
        } else {
            bannerBackground.setPhoto(file(background));
        }
        bannerService.saveBanner(bannerBackground);

        return "redirect:/banners";
    }

    @PostMapping("/add/bannerNews")
    public String addBannerNews(@ModelAttribute("bannerNews1") Banner bannerNews1,
                                @ModelAttribute("bannerNews2") Banner bannerNews2,
                                @ModelAttribute("bannerNews3") Banner bannerNews3,
                                @ModelAttribute("bannerNews4") Banner bannerNews4,
                                @ModelAttribute("bannerNews5") Banner bannerNews5,
                                @RequestParam(value = "banner", required = false) String[] banners,
                                @RequestParam("file1") MultipartFile file1,
                                @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3,
                                @RequestParam("file4") MultipartFile file4,
                                @RequestParam("file5") MultipartFile file5

    ) throws IOException {
        bannerNews1.setId(7L);
        bannerNews1.setPhoto(file(file1));
        bannerNews1.setUrl(banners[0]);

        bannerNews2.setId(8L);
        bannerNews2.setPhoto(file(file2));
        bannerNews2.setUrl(banners[1]);


        bannerNews3.setId(9L);
        bannerNews3.setPhoto(file(file3));
        bannerNews3.setUrl(banners[2]);


        bannerNews4.setId(10L);
        bannerNews4.setPhoto(file(file4));
        bannerNews4.setUrl(banners[3]);


        bannerNews5.setId(11L);
        bannerNews5.setPhoto(file(file5));
        bannerNews5.setUrl(banners[4]);

        if (bannerNews1.getUrl() != null) {
            bannerService.saveBanner(bannerNews1);
        }
        if (bannerNews2.getUrl() != null) {
            bannerService.saveBanner(bannerNews2);
        }
        if (bannerNews3.getUrl() != null) {
            bannerService.saveBanner(bannerNews3);
        }
        if (bannerNews4.getUrl() != null) {
            bannerService.saveBanner(bannerNews4);
        }
        if (bannerNews5.getUrl() != null) {
            bannerService.saveBanner(bannerNews5);
        }

        return "redirect:/banners";
    }
}
