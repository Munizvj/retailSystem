package com.retail_service.service;

import com.retail_service.core.ProductDataService;
import com.retail_service.core.StockDataService;
import com.retail_service.dto.stockDTO.StockRequestDTO;
import com.retail_service.dto.stockDTO.StockResponseDTO;
import com.retail_service.mapper.StockMapper;
import com.retail_service.domain.Product;
import com.retail_service.domain.Stock;
import com.retail_service.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final StockDataService stockDataService;
    private final ProductDataService productDataService;
    private final StockMapper mapper;
    private final static Logger log = LoggerFactory.getLogger(StockService.class);

    @Transactional
    public StockResponseDTO createStock(StockRequestDTO request){
        if (stockRepository.existsByProductId(request.getProductId())){
            throw new IllegalArgumentException("The stock for the product has already been added.");
        }

        Product product = productDataService.findProductById(request.getProductId());

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setQuantity(request.getQuantity());

        Stock savedStock = stockRepository.save(stock);
        log.info("Stock created successfully for product ID: {}", request.getProductId());

        return mapper.toDTO(savedStock);
    }

    @Transactional(readOnly = true)
    public StockResponseDTO getStockByProductId(Long productId){
        Stock stock = stockDataService.findByProductId(productId);
        return mapper.toDTO(stock);
    }

    @Transactional
    public StockResponseDTO updateStock(Long productId, StockRequestDTO request){
        Stock stock = stockDataService.findByProductId(productId);
        if (request.getQuantity() == null || request.getQuantity() < 0){
            throw new IllegalArgumentException("Quantity cannot be null or negative");
        }

        stock.setQuantity(request.getQuantity());

        Stock updatedStock = stockRepository.save(stock);
        log.info("Stock updated successfully for product ID: {}. New Quantity: {}", productId, request.getQuantity());

        return mapper.toDTO(updatedStock);
    }

    @Transactional
    public StockResponseDTO adjustStockQuantity(Long productId, Integer quantity){
        Stock stock = stockDataService.findByProductId(productId);
        int newQuantity = stock.getQuantity() + quantity;

        if (newQuantity < 0){
            throw new IllegalArgumentException("Insufficient stock available for this operation");
        }

        stock.setQuantity(newQuantity);
        Stock updatedStock = stockRepository.save(stock);
        log.info("Stock adjusted by {} for product ID: {}. New quantity: {}", quantity, productId, newQuantity);

        return mapper.toDTO(updatedStock);
    }
}
