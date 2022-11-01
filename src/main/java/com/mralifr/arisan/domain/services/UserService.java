package com.mralifr.arisan.domain.services;

import com.mralifr.arisan.domain.models.User;
import com.mralifr.arisan.domain.ports.inputs.UserInputPort;
import com.mralifr.arisan.domain.ports.outputs.UserOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class UserService implements UserInputPort {

    private final UserOutputPort userOutputPort;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserOutputPort userOutputPort, PasswordEncoder passwordEncoder) {
        this.userOutputPort = userOutputPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return userOutputPort.createUser(user);
    }

    @Override
    public Set<User> getAllUsers() {
        return userOutputPort.getAllUsers();
    }

    @Override
    public void editUser(Long id, User user) {
        if (user.getPassword() != null && !user.getPassword().isBlank()){
            String password = passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
        }
        userOutputPort.editUser(id, user);
    }

    @Override
    public User getUserById(Long id) {
        return userOutputPort.getUserById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userOutputPort.getUserByUsername(username);
    }

    @Override
    public void addUserRole(Long userId, Long roleId) {
        userOutputPort.addUserRole(userId, roleId);
    }

    @Override
    public void removeUserRole(Long userId, Long roleId) {
        userOutputPort.removeUserRole(userId, roleId);
    }

    @Override
    public long count() {
        return userOutputPort.count();
    }

}
