package com.mralifr.arisan.infrastructure.adapters;

import com.mralifr.arisan.domain.models.Role;
import com.mralifr.arisan.domain.ports.outputs.RoleOutputPort;
import com.mralifr.arisan.helpers.CycleAvoidingMappingContext;
import com.mralifr.arisan.infrastructure.persistence.entities.RoleEntity;
import com.mralifr.arisan.infrastructure.persistence.mappers.RoleEntityMapper;
import com.mralifr.arisan.infrastructure.persistence.repositories.RoleRepository;
import org.hibernate.FetchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleJpaAdapter implements RoleOutputPort {

    private final RoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;

    @Autowired
    public RoleJpaAdapter(RoleRepository roleRepository, RoleEntityMapper roleEntityMapper) {
        this.roleRepository = roleRepository;
        this.roleEntityMapper = roleEntityMapper;
    }

    @Override
    public Role createRole(Role role) {
        RoleEntity roleEntity = roleEntityMapper.toRoleEntity(role, new CycleAvoidingMappingContext());
        return roleEntityMapper.toRole(roleRepository.saveAndFlush(roleEntity), new CycleAvoidingMappingContext());
    }

    @Override
    public Role getRoleById(Long id) {
        RoleEntity roleEntity = roleRepository.findById(id).orElseThrow(
                () -> new FetchNotFoundException(RoleEntity.entityName, id)
        );
        return roleEntityMapper.toRole(roleEntity, new CycleAvoidingMappingContext());
    }

    @Override
    public Set<Role> getAllRoles() {
        return roleRepository.findAll().stream().map(
                roleEntity -> roleEntityMapper.toRole(roleEntity, new CycleAvoidingMappingContext())
        ).collect(Collectors.toSet());
    }

    @Override
    public long count() {
        return roleRepository.count();
    }
}
