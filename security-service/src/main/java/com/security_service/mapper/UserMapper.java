package com.security_service.mapper;

import com.security_service.model.User.User;
import com.security_service.dto.UserRequestDTO;
import com.security_service.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDTO dto);

    UserResponseDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "extraPermission", ignore = true)
    void updateEntity(UserRequestDTO dto,@MappingTarget User existingUser);

}
