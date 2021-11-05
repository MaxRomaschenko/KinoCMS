package com.avada.edu.kinoCMS.repo;

import com.avada.edu.kinoCMS.model.Cinema;
import com.avada.edu.kinoCMS.model.PictureGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureGalleryRepo extends JpaRepository<PictureGallery,Long> {
    List<PictureGallery> findAllByCinemaId(Long id);
    List<PictureGallery> findAllByHallId(Long id);
    List<PictureGallery> findAllByFilmId(Long id);
    List<PictureGallery> findAllByNewsId(Long id);
    List<PictureGallery> findAllByStockId(Long id);
    List<PictureGallery> findAllByPageId(Long id);
    void deleteAllByCinemaId(Long id);
    void deleteAllByHallId(Long id);
    void deleteAllByFilmId(Long id);
    void deleteAllByNewsId(Long id);
    void deleteAllByStockId(Long id);
    void deleteAllByPageId(Long id);
}
