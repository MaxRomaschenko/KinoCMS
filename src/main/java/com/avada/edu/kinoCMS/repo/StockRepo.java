package com.avada.edu.kinoCMS.repo;

import com.avada.edu.kinoCMS.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepo extends JpaRepository<Stock,Long> {
    List<Stock> findAll();
}
