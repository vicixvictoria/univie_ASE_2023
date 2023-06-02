package com.ase.login.repository;

import com.ase.login.data.MyUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMyUserRepository extends JpaRepository<MyUser, String> {

    Optional<MyUser> findMyUserByEmail(String email);
}
