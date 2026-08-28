package com.retail_service.mapper;

import com.retail_service.dto.stockDTO.StockRequestDTO;
import com.retail_service.dto.stockDTO.StockResponseDTO;
import com.retail_service.model.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StockMapper {

    Stock toEntity(StockRequestDTO dto);

    StockResponseDTO toDTO(Stock stock);

    void updateStock(StockRequestDTO requestDTO, @MappingTarget Stock stock);

}
