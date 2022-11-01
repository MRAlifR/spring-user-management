package com.mralifr.arisan.infrastructure.persistence.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity extends AuditableEntity {

    public static final String entityName = RoleEntity.class.getSimpleName();

    @Column(name = "name", nullable = false)
    private String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users = new LinkedHashSet<>();

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RoleEntity that = (RoleEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}