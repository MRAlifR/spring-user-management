package com.mralifr.arisan.application.models.responses;

import lombok.Value;

import java.util.Set;

@Value
public class RoleUserResponse {
    Long id;
    String name;
    Set<UserResponse> users;
}
