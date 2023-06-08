package com.ase.common.user;

import java.io.Serializable;

public record JWTResponse(String id, EUserRole role, String jwt) implements Serializable {

}
