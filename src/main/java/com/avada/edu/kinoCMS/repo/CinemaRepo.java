package com.avada.edu.kinoCMS.repo;

import com.avada.edu.kinoCMS.model.Cinema;
import com.avada.edu.kinoCMS.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepo extends JpaRepository<Cinema,Long> {
    List<Cinema> findAll();
    Cinema getById(Long id);
}
