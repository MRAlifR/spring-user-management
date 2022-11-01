package com.mralifr.arisan.infrastructure.persistence.repositories;

import com.mralifr.arisan.infrastructure.persistence.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Set<RoleEntity> findByUsers_Id(Long id);
}