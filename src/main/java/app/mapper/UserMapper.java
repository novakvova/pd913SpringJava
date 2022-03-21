package app.mapper;

import app.dto.auth.UserView;
import app.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {
    @Mapping(target = "user.roles", ignore = true)
    UserView UserToUserView(UserEntity user);
}
