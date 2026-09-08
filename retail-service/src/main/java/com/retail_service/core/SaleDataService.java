package com.retail_service.core;

import com.retail_service.domain.sale.Sale;
import com.retail_service.repository.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleDataService {

    private final SaleRepository repository;

    public Sale findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sale not found"));
    }

}
