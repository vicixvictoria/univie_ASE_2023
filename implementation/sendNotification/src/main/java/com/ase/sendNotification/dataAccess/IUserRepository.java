package com.ase.sendNotification.dataAccess;

import com.ase.sendNotification.domain.NotificationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<NotificationUser, String> {

}
