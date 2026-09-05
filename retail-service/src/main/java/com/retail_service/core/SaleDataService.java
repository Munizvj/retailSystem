package com.retail_service.core;

import com.retail_service.domain.sale.Sale;
import com.retail_service.repository.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SaleDataService {

    private SaleRepository repository;

    public Sale findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sale not found"));
    }

}
