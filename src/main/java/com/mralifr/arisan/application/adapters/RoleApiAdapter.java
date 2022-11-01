package com.mralifr.arisan.application.adapters;

import com.mralifr.arisan.application.models.mappers.RoleMapper;
import com.mralifr.arisan.application.models.requests.CreateRoleRequest;
import com.mralifr.arisan.application.models.responses.RoleResponse;
import com.mralifr.arisan.domain.models.Role;
import com.mralifr.arisan.domain.ports.inputs.RoleInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(path = "/v1/roles")
public class RoleApiAdapter {

    private final RoleInputPort roleInputPort;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleApiAdapter(RoleInputPort roleInputPort, RoleMapper roleMapper) {
        this.roleInputPort = roleInputPort;
        this.roleMapper = roleMapper;
    }

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody @Valid CreateRoleRequest createRoleRequest) {
        Role role = roleMapper.toRole(createRoleRequest);
        Role newRole = roleInputPort.createRole(role);
        return new ResponseEntity<>(roleMapper.toRoleResponse(newRole), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        Set<RoleResponse> roleResponses = roleInputPort.getAllRoles().stream().map(roleMapper::toRoleResponse).collect(Collectors.toSet());
        return ResponseEntity.ok(roleResponses);
    }

    @GetMapping(path = "/{roleId}")
    public ResponseEntity<?> getRoleById(@PathVariable Long roleId) {
        Role role = roleInputPort.getRoleById(roleId);
        return ResponseEntity.ok(
                roleMapper.toRoleUserResponse(role)
        );
    }
}
