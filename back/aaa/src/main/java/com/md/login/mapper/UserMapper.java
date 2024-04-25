package com.md.login.mapper;

import com.md.login.model.dto.UserDto;
import com.md.login.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper instance = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "documentNumber", source = "documentNumber")
    @Mapping(target = "tramitNumber", source = "tramitNumber")
    UserDto mapEntityToUserDto(User user);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "documentNumber", source = "documentNumber")
    @Mapping(target = "tramitNumber", source = "tramitNumber")
    User mapUserDtoToUser(UserDto userDto);

}
