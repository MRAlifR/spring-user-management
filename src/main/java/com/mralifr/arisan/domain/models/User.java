package com.mralifr.arisan.domain.models;

import com.mralifr.arisan.domain.models.common.AuditableModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AuditableModel {

    private String username;

    private String email;

    private String password;

    private Set<Role> roles;

}
