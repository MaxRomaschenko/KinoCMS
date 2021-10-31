package com.avada.edu.kinoCMS.repo;

import com.avada.edu.kinoCMS.model.Film;
import com.avada.edu.kinoCMS.model.Seo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeoRepo extends JpaRepository<Seo,Long> {
    List<Seo> findAll();
    Seo getById(Long id);
}
