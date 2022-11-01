package com.mralifr.arisan.domain.ports.outputs;

import com.mralifr.arisan.domain.models.Role;

import java.util.Set;

public interface RoleOutputPort {

    Role createRole(Role role);
    Role getRoleById(Long id);
    Set<Role> getAllRoles();
    long count();

}
