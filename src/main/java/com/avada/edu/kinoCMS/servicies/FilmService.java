package com.avada.edu.kinoCMS.servicies;

import com.avada.edu.kinoCMS.model.Film;
import com.avada.edu.kinoCMS.model.FilmType;
import com.avada.edu.kinoCMS.repo.FilmRepo;
import org.springframework.stereotype.Service;

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
}