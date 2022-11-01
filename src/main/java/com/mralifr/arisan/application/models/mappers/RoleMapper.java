package com.mralifr.arisan.application.models.mappers;

import com.mralifr.arisan.application.models.requests.CreateRoleRequest;
import com.mralifr.arisan.application.models.responses.RoleResponse;
import com.mralifr.arisan.application.models.responses.RoleUserResponse;
import com.mralifr.arisan.domain.models.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    Role toRole(CreateRoleRequest createRoleRequest);
    RoleResponse toRoleResponse(Role role);
    RoleUserResponse toRoleUserResponse(Role role);
}
