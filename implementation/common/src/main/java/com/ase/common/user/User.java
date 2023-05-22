package com.ase.common.user;

import java.io.Serializable;

public record User(String id, String email, EUserRole role) implements Serializable {

}
