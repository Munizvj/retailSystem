package com.retail_service.service;

import com.retail_service.core.ProductDataService;
import com.retail_service.dto.productDTO.ProductRequestDTO;
import com.retail_service.dto.productDTO.ProductResponseDTO;
import com.retail_service.mapper.ProductMapper;
import com.retail_service.domain.Product;
import com.retail_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductDataService productDataService;
    private final ProductMapper mapper;
    private final static Logger log = LoggerFactory.getLogger(ProductService.class);

    public Page<ProductResponseDTO> getAllProduct(Pageable pageable) {
        Page<Product> products = repository.findAll(pageable);
        return products.map(product -> new ProductResponseDTO(product));
    }

    public ProductResponseDTO findProductById(Long id) {
        Product product = productDataService.findProductById(id);
        ProductResponseDTO response = mapper.toDTO(product);

        return response;
    }

    public ProductResponseDTO findProductByName(String name) {
        Product product = productDataService.findProductByName(name);
        ProductResponseDTO response = mapper.toDTO(product);

        return response;
    }

    @Transactional
    public ProductResponseDTO registerProduct(ProductRequestDTO request) {
        Product product = mapper.toEntity(request);

        log.info("Product registered with success");
        return mapper.toDTO(repository.save(product));
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {
        Product existingProduct = productDataService.findProductById(id);

        mapper.updateEntity(request, existingProduct);
        log.info("Product {} successfully updated", existingProduct.getDescription());

        Product updatedProduct = repository.save(existingProduct);
        return mapper.toDTO(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productDataService.findProductById(id);

        repository.delete(product);
        log.info("Product with id: {}, deleted successfully", product.getId());
    }

}
