package com.avada.edu.kinoCMS.controllers;


import com.avada.edu.kinoCMS.model.PictureGallery;
import com.avada.edu.kinoCMS.model.Stock;
import com.avada.edu.kinoCMS.servicies.PictureGalleryService;
import com.avada.edu.kinoCMS.servicies.SeoService;
import com.avada.edu.kinoCMS.servicies.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller()
@RequestMapping("/stock")
public class StockController {

    @Value("${upload.path}")
    private String uploadPath;
    private final SeoService seoService;
    private final StockService stockService;
    private final PictureGalleryService pictureGalleryService;

    public StockController(SeoService seoService,
                           StockService stockService,
                           PictureGalleryService pictureGalleryService) {
        this.seoService = seoService;
        this.stockService = stockService;
        this.pictureGalleryService = pictureGalleryService;
    }

    private String file(MultipartFile multipartFile) throws IOException {

        File uploadDir = new File(uploadPath);

        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + multipartFile.getOriginalFilename();
        multipartFile.transferTo(new File(uploadPath + "/" + resultFileName));

        return resultFileName;
    }


    @GetMapping("/admin")
    public String getStocks(@ModelAttribute("stock") Stock stock,
                          Model model){
        List<Stock> stocks = stockService.findAll();
        model.addAttribute("stocks",stocks);

        return "UI/stock";
    }

    @PostMapping("/add/admin")
    public String addStock(@ModelAttribute("stock")Stock stock,
                           @RequestParam("gallery") List<MultipartFile> pictureGalleries,
                           @RequestParam("logo") MultipartFile logo
    ) throws IOException{
        stock.setMain_picture(file(logo));
        stock.setIs_active(true);
        Date date = new Date();
        stock.setPublication_date(new Timestamp(date.getTime()));
        if(stock.getSeo() != null){
            seoService.save(stock.getSeo());
        }

        Stock stock_from_db = stockService.save(stock);

        for (MultipartFile file : pictureGalleries){
            PictureGallery pictureGallery = new PictureGallery();
            pictureGallery.setPicture(file(file));
            pictureGallery.setStock(stock_from_db);
            pictureGalleryService.save(pictureGallery);
        }
        return "redirect:/stock/admin";
    }

    @GetMapping("/{id}/edit/admin")
    public String edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("stock", stockService.findById(id));
        return "UI/stock_edit";
    }

    @PostMapping("/{id}/edit/admin")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("stock") Stock stock,
                         @RequestParam("gallery") List<MultipartFile> pictureGalleries,
                         @RequestParam("logo") MultipartFile mainPicture
    ) throws IOException{

        Stock stock_by_id = stockService.findById(id);
        stock.setId(stock_by_id.getId());
        stock.getSeo().setId(stock_by_id.getSeo().getId());

        stock.setIs_active(true);
        Date date = new Date();
        stock.setPublication_date(new Timestamp(date.getTime()));

        if(!mainPicture.isEmpty()){
            stock.setMain_picture(file(mainPicture));
        }else {
            stock.setMain_picture(stock_by_id.getMain_picture());
        }

        if(stock.getSeo() != null){
            seoService.save(stock.getSeo());
        }

        Stock stock_from_db = stockService.save(stock);

        for(MultipartFile file: pictureGalleries){
            PictureGallery pictureGallery = new PictureGallery();
            pictureGallery.setPicture(file(file));
            pictureGallery.setStock(stock_from_db);
            pictureGalleryService.save(pictureGallery);
        }

        return "redirect:/stock/admin";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") Long id, Model model) {
        model.addAttribute("stock", stockService.findById(id));
        return "UI/stock_index";
    }

    @PostMapping("/delete/{id}/admin")
    public String delete(@PathVariable("id") Long id) {
        Stock stock =  stockService.findById(id);
        List<PictureGallery> pictureGalleries = pictureGalleryService.findAllByStockId(id);
        pictureGalleryService.deleteAll(pictureGalleries);
        seoService.deleteById(stock.getSeo().getId());
        stockService.deleteById(id);
        return "redirect:/stock/admin";
    }
}
