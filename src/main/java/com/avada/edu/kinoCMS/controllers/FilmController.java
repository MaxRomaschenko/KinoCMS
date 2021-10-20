package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.Film;
import com.avada.edu.kinoCMS.repo.FilmRepo;
import com.avada.edu.kinoCMS.repo.SeoRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FilmController {

    @Value("${upload.path}")
    private String uploadPath;
    private final SeoRepo seoRepo;
    private final FilmRepo filmRepo;

    public FilmController(SeoRepo seoRepo, FilmRepo filmRepo) {
        this.seoRepo = seoRepo;
        this.filmRepo = filmRepo;
    }

    @GetMapping("/film")
    public String getFilm(@ModelAttribute("film")Film film){
        return "films";
    }

    @PostMapping("/film")
    public String add(@ModelAttribute("film") Film film,
                      @RequestParam("file") MultipartFile file
    ) throws IOException {
        if(film != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            film.setMainPicture(resultFileName);
        }
        seoRepo.save(film.getSeo());
        filmRepo.save(film);

        return "films";
    }


}
