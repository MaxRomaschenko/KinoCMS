package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.Film;
import com.avada.edu.kinoCMS.model.FilmType;
import com.avada.edu.kinoCMS.model.News;
import com.avada.edu.kinoCMS.servicies.NewsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller()
@RequestMapping("/news")
public class NewsController {

    @Value("${upload.path}")
    private String uploadPath;
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
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
    public String getNews(@ModelAttribute("news") News news,
                          Model model){
        List<News> newsList = newsService.findAll();
        model.addAttribute("newsType",newsList);

        return "UI/news";
    }

}
