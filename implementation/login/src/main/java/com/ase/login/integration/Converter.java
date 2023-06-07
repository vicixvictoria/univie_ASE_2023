package com.ase.login.integration;

import com.ase.common.user.EUserRole;
import com.ase.login.dataAccess.JWTResponse;
import com.ase.login.domain.ERole;
import com.ase.login.domain.IUserData;
import com.ase.login.domain.MyUser;
import com.ase.login.domain.UserData;
import org.springframework.stereotype.Component;

/**
 * Responsible for converting between common objects and internal objects
 */
@Component
public class Converter {

    public EUserRole internalUserRoleToNetworkUserRole(ERole role) {
        return switch (role) {
            case ADMIN -> EUserRole.ADMIN;
            case ATTENDEE -> EUserRole.ATTENDEE;
            case ORGANIZER -> EUserRole.ORGANIZER;
        };
    }

    public ERole networkUserRoleToInternalUserRole(EUserRole role) {
        return switch (role) {
            case ADMIN -> ERole.ADMIN;
            case ATTENDEE -> ERole.ATTENDEE;
            case ORGANIZER -> ERole.ORGANIZER;
        };
    }

    public com.ase.common.user.User internalUserToNetworkUser(MyUser user) {
        return new com.ase.common.user.User(user.getId(), user.getEmail(),
                internalUserRoleToNetworkUserRole(user.getRole()));
    }

    public IUserData networkUserDataToInternalUserData(com.ase.common.user.UserData userData) {
        return new UserData(userData.email(), userData.password(),
                networkUserRoleToInternalUserRole(userData.role()));
    }

    public com.ase.common.user.JWTResponse internalJWTResponseToNetworkJWTResponse(
            JWTResponse jwtResponse) {
        return new com.ase.common.user.JWTResponse(jwtResponse.user().getId(),
                internalUserRoleToNetworkUserRole(jwtResponse.user().getRole()),
                jwtResponse.jwt());
    }
}
