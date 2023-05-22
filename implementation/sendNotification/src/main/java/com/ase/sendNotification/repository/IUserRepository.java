package com.ase.sendNotification.repository;

import com.ase.sendNotification.model.NotificationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<NotificationUser, String> {

}
