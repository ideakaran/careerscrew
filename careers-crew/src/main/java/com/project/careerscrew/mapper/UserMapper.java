package com.project.careerscrew.mapper;

import com.project.careerscrew.dto.UserEntityDTO;
import com.project.careerscrew.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity(UserEntityDTO userEntityDTO);

    UserEntityDTO toDto(UserEntity userEntity);

    List<UserEntityDTO> toDto(List<UserEntity> userEntities);
}
