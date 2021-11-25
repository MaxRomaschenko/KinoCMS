package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.Film;
import com.avada.edu.kinoCMS.model.News;
import com.avada.edu.kinoCMS.model.PictureGallery;
import com.avada.edu.kinoCMS.repo.SeoRepo;
import com.avada.edu.kinoCMS.servicies.NewsService;
import com.avada.edu.kinoCMS.servicies.PictureGalleryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller()
@RequestMapping("/news")
public class NewsController {

    @Value("${upload.path}")
    private String uploadPath;
    private final NewsService newsService;
    private final PictureGalleryService pictureGalleryService;
    private final SeoRepo seoRepo;

    public NewsController(NewsService newsService, PictureGalleryService pictureGalleryService, SeoRepo seoRepo) {
        this.newsService = newsService;
        this.pictureGalleryService = pictureGalleryService;
        this.seoRepo = seoRepo;
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
    public String getNews(@ModelAttribute("newsO") News news,
                          Model model){
        List<News> newsList = newsService.findAll();
        model.addAttribute("news",newsList);

        return "UI/news";
    }

    @PostMapping("/add/admin")
    public String add(@ModelAttribute("news") News news,
                      @RequestParam("gallery") List<MultipartFile> pictureGalleries,
                      @RequestParam("logo") MultipartFile mainPicture
    ) throws IOException{
        news.setMain_picture(file(mainPicture));
        news.setIs_active(true);
        Date date = new Date();
        news.setCreated_at(new Timestamp(date.getTime()));
        if(news.getSeo() != null){
            seoRepo.save(news.getSeo());
        }

        News news1 = newsService.save(news);

        for(MultipartFile file: pictureGalleries){
            PictureGallery pictureGallery = new PictureGallery();
            pictureGallery.setPicture(file(file));
            pictureGallery.setNews(news1);
            pictureGalleryService.save(pictureGallery);
        }

        return "redirect:/news/admin";
    }

    @GetMapping("/{id}/edit/admin")
    public String edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("news",newsService.findById(id));
        return "UI/news_edit";
    }

    @PostMapping("/{id}/edit/admin")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("news") News news,
                         @RequestParam("gallery") List<MultipartFile> pictureGalleries,
                         @RequestParam("logo") MultipartFile mainPicture
    ) throws IOException{

        News news1 = newsService.findById(id);

        news.setId(news1.getId());
        news.getSeo().setId(news1.getSeo().getId());

        news.setIs_active(true);
        Date date = new Date();
        news.setCreated_at(new Timestamp(date.getTime()));

        if(!mainPicture.isEmpty()){
            news.setMain_picture(file(mainPicture));
        }else {
            news.setMain_picture(news1.getMain_picture());
        }

        if(news.getSeo() != null){
            seoRepo.save(news.getSeo());
        }

        News news2 = newsService.save(news);

        for(MultipartFile file: pictureGalleries){
            PictureGallery pictureGallery = new PictureGallery();
            pictureGallery.setPicture(file(file));
            pictureGallery.setNews(news2);
            pictureGalleryService.save(pictureGallery);
        }

        return "redirect:/news/admin";

    }

    @GetMapping()
    public String newsMain(Model model){
        model.addAttribute("news",newsService.findAll());
        return "UI/news_main";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") Long id, Model model) {
        model.addAttribute("news", newsService.findById(id));
        return "UI/news_index";
    }

    @PostMapping("/delete/{id}/admin")
    public String delete(@PathVariable("id") Long id) {
        News news =  newsService.findById(id);
        List<PictureGallery> pictureGalleries = pictureGalleryService.findAllByNewsId(id);
        pictureGalleryService.deleteAll(pictureGalleries);
        seoRepo.deleteById(news.getSeo().getId());
        newsService.deleteById(id);
        return "redirect:/news/admin";
    }
}
