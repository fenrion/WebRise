package Kopylov.webrise.domain.mapper;

import Kopylov.webrise.domain.dto.UserDto;
import Kopylov.webrise.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface UserMapper {

    @Mapping(target = "name", source = "name")
    UserDto userDtoFromUser(User user);

    @Mapping(target = "name", source = "name")
    User userFromUserDto(UserDto userDto);
}
