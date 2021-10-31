package com.avada.edu.kinoCMS.servicies;

import com.avada.edu.kinoCMS.model.PictureGallery;
import com.avada.edu.kinoCMS.repo.PictureGalleryRepo;
import org.springframework.stereotype.Service;

@Service
public class PictureGalleryService {
    private final PictureGalleryRepo pictureGalleryRepo;

    public PictureGalleryService(PictureGalleryRepo pictureGalleryRepo) {
        this.pictureGalleryRepo = pictureGalleryRepo;
    }

    public PictureGallery save(PictureGallery pictureGallery){
        return pictureGalleryRepo.save(pictureGallery);
    }
}
