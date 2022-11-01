package com.mralifr.arisan.application.adapters;

import com.mralifr.arisan.application.models.mappers.UserMapper;
import com.mralifr.arisan.application.models.requests.CreateUserRequest;
import com.mralifr.arisan.application.models.requests.EditUserRequest;
import com.mralifr.arisan.application.models.responses.UserRoleResponse;
import com.mralifr.arisan.domain.models.User;
import com.mralifr.arisan.domain.ports.inputs.UserInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/v1/users")
public class UserApiAdapter {

    private final UserInputPort userInputPort;
    private final UserMapper userMapper;

    @Autowired
    public UserApiAdapter(UserInputPort userInputPort, UserMapper userMapper) {
        this.userInputPort = userInputPort;
        this.userMapper = userMapper;
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        User user = userMapper.toUser(createUserRequest);
        User registeredUser = userInputPort.registerUser(user);
        return new ResponseEntity<>(userMapper.toUserRoleResponse(registeredUser), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllUser() {
        Set<UserRoleResponse> users = userInputPort.getAllUsers().stream().map(
                userMapper::toUserRoleResponse
        ).collect(Collectors.toSet());
        return ResponseEntity.ok(users);
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<?> editUser(@PathVariable Long userId, @RequestBody EditUserRequest editUserRequest) {
        User user = userMapper.toUser(editUserRequest);
        userInputPort.editUser(userId, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        User user = userInputPort.getUserById(userId);
        return ResponseEntity.ok(userMapper.toUserRoleResponse(user));
    }

    @PutMapping(path = "/{userId}/roles/{roleId}")
    public ResponseEntity<?> addRoleToUser(
            @PathVariable Long userId,
            @PathVariable Long roleId
    ) {
        userInputPort.addUserRole(userId, roleId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{userId}/roles/{roleId}")
    public ResponseEntity<?> removeRoleFromUser(
            @PathVariable Long userId,
            @PathVariable Long roleId
    ) {
        userInputPort.removeUserRole(userId, roleId);
        return ResponseEntity.ok().build();
    }
}
