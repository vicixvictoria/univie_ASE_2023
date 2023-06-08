package com.ase.common.user;

import java.io.Serializable;

public record UserData(String email, String password, EUserRole role) implements Serializable {

}
