package com.avada.edu.kinoCMS.servicies;

import com.avada.edu.kinoCMS.model.Banner;
import com.avada.edu.kinoCMS.model.Page;
import com.avada.edu.kinoCMS.repo.PageRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService {

    private final PageRepo pageRepo;

    public PageService(PageRepo pageRepo) {
        this.pageRepo = pageRepo;
    }

    public Page findById(Long id){
        return pageRepo.getOne(id);
    }

    public List<Page> findAll(){
        return pageRepo.findAll();
    }
}