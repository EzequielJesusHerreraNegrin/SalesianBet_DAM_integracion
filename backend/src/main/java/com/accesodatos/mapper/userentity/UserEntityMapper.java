package com.accesodatos.mapper.userentity;

import org.mapstruct.Mapper;

import com.accesodatos.dto.userentity.UserEntityResponseDto;
import com.accesodatos.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

	UserEntityResponseDto toUserEntityResponseDto (UserEntity userEntity);
}
