package com.ase.login.dataAccess;

import com.ase.login.domain.IUserUnmodifiable;

public record JWTResponse(IUserUnmodifiable user, String jwt) {

}
