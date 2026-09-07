package com.retail_service.controller;

import com.retail_service.dto.SaleRequestDTO;
import com.retail_service.dto.SaleResponseDTO;
import com.retail_service.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService service;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_SALE')")
    public ResponseEntity<SaleResponseDTO> createSale(@RequestBody @Valid SaleRequestDTO request){
        SaleResponseDTO response = service.createSale(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/finalize/{saleID}")
    @PreAuthorize("hasAuthority('FINALIZE_SALE')")
    public ResponseEntity<SaleResponseDTO> finalizeSale(@PathVariable Long saleID){
        return ResponseEntity.ok(service.finalizeSale(saleID));
    }

    @PostMapping("/cancel/{saleID}")
    @PreAuthorize("hasAuthority('CANCEL_SALE')")
    public ResponseEntity<SaleResponseDTO> cancel(@PathVariable Long saleID){
        return ResponseEntity.ok(service.cancelSale(saleID));
    }

}
