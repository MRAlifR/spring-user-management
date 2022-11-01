package com.mralifr.arisan.application.models.responses;

import lombok.Value;

import java.io.Serializable;
import java.util.Set;

@Value
public class UserRoleResponse implements Serializable {
    Long id;
    String username;
    String email;
    Set<RoleResponse> roles;
}
