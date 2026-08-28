package com.retail_service.controller;

import com.retail_service.dto.stockDTO.StockRequestDTO;
import com.retail_service.dto.stockDTO.StockResponseDTO;
import com.retail_service.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    @PostMapping("/create")
    public ResponseEntity<StockResponseDTO> createStock(StockRequestDTO request){
         StockResponseDTO response = service.createStock(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<StockResponseDTO> getStockByProductId(Long productId){
        StockResponseDTO response = service.getStockByProductId(productId);

        return ResponseEntity.ok(response);
    }

}
