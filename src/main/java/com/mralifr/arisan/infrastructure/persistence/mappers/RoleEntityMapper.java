package com.mralifr.arisan.infrastructure.persistence.mappers;

import com.mralifr.arisan.domain.models.Role;
import com.mralifr.arisan.helpers.CycleAvoidingMappingContext;
import com.mralifr.arisan.infrastructure.persistence.entities.RoleEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper
public interface RoleEntityMapper {

    Role toRole(RoleEntity roleEntity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    RoleEntity toRoleEntity(Role role, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

}
