package com.mralifr.arisan.infrastructure.adapters;

import com.mralifr.arisan.domain.models.User;
import com.mralifr.arisan.domain.ports.outputs.UserOutputPort;
import com.mralifr.arisan.helpers.CycleAvoidingMappingContext;
import com.mralifr.arisan.infrastructure.persistence.entities.RoleEntity;
import com.mralifr.arisan.infrastructure.persistence.entities.UserEntity;
import com.mralifr.arisan.infrastructure.persistence.mappers.UserEntityMapper;
import com.mralifr.arisan.infrastructure.persistence.repositories.RoleRepository;
import com.mralifr.arisan.infrastructure.persistence.repositories.UserRepository;
import org.hibernate.FetchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserJpaAdapter implements UserOutputPort {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserEntityMapper userEntityMapper;

    @Autowired
    public UserJpaAdapter(UserRepository userRepository, RoleRepository roleRepository, UserEntityMapper userEntityMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public User createUser(User user) {
        UserEntity userEntity = userEntityMapper.toUserEntity(user, new CycleAvoidingMappingContext());
        return userEntityMapper.toUser(userRepository.saveAndFlush(userEntity), new CycleAvoidingMappingContext());
    }

    @Override
    public Set<User> getAllUsers() {
        return userRepository.findAll().stream().map(
                userEntity -> userEntityMapper.toUser(userEntity, new CycleAvoidingMappingContext())
        ).collect(Collectors.toSet());
    }

    @Override
    public void editUser(Long id, User user) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new FetchNotFoundException(UserEntity.entityName, id)
        );
        userEntityMapper.updateUserEntityFromUser(user, userEntity);
        userRepository.saveAndFlush(userEntity);
    }

    @Override
    public User getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new FetchNotFoundException(UserEntity.entityName, id)
        );
        return userEntityMapper.toUser(userEntity, new CycleAvoidingMappingContext());
    }

    @Override
    public User getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(
                () -> new FetchNotFoundException(UserEntity.entityName, username)
        );
        return userEntityMapper.toUser(userEntity, new CycleAvoidingMappingContext());
    }

    @Override
    public void addUserRole(Long userId, Long roleId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new FetchNotFoundException(UserEntity.entityName, userId)
        );
        RoleEntity role = roleRepository.findById(roleId).orElseThrow(
                () -> new FetchNotFoundException(RoleEntity.entityName, roleId)
        );

        Set<RoleEntity> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    @Override
    public void removeUserRole(Long userId, Long roleId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new FetchNotFoundException(UserEntity.entityName, userId)
        );
        RoleEntity role = roleRepository.findById(roleId).orElseThrow(
                () -> new FetchNotFoundException(RoleEntity.entityName, roleId)
        );

        Set<RoleEntity> userRoles = user.getRoles();
        userRoles.remove(role);
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    @Override
    public long count() {
        return userRepository.count();
    }
}
