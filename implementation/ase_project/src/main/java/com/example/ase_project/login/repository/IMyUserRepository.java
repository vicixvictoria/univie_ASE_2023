package com.example.ase_project.login.repository;

import com.example.ase_project.login.data.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IMyUserRepository extends JpaRepository<MyUserSavable, String> {
    Optional<MyUserSavable> findMyUserSavableByEmail(String email);
}
