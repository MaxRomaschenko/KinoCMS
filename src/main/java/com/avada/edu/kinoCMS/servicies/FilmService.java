package com.avada.edu.kinoCMS.servicies;

import com.avada.edu.kinoCMS.model.Film;
import com.avada.edu.kinoCMS.model.FilmType;
import com.avada.edu.kinoCMS.repo.FilmRepo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FilmService {

    private final FilmRepo filmRepo;

    public FilmService(FilmRepo filmRepo) {
        this.filmRepo = filmRepo;
    }

    public Film findById(Long id){
        return filmRepo.getById(id);
    }

    public List<Film> findAll(){
        return filmRepo.findAll();
    }

    public List<Film> findAllByActual(Boolean b){
        return filmRepo.findAllByActual(b);
    }
}
