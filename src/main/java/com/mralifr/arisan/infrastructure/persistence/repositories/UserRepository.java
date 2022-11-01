package com.mralifr.arisan.infrastructure.persistence.repositories;

import com.mralifr.arisan.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "select * from \"user\" u " +
            "left join user_roles_rel urr on u.id = urr.user_id " +
            "left join role r on r.id = urr.roles_id " +
            "where u.username = :username", nativeQuery = true
    )
    Optional<UserEntity> findByUsername(@Param("username") String username);

    @EntityGraph(attributePaths = {"roles"})
    Optional<UserEntity> findById(Long id);

}
