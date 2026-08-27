package com.retail_service.controller;

import com.retail_service.dto.ProductRequestDTO;
import com.retail_service.dto.ProductResponseDTO;
import com.retail_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    @PreAuthorize("hasAuthority('GET_PRODUCTS')")
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        System.out.println("CHEGOU NO CONTROLLER");
        Page<ProductResponseDTO> page = service.getAllProduct(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GET_PRODUCTS')")
    public ResponseEntity<ProductResponseDTO> findProductById(@PathVariable Long id){

        ProductResponseDTO product = service.findProductById(id);

        return ResponseEntity.ok(product);

    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('GET_PRODUCTS')")
    public ResponseEntity<ProductResponseDTO> findProductByName(@RequestParam String name){

        ProductResponseDTO product = service.findProductByName(name);

        return ResponseEntity.ok(product);

    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('REGISTER_PRODUCT')")
    public ResponseEntity<ProductResponseDTO> registerProduct(@RequestBody ProductRequestDTO request){

        ProductResponseDTO response = service.registerProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO request){

        ProductResponseDTO response = service.updateProduct(id,request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        service.deleteProduct(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
