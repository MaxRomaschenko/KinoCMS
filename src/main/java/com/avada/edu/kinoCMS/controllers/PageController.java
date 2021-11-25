package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.News;
import com.avada.edu.kinoCMS.model.Page;
import com.avada.edu.kinoCMS.model.PictureGallery;
import com.avada.edu.kinoCMS.repo.SeoRepo;
import com.avada.edu.kinoCMS.servicies.PageService;
import com.avada.edu.kinoCMS.servicies.PictureGalleryService;
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

@Controller
@RequestMapping("/page")
public class PageController {
    @Value("${upload.path}")
    private String uploadPath;
    private final SeoRepo seoRepo;
    private final PageService pageService;
    private final PictureGalleryService pictureGalleryService;

    public PageController(SeoRepo seoRepo, PageService pageService, PictureGalleryService pictureGalleryService) {
        this.seoRepo = seoRepo;
        this.pageService = pageService;
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
    public String getPages(@ModelAttribute("page") Page page, Model model){
        List<Page> pagesList = pageService.findAllByIs_redacted(true);
        model.addAttribute("pages",pagesList);
        model.addAttribute("mainPage",pageService.findById(1L));//TODO::переделать
        return "UI/page";
    }

    @PostMapping("/add/admin")
    public String add(@ModelAttribute("page") Page page,
                      @RequestParam("gallery") List<MultipartFile> pictureGalleries,
                      @RequestParam("file") MultipartFile mainPicture
    ) throws IOException{
        page.setBanner_url(file(mainPicture));
        Date date = new Date();
        page.setCreated_at(new Timestamp(date.getTime()));
        page.setRedacted(true);

        if(page.getSeo() != null){
            seoRepo.save(page.getSeo());
        }

        Page page1 = pageService.save(page);

        for(MultipartFile file: pictureGalleries){
            PictureGallery pictureGallery = new PictureGallery();
            pictureGallery.setPicture(file(file));
            pictureGallery.setPage(page1);
            pictureGalleryService.save(pictureGallery);
        }

        return "redirect:/page/admin";

    }

    @GetMapping("/{id}/edit/admin")
    public String edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("page",pageService.findById(id));
        return "UI/page_edit";
    }

    @PostMapping("/{id}/edit/admin")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("page") Page page,
                         @RequestParam("gallery") List<MultipartFile> pictureGalleries,
                         @RequestParam("logo") MultipartFile mainPicture
    ) throws IOException{
        Page page1 = pageService.findById(id);
        page.setId(page1.getId());
        page.getSeo().setId(page1.getSeo().getId());

        page.setRedacted(true);

        Date date = new Date();
        page.setCreated_at(new Timestamp(date.getTime()));

        if(!mainPicture.isEmpty()){
            page.setBanner_url(file(mainPicture));
        }else {
            page.setBanner_url(page1.getBanner_url());
        }

        if(page.getSeo() != null){
            seoRepo.save(page.getSeo());
        }

        Page page2 = pageService.save(page);

        for(MultipartFile file: pictureGalleries){
            PictureGallery pictureGallery = new PictureGallery();
            pictureGallery.setPicture(file(file));
            pictureGallery.setPage(page2);
            pictureGalleryService.save(pictureGallery);
        }
        return "redirect:/page/admin";
    }

    @GetMapping("/{id}/mainPage/edit/admin")
    public String editMainPage(Model model, @PathVariable("id") Long id){
        model.addAttribute("page",pageService.findById(id));
        return "UI/mainPage_edit";
    }

    @PostMapping("/{id}/mainPage/edit/admin")
    public String updateMainPage(@PathVariable("id") Long id,
                         @ModelAttribute("page") Page page
    ) throws IOException{
        Page page1 = pageService.findById(id);
        page.setId(page1.getId());
        page.getSeo().setId(page1.getSeo().getId());
        page.setTitle(page1.getTitle());
        page.setRedacted(false);


        Date date = new Date();
        page.setCreated_at(new Timestamp(date.getTime()));

        if(page.getSeo() != null){
            seoRepo.save(page.getSeo());
        }

        pageService.save(page);


        return "redirect:/page/admin";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") Long id, Model model) {
        model.addAttribute("page", pageService.findById(id));
        model.addAttribute("gallery", pictureGalleryService.findAllByPageId(id));
        return "UI/page_index";
    }

    @PostMapping("/delete/{id}/admin")
    public String delete(@PathVariable("id") Long id) {
        Page page = pageService.findById(id);
        List<PictureGallery> pictureGalleries = pictureGalleryService.findAllByPageId(id);
        pictureGalleryService.deleteAll(pictureGalleries);
        seoRepo.deleteById(page.getSeo().getId());
        pageService.deleteById(id);
        return "redirect:/page/admin";
    }
}
