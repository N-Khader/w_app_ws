package com.developerblog.ws.shared;

import com.developerblog.ws.io.entity.UserEntity;
import com.developerblog.ws.shared.dto.UserDto;
import com.developerblog.ws.ui.model.request.UserDetialsRequestModel;
import com.developerblog.ws.ui.model.response.UserRest;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
//    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto userDetailsRequestModelToUserDto(UserDetialsRequestModel userDetails);

    UserRest userDtoToUserRest(UserDto userRest);

    UserEntity UserDtoTouserEntity(UserDto userDto);

    UserDto UserEntityTouserDto(UserEntity userEntity);

}
