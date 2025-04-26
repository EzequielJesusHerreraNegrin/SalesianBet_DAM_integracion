package com.accesodatos.mappers.userentity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.accesodatos.dto.userentity.UserEntityRegisterRequestDto;
import com.accesodatos.dto.userentity.UserEntityResponseDto;
import com.accesodatos.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

	UserEntityResponseDto toUserEntityResponseDto (UserEntity userEntity);
	
	
	@Mapping(target = "userId", ignore = true)
	@Mapping(target = "basket", ignore = true)
	UserEntity toUserEntity (UserEntityRegisterRequestDto dto);
}
