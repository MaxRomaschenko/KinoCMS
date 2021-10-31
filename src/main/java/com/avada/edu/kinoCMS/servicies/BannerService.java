package com.avada.edu.kinoCMS.servicies;

import com.avada.edu.kinoCMS.model.Banner;
import com.avada.edu.kinoCMS.model.Film;
import com.avada.edu.kinoCMS.repo.BannerRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {

    private final BannerRepo bannerRepo;

    public BannerService(BannerRepo bannerRepo) {
        this.bannerRepo = bannerRepo;
    }

    public Banner saveBanner(Banner banner){
       return bannerRepo.save(banner);
    }

    public Banner findById(Long id){
        return bannerRepo.getOne(id);
    }

    public List<Banner> findAll(){
        return bannerRepo.findAll();
    }
}
