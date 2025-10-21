package carsharingapp.app.mapper;

import carsharingapp.app.config.MapperConfig;
import carsharingapp.app.dto.user.UserRequestDto;
import carsharingapp.app.dto.user.UserResponseDto;
import carsharingapp.app.enums.RoleType;
import carsharingapp.app.model.Role;
import carsharingapp.app.model.User;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = User.class)
public interface UserMapper {
    @Mapping(target = "roles", source = "user", qualifiedByName = "mapRoleTypes")
    UserResponseDto toResponseDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toUserModel(UserRequestDto userRequestDto);

    @Named("mapRoleTypes")
    default Set<RoleType> mapRoleTypes(User user) {
        if (user.getRoles() == null) {
            return Set.of();
        }
        return user.getRoles().stream()
                .map(Role::getRoleType)
                .collect(Collectors.toSet());
    }
}
