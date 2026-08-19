package com.retail_service.mapper;

import com.retail_service.dto.ProductRequestDTO;
import com.retail_service.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDTO dto);

    Product toDTO(Product product);

}
