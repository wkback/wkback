package com.stswalkoo.fwalarm.service;

import com.stswalkoo.Fwmemberno.mapper.MembernoMapper;
import com.stswalkoo.Fwmemberno.model.MemberNo;
import com.stswalkoo.fwalarm.mapper.NotificationRepository;
import com.stswalkoo.fwalarm.model.Notification;
import com.stswalkoo.fwmember.mapper.MemberMapper;
import com.stswalkoo.fwmember.model.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Date;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final MembernoMapper membernoMapper;
    private final MemberMapper memberRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, MembernoMapper membernoMapper, MemberMapper memberRepository) {
        this.notificationRepository = notificationRepository;
        this.membernoMapper = membernoMapper;
        this.memberRepository = memberRepository;
    }


    public Notification createNotification(String userEmail, String noMemberName, String noMemberImage, Long userkey) {
        Notification notification = new Notification();
        notification.setUserEmail(userEmail);
        notification.setNoMemberName(noMemberName);
        notification.setNoMemberImage(noMemberImage);
        notification.setUserkey(userkey);
        notification.setCreatedAt(new Date());
        return notificationRepository.save(notification);
    }

    public Notification sendFriendRequestNotification(String userEmail, String noMemberName, String noMemberImage, Long userkey) {
        return createNotification(userEmail, noMemberName, noMemberImage, userkey);
    }

    public List<Notification> getNotificationsByEmail(String userEmail) {
        return notificationRepository.findByUserEmail(userEmail);
    }

    public List<Notification> getAllNotificationsWithImageAndName() {
        return notificationRepository.findAll();
    }

    @Transactional
    public void acceptFriendRequest(String userEmail, Long userkey) {
        // 1. Find the no_member data associated with the user
        MemberNo noMember = membernoMapper.findByUserEmailAndUserkey(userEmail, userkey);

        if (noMember != null) {
            // 2. Create a new Member object based on the no_member data
            Member member = new Member();
            member.setUserkey(noMember.getUserkey());
            member.setMemberName(noMember.getNoMemberName());
            member.setMemberImage(noMember.getNoMemberImage());
            member.setUserEmail(userEmail);

            // 3. Save or update the Member object in the member table
            // You may need to create a method in your memberRepository to handle this
            memberRepository.save(member);

            // 4. Delete the no_member data
            membernoMapper.deleteByUserEmailAndUserkey(userEmail, userkey);
        }

        // 5. Delete the notification
        notificationRepository.deleteByUserEmailAndUserkey(userEmail, userkey);
    }

    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}