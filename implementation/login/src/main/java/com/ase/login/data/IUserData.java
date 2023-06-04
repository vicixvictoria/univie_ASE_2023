package com.ase.login.data;

/**
 * Defines getters needed to communicate user data
 */
public interface IUserData {

    String getEmail();

    String getPassword();

    ERole getRole();

}
