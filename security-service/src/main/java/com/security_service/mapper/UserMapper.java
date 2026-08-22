package com.security_service.mapper;

import com.security_service.model.User;
import com.security_service.dto.UserRequestDTO;
import com.security_service.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDTO dto);

    UserResponseDTO toDTO(User user);

    void updateEntity(UserRequestDTO dto,@MappingTarget User existingUser);

}
