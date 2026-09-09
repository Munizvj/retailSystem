package com.retail_service.core;

import com.retail_service.domain.Stock;
import com.retail_service.repository.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockDataService {

    private final StockRepository stockRepository;
    public Stock save(Stock stock){
        return stockRepository.save(stock);
    }

    public Stock findByProductId(Long productId){

        return stockRepository.findByProductId(productId)
                .orElseThrow(() -> new EntityNotFoundException("The stock for the product don't exists"));


    }

}
