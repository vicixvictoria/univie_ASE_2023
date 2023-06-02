package com.ase.common.user;

import java.io.Serializable;

public record UserLogin(String email, String password) implements Serializable {

}
