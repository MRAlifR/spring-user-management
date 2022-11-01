package com.mralifr.arisan.application.models.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Value
@NoArgsConstructor(force = true)
public class AuthUserRequest implements Serializable {

    @NotBlank
    @JsonProperty("username")
    String username;

    @NotBlank
    @JsonProperty("password")
    String password;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AuthUserRequest(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

}
