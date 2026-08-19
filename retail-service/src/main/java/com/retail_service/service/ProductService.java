package com.retail_service.service;

import com.retail_service.dto.ProductRequestDTO;
import com.retail_service.dto.ProductResponseDTO;
import com.retail_service.mapper.ProductMapper;
import com.retail_service.model.Product;
import com.retail_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final static Logger log = LoggerFactory.getLogger(ProductService.class);

    public List<ProductResponseDTO> getAllProduct(){
        return repository.findAll()
                .stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO findProductById(Long id){
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        ProductResponseDTO response = mapper.toDTO(product);

        return response;
    }

    public ProductResponseDTO registerProduct(ProductRequestDTO request){
        Product product = mapper.toEntity(request);

        log.info("Product registered with success");
        return mapper.toDTO(repository.save(product));
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request){
        Product existingProduct = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        mapper.updateEntity(request, existingProduct);
        log.info("Product {} successfully updated", existingProduct.getDescription());

        Product updatedProduct = repository.save(existingProduct);
        return mapper.toDTO(updatedProduct);
    }

    public void deleteUser(Long id){
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        repository.delete(product);
        log.info("Product deleted successfully");
    }

}
