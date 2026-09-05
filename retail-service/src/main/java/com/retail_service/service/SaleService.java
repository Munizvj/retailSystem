package com.retail_service.service;

import com.retail_service.core.ProductDataService;
import com.retail_service.core.SaleDataService;
import com.retail_service.core.StockDataService;
import com.retail_service.domain.Product;
import com.retail_service.domain.Stock;
import com.retail_service.domain.sale.Sale;
import com.retail_service.dto.SaleRequestDTO;
import com.retail_service.dto.SaleResponseDTO;
import com.retail_service.mapper.SaleMapper;
import com.retail_service.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleDataService saleDataService;
    private final SaleMapper mapper;
    private final ProductDataService productDataService;
    private final StockDataService stockDataService;

    @Transactional
    public SaleResponseDTO createSale(SaleRequestDTO request) {
        Sale sale = Sale.create(request.getUserId(), request.getPaymentMethod());

        request.getItems().forEach(itemRequest -> {
            Product product = productDataService.findProductById(itemRequest.getProductId());

            sale.addItem(product, itemRequest.getQuantity());
        });

        Sale savedSale = saleRepository.save(sale);

        log.info("Sale created successfully");
        return mapper.toDTO(savedSale);
    }

    @Transactional
    public SaleResponseDTO finalizeSale(Long saleId) {
        Sale sale = saleDataService.findById(saleId);

        sale.finalizeSale();

        sale.getSaleList().forEach(item -> {
            Stock stock = stockDataService.findByProductId(item.getProduct().getId());

            if (stock.getQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product ID:" + item.getProduct().getId());
            }

            stock.setQuantity(stock.getQuantity() - item.getQuantity());
            stockDataService.save(stock);
        });

        Sale updatedSale = saleRepository.save(sale);
        log.info("Sale ID {} finalized successfully", saleId);
        return mapper.toDTO(updatedSale);
    }

    @Transactional
    public SaleResponseDTO cancelSale(Long saleId) {
        Sale sale = saleDataService.findById(saleId);

        if (sale.getSaleStatus().name().equals("FINISHED")) {
            sale.getSaleList().forEach(item -> {
                Stock stock = stockDataService.findByProductId(item.getProduct().getId());

                stock.setQuantity(stock.getQuantity() + item.getQuantity());
                stockDataService.save(stock);
            });
        }


        sale.cancelSale();
        Sale updatedSale = saleRepository.save(sale);

        log.info("Sale ID {} cancelled successfully", saleId);
        return mapper.toDTO(updatedSale);
    }

}
