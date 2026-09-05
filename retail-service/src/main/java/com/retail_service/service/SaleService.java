package com.retail_service.service;

import com.retail_service.core.ProductDataService;
import com.retail_service.mapper.SaleMapper;
import com.retail_service.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper mapper;
    private final ProductDataService productDataService;
    private final StockService stockService;

}
