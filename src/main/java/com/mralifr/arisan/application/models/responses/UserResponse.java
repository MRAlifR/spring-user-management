package com.mralifr.arisan.application.models.responses;

import lombok.Value;

import java.io.Serializable;

@Value
public class UserResponse implements Serializable {
    Long id;
    String username;
    String email;
}
