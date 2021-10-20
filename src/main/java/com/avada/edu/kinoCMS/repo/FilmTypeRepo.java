package com.avada.edu.kinoCMS.repo;

import com.avada.edu.kinoCMS.model.FilmType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmTypeRepo extends CrudRepository<FilmType,Long> {
}
