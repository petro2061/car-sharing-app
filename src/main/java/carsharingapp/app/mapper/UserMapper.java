package carsharingapp.app.mapper;

import carsharingapp.app.config.MapperConfig;
import carsharingapp.app.dto.user.UserRequestDto;
import carsharingapp.app.dto.user.UserResponseDto;
import carsharingapp.app.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    @Mapping(target = "role", ignore = true)
    UserResponseDto toResponseDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    User toUserModel(UserRequestDto userRequestDto);
}
