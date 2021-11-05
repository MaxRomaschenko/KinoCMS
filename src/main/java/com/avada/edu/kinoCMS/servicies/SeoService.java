package com.avada.edu.kinoCMS.servicies;

import com.avada.edu.kinoCMS.model.Seo;
import com.avada.edu.kinoCMS.repo.SeoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeoService {
    private final SeoRepo seoRepo;

    public SeoService(SeoRepo seoRepo) {
        this.seoRepo = seoRepo;
    }

    public Seo findById(Long id){
        return seoRepo.getById(id);
    }

    public List<Seo> findAll(){
        return seoRepo.findAll();
    }

    public Seo save(Seo seo) {
        return seoRepo.save(seo);
    }
    public void deleteById(Long id){
        seoRepo.deleteById(id);
    }

}
