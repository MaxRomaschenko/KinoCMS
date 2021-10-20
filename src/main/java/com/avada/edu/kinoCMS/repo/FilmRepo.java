package com.avada.edu.kinoCMS.repo;

import com.avada.edu.kinoCMS.model.Film;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepo extends CrudRepository<Film,Long> {
    List<Film> findAll();
 }
