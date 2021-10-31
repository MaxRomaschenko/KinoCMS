package com.avada.edu.kinoCMS.repo;

import com.avada.edu.kinoCMS.model.Banner;
import com.avada.edu.kinoCMS.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepo extends JpaRepository<Banner,Long> {
    List<Banner> findAll();
}
