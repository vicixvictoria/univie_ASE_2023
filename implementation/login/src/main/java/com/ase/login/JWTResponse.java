package com.ase.login;

import com.ase.login.data.IUserUnmodifiable;

public record JWTResponse(IUserUnmodifiable user, String jwt) {

}
