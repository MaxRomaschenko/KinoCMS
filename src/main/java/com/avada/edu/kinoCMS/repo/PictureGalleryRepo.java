package com.avada.edu.kinoCMS.repo;

import com.avada.edu.kinoCMS.model.PictureGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureGalleryRepo extends JpaRepository<PictureGallery,Long> {
}
