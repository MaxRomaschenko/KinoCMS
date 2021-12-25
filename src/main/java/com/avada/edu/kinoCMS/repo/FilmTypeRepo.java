package com.avada.edu.kinoCMS.repo;

import com.avada.edu.kinoCMS.model.Film;
import com.avada.edu.kinoCMS.model.FilmType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmTypeRepo extends JpaRepository<FilmType,Long> {
    List<FilmType> findAll();

}
