package com.retail_service.mapper;

import com.retail_service.domain.sale.Sale;
import com.retail_service.dto.SaleResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    SaleResponseDTO toDTO(Sale sale);

}
