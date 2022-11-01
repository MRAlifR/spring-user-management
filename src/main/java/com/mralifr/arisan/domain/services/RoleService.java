package com.mralifr.arisan.domain.services;

import com.mralifr.arisan.domain.models.Role;
import com.mralifr.arisan.domain.ports.inputs.RoleInputPort;
import com.mralifr.arisan.domain.ports.outputs.RoleOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class RoleService implements RoleInputPort {

    private final RoleOutputPort roleOutputPort;

    @Autowired
    public RoleService(RoleOutputPort roleOutputPort) {
        this.roleOutputPort = roleOutputPort;
    }

    @Override
    public Role createRole(Role role) {
        return roleOutputPort.createRole(role);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleOutputPort.getRoleById(id);
    }

    @Override
    public Set<Role> getAllRoles() {
        return roleOutputPort.getAllRoles();
    }

    @Override
    public long count() {
        return roleOutputPort.count();
    }
}
