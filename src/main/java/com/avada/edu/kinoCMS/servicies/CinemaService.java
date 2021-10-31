package com.avada.edu.kinoCMS.servicies;

import com.avada.edu.kinoCMS.model.Banner;
import com.avada.edu.kinoCMS.model.Cinema;
import com.avada.edu.kinoCMS.repo.CinemaRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService {

    private final CinemaRepo cinemaRepo;

    public CinemaService(CinemaRepo cinemaRepo) {
        this.cinemaRepo = cinemaRepo;
    }

    public Cinema save(Cinema cinema){
        return cinemaRepo.save(cinema);
    }

    public Cinema findById(Long id){
        return cinemaRepo.getOne(id);
    }

    public List<Cinema> findAll(){
        return cinemaRepo.findAll();
    }
}
