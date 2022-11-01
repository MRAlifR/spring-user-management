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
public class Role extends AuditableModel {

    private String name;

    private Set<User> users;

    @Override
    public String toString() {
        return name;
    }
}
