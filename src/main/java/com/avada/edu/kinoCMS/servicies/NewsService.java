package com.avada.edu.kinoCMS.servicies;

import com.avada.edu.kinoCMS.model.Film;
import com.avada.edu.kinoCMS.model.News;
import com.avada.edu.kinoCMS.repo.NewsRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    private final NewsRepo newsRepo;

    public NewsService(NewsRepo newsRepo) {
        this.newsRepo = newsRepo;
    }

    public News findById(Long id){
        return newsRepo.getOne(id);
    }

    public List<News> findAll(){
        return newsRepo.findAll();
    }

    public News save(News news){
        return newsRepo.save(news);
    }

    public void deleteById(Long id){
        newsRepo.deleteById(id);
    }
}
