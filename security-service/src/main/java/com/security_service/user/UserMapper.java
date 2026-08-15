package com.security_service.user;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDTO dto);

    UserResponseDTO toDTO(User user);

    void updateEntity(UserRequestDTO dto,@MappingTarget User existingUser);

}
