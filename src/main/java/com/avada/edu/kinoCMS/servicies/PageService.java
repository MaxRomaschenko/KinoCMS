package com.avada.edu.kinoCMS.servicies;

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
    public List<Page> findAllByIs_redacted(Boolean b){
        return pageRepo.findAllByRedacted(b);
    }

    public Page save(Page page){
        return pageRepo.save(page);
    }

    public void deleteById(Long id){
        pageRepo.deleteById(id);
    }
}
