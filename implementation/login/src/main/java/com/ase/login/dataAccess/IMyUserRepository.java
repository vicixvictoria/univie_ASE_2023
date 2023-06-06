package com.ase.login.dataAccess;

import com.ase.login.domain.MyUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMyUserRepository extends JpaRepository<MyUser, String> {

    Optional<MyUser> findMyUserByEmail(String email);
}
