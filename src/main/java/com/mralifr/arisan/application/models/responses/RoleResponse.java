package com.mralifr.arisan.application.models.responses;

import lombok.Value;

import java.io.Serializable;

@Value
public class RoleResponse implements Serializable {
    Long id;
    String name;
}
