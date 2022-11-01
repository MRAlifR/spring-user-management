package com.mralifr.arisan.domain.ports.inputs;

import com.mralifr.arisan.domain.models.Role;

import java.util.Set;

public interface RoleInputPort {
    Role createRole(Role role);
    Role getRoleById(Long id);
    Set<Role> getAllRoles();
    long count();
}
