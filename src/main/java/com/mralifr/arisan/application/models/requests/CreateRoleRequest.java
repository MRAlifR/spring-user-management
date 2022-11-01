package com.mralifr.arisan.application.models.requests;

import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Value
@NoArgsConstructor(force = true)
public class CreateRoleRequest implements Serializable {
    @NotBlank
    String name;
}
