package com.stswalkoo.fwalarm.mapper;

import com.stswalkoo.Fwmemberno.model.MemberNo;
import com.stswalkoo.fwalarm.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserEmail(String userEmail);
    void deleteByUserEmailAndUserkey(String userEmail, Long userkey);
}
