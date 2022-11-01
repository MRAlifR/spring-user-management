package com.mralifr.arisan.domain.ports.outputs;

import com.mralifr.arisan.domain.models.User;

import java.util.Set;

public interface UserOutputPort {

    User createUser(User user);
    Set<User> getAllUsers();
    void editUser(Long id, User user);
    User getUserById(Long id);
    User getUserByUsername(String username);
    void addUserRole(Long userId, Long roleId);
    void removeUserRole(Long userId, Long roleId);
    long count();

}
