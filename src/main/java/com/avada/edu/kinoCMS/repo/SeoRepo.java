package com.avada.edu.kinoCMS.repo;

import com.avada.edu.kinoCMS.model.Seo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeoRepo extends CrudRepository<Seo,Long> {
    List<Seo> findAll();
}
