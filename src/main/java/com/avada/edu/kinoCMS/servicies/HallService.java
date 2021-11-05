package com.avada.edu.kinoCMS.servicies;

import com.avada.edu.kinoCMS.model.Banner;
import com.avada.edu.kinoCMS.model.Hall;
import com.avada.edu.kinoCMS.repo.HallRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService {

    private final HallRepo hallRepo;

    public HallService(HallRepo hallRepo) {
        this.hallRepo = hallRepo;
    }

    public Hall save(Hall hall){
        return hallRepo.save(hall);
    }

    public Hall findById(Long id){
        return hallRepo.getOne(id);
    }

    public List<Hall> findAll(){
        return hallRepo.findAll();
    }

    public List<Hall> findAllByCinemaId(Long id){
        return hallRepo.findAllByCinemaId(id);
    }
}
