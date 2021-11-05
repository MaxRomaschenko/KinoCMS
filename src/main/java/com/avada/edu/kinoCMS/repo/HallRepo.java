package com.avada.edu.kinoCMS.repo;

import com.avada.edu.kinoCMS.model.Cinema;
import com.avada.edu.kinoCMS.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallRepo extends JpaRepository<Hall,Long>{
    List<Hall> findAll();
    List<Hall> findAllByCinemaId(Long id);
}



