package com.retail_service.service;

import com.retail_service.dto.stockDTO.StockRequestDTO;
import com.retail_service.dto.stockDTO.StockResponseDTO;
import com.retail_service.mapper.StockMapper;
import com.retail_service.model.Product;
import com.retail_service.model.Stock;
import com.retail_service.repository.ProductRepository;
import com.retail_service.repository.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final StockMapper mapper;
    private final static Logger log = LoggerFactory.getLogger(StockService.class);

    @Transactional
    public StockResponseDTO createStock(StockRequestDTO request){
        if (stockRepository.existsByProductId(request.getProductId())){
            throw new IllegalArgumentException("The stock for the product has already been added.");
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setQuantity(request.getQuantity());

        Stock savedStock = stockRepository.save(stock);
        log.info("Stock created successfully for product ID: {}", request.getProductId());

        return mapper.toDTO(savedStock);
    }

    public StockResponseDTO getStockByProductId(Long productId){
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(() -> new EntityNotFoundException("The stock for the product don't exists"));

        return mapper.toDTO(stock);
    }
}
