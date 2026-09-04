package com.retail_service.mapper;

import com.retail_service.dto.productDTO.ProductRequestDTO;
import com.retail_service.dto.productDTO.ProductResponseDTO;
import com.retail_service.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDTO dto);

    ProductResponseDTO toDTO(Product product);

    void updateEntity(ProductRequestDTO request,@MappingTarget Product product);

}
