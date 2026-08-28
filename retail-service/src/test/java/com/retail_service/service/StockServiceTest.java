package com.retail_service.service;

import com.retail_service.dto.stockDTO.StockRequestDTO;
import com.retail_service.dto.stockDTO.StockResponseDTO;
import com.retail_service.mapper.StockMapper;
import com.retail_service.model.Product;
import com.retail_service.model.Stock;
import com.retail_service.repository.ProductRepository;
import com.retail_service.repository.StockRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock private StockRepository stockRepository;
    @Mock private ProductRepository productRepository;
    @Mock private StockMapper mapper;
    @InjectMocks private StockService service;

    @Test
    @DisplayName("Should create stock when product exists")
    void shouldCreateStockSuccessfully() {
        Long productId = 1L;
        StockRequestDTO requestDTO = new StockRequestDTO(productId, 10);
        Product product = createProduct(productId);
        Stock savedStock = createStock(100L, product, 10);
        StockResponseDTO expectedResponse = new StockResponseDTO(100L, productId, "Product Name", 10);

        given(stockRepository.existsByProductId(productId)).willReturn(false);
        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(stockRepository.save(any(Stock.class))).willReturn(savedStock);
        given(mapper.toDTO(any(Stock.class))).willReturn(expectedResponse);

        StockResponseDTO response = service.createStock(requestDTO);

        assertThat(response)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);

        verify(stockRepository).save(any(Stock.class));
    }

    private Product createProduct(Long id) {
        Product p = new Product();
        p.setProductId(id);
        return p;
    }

    private Stock createStock(Long id, Product product, Integer quantity) {
        Stock s = new Stock();
        s.setId(id);
        s.setProduct(product);
        s.setQuantity(quantity);
        return s;
    }
}
