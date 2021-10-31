package com.avada.edu.kinoCMS.repo;

import com.avada.edu.kinoCMS.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepo extends JpaRepository<News,Long> {
    List<News> findAll();
}
