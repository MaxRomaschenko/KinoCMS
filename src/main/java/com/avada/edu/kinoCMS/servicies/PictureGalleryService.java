package com.avada.edu.kinoCMS.servicies;

import com.avada.edu.kinoCMS.model.PictureGallery;
import com.avada.edu.kinoCMS.repo.PictureGalleryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureGalleryService {
    private final PictureGalleryRepo pictureGalleryRepo;

    public PictureGalleryService(PictureGalleryRepo pictureGalleryRepo) {
        this.pictureGalleryRepo = pictureGalleryRepo;
    }

    public PictureGallery save(PictureGallery pictureGallery){
        return pictureGalleryRepo.save(pictureGallery);
    }

    public List<PictureGallery> findAllByCinemaId(Long id){
        return pictureGalleryRepo.findAllByCinemaId(id);
    }

    public List<PictureGallery> findAllByHallId(Long id){
        return pictureGalleryRepo.findAllByHallId(id);
    }

    public List<PictureGallery> findAllByFilmId(Long id){
        return pictureGalleryRepo.findAllByFilmId(id);
    }

    public List<PictureGallery> findAllByNewsId(Long id){
        return pictureGalleryRepo.findAllByNewsId(id);
    }

    public List<PictureGallery> findAllByStockId(Long id){
        return pictureGalleryRepo.findAllByStockId(id);
    }

    public List<PictureGallery> findAllByPageId(Long id){
        return pictureGalleryRepo.findAllByPageId(id);
    }

    public void delete(PictureGallery pictureGallery){
        pictureGalleryRepo.delete(pictureGallery);
    }

    public void deleteAll(List<PictureGallery> pictureGalleries){
        pictureGalleryRepo.deleteAll(pictureGalleries);
    }

    public void deleteAllByCinemaId(Long id){
        pictureGalleryRepo.deleteAllByCinemaId(id);
    }

    public void deleteAllByHallId(Long id){
        pictureGalleryRepo.deleteAllByHallId(id);
    }

    public void deleteAllByFilmId(Long id){
        pictureGalleryRepo.deleteAllByFilmId(id);
    }

    public void deleteAllByNewsId(Long id){
        pictureGalleryRepo.deleteAllByNewsId(id);
    }

    public void deleteAllByStockId(Long id){
        pictureGalleryRepo.deleteAllByStockId(id);
    }

    public void deleteAllByPageId(Long id){
        pictureGalleryRepo.deleteAllByPageId(id);
    }
}
