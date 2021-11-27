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
            Model model) {
        model.addAttribute("bannerMainOwl1", bannerService.findById(1L));
        model.addAttribute("bannerMainOwl2", bannerService.findById(2L));
        model.addAttribute("bannerMainOwl3", bannerService.findById(3L));
        model.addAttribute("bannerMainOwl4", bannerService.findById(4L));
        model.addAttribute("bannerMainOwl5", bannerService.findById(5L));
        model.addAttribute("bannerBackground", bannerService.findById(6L));
        model.addAttribute("bannerNews1", bannerService.findById(7L));
        model.addAttribute("bannerNews2", bannerService.findById(8L));
        model.addAttribute("bannerNews3", bannerService.findById(9L));
        model.addAttribute("bannerNews4", bannerService.findById(10L));
        model.addAttribute("bannerNews5", bannerService.findById(11L));
        model.addAttribute("banners", bannerService.findAll());
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
        if (!file1.isEmpty()) {
            bannerMainOwl1.setPhoto(file(file1));
        } else {
            bannerMainOwl1.setPhoto(bannerService.findById(1L).getPhoto());
        }
        bannerMainOwl1.setId(1L);
        bannerMainOwl1.setUrl(banners[0]);
        bannerMainOwl1.setTextMessage(banners[1]);
        if (!bannerMainOwl1.getTextMessage().isEmpty()) {
            bannerService.saveBanner(bannerMainOwl1);
        }

        if (!file2.isEmpty()) {
            bannerMainOwl2.setPhoto(file(file2));
        } else {
            bannerMainOwl2.setPhoto(bannerService.findById(2L).getPhoto());
        }
        bannerMainOwl2.setId(2L);
        bannerMainOwl2.setUrl(banners[2]);
        bannerMainOwl2.setTextMessage(banners[3]);
        if (!bannerMainOwl2.getTextMessage().isEmpty()) {
            bannerService.saveBanner(bannerMainOwl2);
        }

        if (!file3.isEmpty()) {
            bannerMainOwl3.setPhoto(file(file3));
        }else {
            bannerMainOwl3.setPhoto(bannerService.findById(3L).getPhoto());
        }
        bannerMainOwl3.setId(3L);
        bannerMainOwl3.setUrl(banners[4]);
        bannerMainOwl3.setTextMessage(banners[5]);
        if (!bannerMainOwl3.getTextMessage().isEmpty()) {
            bannerService.saveBanner(bannerMainOwl3);
        }

        if (!file4.isEmpty()) {
            bannerMainOwl4.setPhoto(file(file4));
        }else {
            bannerMainOwl4.setPhoto(bannerService.findById(4L).getPhoto());
        }
            bannerMainOwl4.setId(4L);
            bannerMainOwl4.setUrl(banners[6]);
            bannerMainOwl4.setTextMessage(banners[7]);
            if (!bannerMainOwl4.getTextMessage().isEmpty()) {
                bannerService.saveBanner(bannerMainOwl4);
            }

        if (!file5.isEmpty()) {
            bannerMainOwl5.setPhoto(file(file5));
        }else {
            bannerMainOwl5.setPhoto(bannerService.findById(5L).getPhoto());
        }
            bannerMainOwl5.setId(5L);
            bannerMainOwl5.setUrl(banners[8]);
            bannerMainOwl5.setTextMessage(banners[9]);
            if (!bannerMainOwl5.getTextMessage().isEmpty()) {
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
        } else if (!background.isEmpty()) {
            bannerBackground.setPhoto(file(background));
        } else {
            bannerBackground.setPhoto(bannerService.findById(6L).getPhoto());
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

        if (!file1.isEmpty()) {
            bannerNews1.setPhoto(file(file1));
        }else {
            bannerNews1.setPhoto(bannerService.findById(7L).getPhoto());
        }
            bannerNews1.setId(7L);
            bannerNews1.setUrl(banners[0]);
            if (!bannerNews1.getUrl().isEmpty()) {
                bannerService.saveBanner(bannerNews1);
            }

        if (!file2.isEmpty()) {
            bannerNews2.setPhoto(file(file2));
        }else {
            bannerNews2.setPhoto(bannerService.findById(8L).getPhoto());
        }
            bannerNews2.setId(8L);
            bannerNews2.setUrl(banners[1]);
            if (!bannerNews2.getUrl().isEmpty()) {
                bannerService.saveBanner(bannerNews2);
            }

        if (!file3.isEmpty()) {
            bannerNews3.setPhoto(file(file3));
        }else {
            bannerNews3.setPhoto(bannerService.findById(9L).getPhoto());
        }
            bannerNews3.setId(9L);
            bannerNews3.setUrl(banners[2]);
            if (!bannerNews3.getUrl().isEmpty()) {
                bannerService.saveBanner(bannerNews3);
            }

        if (!file4.isEmpty()) {
            bannerNews4.setPhoto(file(file4));
        }else {
            bannerNews4.setPhoto(bannerService.findById(10L).getPhoto());
        }
            bannerNews4.setId(10L);
            bannerNews4.setUrl(banners[3]);
            if (!bannerNews4.getUrl().isEmpty()) {
                bannerService.saveBanner(bannerNews4);
            }

        if (!file5.isEmpty()) {
            bannerNews5.setPhoto(file(file5));
        }else {
            bannerNews5.setPhoto(bannerService.findById(11L).getPhoto());
        }
            bannerNews5.setId(11L);
            bannerNews5.setUrl(banners[4]);
            if (!bannerNews5.getUrl().isEmpty()) {
                bannerService.saveBanner(bannerNews5);
            }

        return "redirect:/banners";
    }

    @GetMapping("/her")
    public String help(){
        return "UI/ressilka";
    }
}
