package com.mralifr.arisan.infrastructure.persistence.mappers;

import com.mralifr.arisan.domain.models.User;
import com.mralifr.arisan.helpers.CycleAvoidingMappingContext;
import com.mralifr.arisan.infrastructure.persistence.entities.UserEntity;
import org.mapstruct.*;

@Mapper
public interface UserEntityMapper {

    User toUser(UserEntity userEntity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @InheritInverseConfiguration
    UserEntity toUserEntity(User user, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserEntityFromUser(User user, @MappingTarget UserEntity userEntity);

}
