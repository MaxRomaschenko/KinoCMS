package com.avada.edu.kinoCMS.servicies;

import com.avada.edu.kinoCMS.model.FilmType;
import com.avada.edu.kinoCMS.repo.FilmTypeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmTypeService {

    private final FilmTypeRepo filmTypeRepo;

    public FilmTypeService(FilmTypeRepo filmTypeRepo) {
        this.filmTypeRepo = filmTypeRepo;
    }

    public FilmType findById(Long id){
        return filmTypeRepo.getOne(id);
    }

    public List<FilmType> findAll(){
        return filmTypeRepo.findAll();
    }
}
