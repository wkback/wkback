package com.stswalkoo.fwalarm.controller;

import com.stswalkoo.Fwmemberno.mapper.MembernoMapper;
import com.stswalkoo.Fwmemberno.model.MemberNo;
import com.stswalkoo.Fwmemberno.service.MembernoService;
import com.stswalkoo.fwalarm.model.Notification;
import com.stswalkoo.fwalarm.service.NotificationService;

import com.stswalkoo.fwmember.mapper.MemberMapper;
import com.stswalkoo.fwmember.model.Member;
import com.stswalkoo.fwmember.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send-friend-request-notification")
    public ResponseEntity<Notification> sendFriendRequestNotification(
            @RequestParam String targetUserEmail,
            @RequestParam String noMemberName,
            @RequestParam String noMemberImage,
            @RequestParam Long userkey) {
        Notification notification = notificationService.sendFriendRequestNotification(targetUserEmail, noMemberName, noMemberImage, userkey);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/notification-by-email")
    public ResponseEntity<List<Notification>> getNotificationsByEmail(@RequestParam String userEmail) {
        List<Notification> notifications = notificationService.getNotificationsByEmail(userEmail);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getAllNotificationsWithImageAndName() {
        List<Notification> notifications = notificationService.getAllNotificationsWithImageAndName();
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/accept-friend-request")
    public ResponseEntity<String> acceptFriendRequest(
            @RequestParam String userEmail,
            @RequestParam Long userkey
    ) {
        notificationService.acceptFriendRequest(userEmail, userkey);
        return ResponseEntity.ok("Friend request accepted");
    }

    @DeleteMapping("/notifications/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long notificationId) {
        try {
            if (notificationId == null) {
                return ResponseEntity.badRequest().body("Invalid notificationId");
            }
            notificationService.deleteNotification(notificationId);
            return ResponseEntity.ok("Notification deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete notification.");
        }
    }
}