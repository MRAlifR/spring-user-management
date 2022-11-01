package com.mralifr.arisan.application.models.requests;

import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Value
@NoArgsConstructor(force = true)
public class CreateUserRequest implements Serializable {
    @NotBlank
    String username;

    @Email
    @NotBlank
    String email;

    @NotBlank
    String password;
}
