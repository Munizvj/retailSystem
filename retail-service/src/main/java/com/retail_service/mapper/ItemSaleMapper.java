package com.retail_service.mapper;

import com.retail_service.domain.sale.ItemSale;
import com.retail_service.dto.ItemSaleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemSaleMapper {

    @Mapping(source = "product.id", target = "productId")
    ItemSaleResponseDTO toDTO(ItemSale itemSale);

}
