package com.mralifr.arisan.application.models.requests;

import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Value
@NoArgsConstructor(force = true)
public class EditUserRequest implements Serializable {
    String username;
    String email;
    String password;
}
