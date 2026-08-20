package com.retail_service.controller;

import com.retail_service.dto.ProductRequestDTO;
import com.retail_service.dto.ProductResponseDTO;
import com.retail_service.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/product")
public class ProductController {

    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<org.springframework.data.domain.Page<ProductResponseDTO>> getAllProduct(Pageable pageable){
        Page<ProductResponseDTO> productsPage = service.getAllProduct(pageable);

        return ResponseEntity.ok(productsPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findProductById(@PathVariable Long id){

        ProductResponseDTO product = service.findProductById(id);

        return ResponseEntity.ok(product);

    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductResponseDTO> findProductByName(@RequestParam String name){

        ProductResponseDTO product = service.findProductByName(name);

        return ResponseEntity.ok(product);

    }

    @PostMapping("/register")
    public ResponseEntity<ProductResponseDTO> registerProduct(ProductRequestDTO request){

        ProductResponseDTO response = service.registerProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO request){

        ProductResponseDTO response = service.updateProduct(id,request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        service.deleteProduct(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
