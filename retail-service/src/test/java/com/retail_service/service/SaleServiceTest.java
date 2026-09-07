package com.retail_service.service;

import com.retail_service.core.ProductDataService;
import com.retail_service.core.SaleDataService;
import com.retail_service.core.StockDataService;
import com.retail_service.domain.Product;
import com.retail_service.domain.sale.PaymentMethod;
import com.retail_service.domain.sale.Sale;
import com.retail_service.domain.sale.SaleStatus;
import com.retail_service.dto.ItemSaleRequestDTO;
import com.retail_service.dto.SaleRequestDTO;
import com.retail_service.dto.SaleResponseDTO;
import com.retail_service.mapper.SaleMapper;
import com.retail_service.repository.SaleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private SaleDataService saleDataService;

    @Mock
    private SaleMapper mapper;

    @Mock
    private ProductDataService productDataService;

    @Mock
    private StockDataService stockDataService;

    @InjectMocks
    private SaleService service;

    @Test
    @DisplayName("Should create Sale")
    void shouldCreateSaleSuccessfully(){
        ItemSaleRequestDTO itemDTO = new ItemSaleRequestDTO(1L, 2);
        SaleRequestDTO request = new SaleRequestDTO(100L, PaymentMethod.PIX, List.of(itemDTO));

        Product product = mock(Product.class);

        when(product.getPrice()).thenReturn(new BigDecimal("5.99"));

        SaleResponseDTO expectedResponse = new SaleResponseDTO();

        when(productDataService.findProductById(1L)).thenReturn(product);
        when(saleRepository.save(any(Sale.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toDTO(any(Sale.class))).thenReturn(expectedResponse);

        SaleResponseDTO response = service.createSale(request);

        assertThat(response).isNotNull().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Stock should not change when sale is cancelled")
    void shouldNotChangeStockWhenCancelingNonFinishedSale(){

        Long saleId = 1L;

        Sale sale = mock(Sale.class);

        when(sale.getSaleStatus()).thenReturn(SaleStatus.PENDING);
        when(saleDataService.findById(saleId)).thenReturn(sale);
        when(saleRepository.save(sale)).thenReturn(sale);

        service.cancelSale(saleId);

        verify(stockDataService, never()).findByProductId(any());
        verify(stockDataService,never()).save(any());
        verify(sale).cancelSale();
        verify(saleRepository).save(sale);

    }

}