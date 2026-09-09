package com.retail_service.mapper;

import com.retail_service.domain.sale.Sale;
import com.retail_service.dto.SaleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ItemSaleMapper.class)
public interface SaleMapper {

    @Mapping(source = "saleStatus", target = "status")
    @Mapping(source = "createdAt", target = "createAt")
    @Mapping(source = "saleList", target = "items")
    SaleResponseDTO toDTO(Sale sale);

}
