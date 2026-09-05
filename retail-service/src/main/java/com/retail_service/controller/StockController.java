package com.retail_service.controller;

import com.retail_service.dto.stockDTO.StockRequestDTO;
import com.retail_service.dto.stockDTO.StockResponseDTO;
import com.retail_service.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_STOCK')")
    public ResponseEntity<StockResponseDTO> createStock(@RequestBody @Valid StockRequestDTO request) {
        StockResponseDTO response = service.createStock(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/product/{productId}")
    @PreAuthorize("hasAuthority('GET_STOCK')")
    public ResponseEntity<StockResponseDTO> getStockByProductId(@PathVariable Long productId) {
        StockResponseDTO response = service.getStockByProductId(productId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/product/{productId}")
    @PreAuthorize("hasAuthority('UPDATE_STOCK')")
    public ResponseEntity<StockResponseDTO> updateStock(
            @PathVariable Long productId,
            @RequestBody @Valid StockRequestDTO request) {

        StockResponseDTO response = service.updateStock(productId, request);
        return ResponseEntity.ok(response);

    }

    @PatchMapping("/product/{productId}/adjust")
    @PreAuthorize("hasAuthority('UPDATE_STOCK')")
    public ResponseEntity<StockResponseDTO> adjustStockQuantity(
            @PathVariable Long productId,
            @RequestParam Integer delta) {
        StockResponseDTO response = service.adjustStockQuantity(productId, delta);
        return ResponseEntity.ok(response);
    }
}
