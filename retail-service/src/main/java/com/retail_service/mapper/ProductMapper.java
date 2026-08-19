package com.retail_service.mapper;

import com.retail_service.dto.ProductRequestDTO;
import com.retail_service.dto.ProductResponseDTO;
import com.retail_service.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDTO dto);

    ProductResponseDTO toDTO(Product product);

    void updateEntity(ProductRequestDTO request,@MappingTarget Product product);

}
