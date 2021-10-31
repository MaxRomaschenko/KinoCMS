package com.avada.edu.kinoCMS.servicies;

import com.avada.edu.kinoCMS.model.Banner;
import com.avada.edu.kinoCMS.model.Stock;
import com.avada.edu.kinoCMS.repo.StockRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private final StockRepo stockRepo;

    public StockService(StockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    public Stock findById(Long id){
        return stockRepo.getOne(id);
    }

    public List<Stock> findAll(){
        return stockRepo.findAll();
    }
}
