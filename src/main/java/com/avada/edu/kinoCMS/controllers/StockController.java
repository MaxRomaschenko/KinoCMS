package com.avada.edu.kinoCMS.controllers;


import com.avada.edu.kinoCMS.model.Banner;
import com.avada.edu.kinoCMS.model.PictureGallery;
import com.avada.edu.kinoCMS.model.Stock;
import com.avada.edu.kinoCMS.servicies.BannerService;
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
import java.util.ArrayList;
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
    private final BannerService bannerService;

    public StockController(SeoService seoService,
                           StockService stockService,
                           PictureGalleryService pictureGalleryService, BannerService bannerService) {
        this.seoService = seoService;
        this.stockService = stockService;
        this.pictureGalleryService = pictureGalleryService;
        this.bannerService = bannerService;
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
        model.addAttribute("gallery",pictureGalleryService.findAllByStockId(id));
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

        int i = 0;
        for(MultipartFile file: pictureGalleries){
            if(!file.isEmpty()) {
                List<PictureGallery> pictureGallery1 = pictureGalleryService.findAllByStockId(id);
                pictureGallery1.get(i).setPicture(file(file));
                pictureGallery1.get(i).setStock(stock_from_db);
                pictureGalleryService.save(pictureGallery1.get(i));
            }
            i++;
        }

        return "redirect:/stock/admin";
    }

    @GetMapping("")
    public String stockMain( Model model) {
        model.addAttribute("stocks", stockService.findAll());

        Banner banner7 = bannerService.findById(7L);
        Banner banner8 = bannerService.findById(8L);
        Banner banner9 = bannerService.findById(9L);
        Banner banner10 = bannerService.findById(10L);
        Banner banner11 = bannerService.findById(11L);
        List<Banner> bannerNews = new ArrayList<>();
        bannerNews.add(banner7);
        bannerNews.add(banner8);
        bannerNews.add(banner9);
        bannerNews.add(banner10);
        bannerNews.add(banner11);
        model.addAttribute("bannerNews", bannerNews);

        return "UI/stock_main";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") Long id
            ,Model model){
        model.addAttribute("stock", stockService.findById(id));
        Banner banner7 = bannerService.findById(7L);
        Banner banner8 = bannerService.findById(8L);
        Banner banner9 = bannerService.findById(9L);
        Banner banner10 = bannerService.findById(10L);
        Banner banner11 = bannerService.findById(11L);
        List<Banner> bannerNews = new ArrayList<>();
        bannerNews.add(banner7);
        bannerNews.add(banner8);
        bannerNews.add(banner9);
        bannerNews.add(banner10);
        bannerNews.add(banner11);
        model.addAttribute("bannerNews", bannerNews);
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
