package com.mralifr.arisan.application.models.mappers;

import com.mralifr.arisan.application.models.requests.CreateUserRequest;
import com.mralifr.arisan.application.models.requests.EditUserRequest;
import com.mralifr.arisan.application.models.responses.UserResponse;
import com.mralifr.arisan.application.models.responses.UserRoleResponse;
import com.mralifr.arisan.domain.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User toUser(CreateUserRequest createUserRequest);
    User toUser(EditUserRequest editUserRequest);
    UserRoleResponse toUserRoleResponse(User user);
    UserResponse toUserResponse(User user);
}
