package com.stswalkoo.fwmember.controller;

import com.stswalkoo.fwmember.model.Member;
import com.stswalkoo.fwmember.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class WalkController {

    private final MemberService memberService;
    @GetMapping("/member-info")
    public ResponseEntity<?> getMemberInfo(@RequestParam(value = "userEmail", required = false) String userEmail) {
        if (userEmail != null) {
            Member member = memberService.getMemberInfoByEmail(userEmail);
            if (member != null) {
                return ResponseEntity.ok(member);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            List<Member> members = memberService.getAllMembersInfo();
            return ResponseEntity.ok(members);
        }
    }

    @PostMapping("/save-member-image")
    public ResponseEntity<Void> saveMemberImage(@RequestParam("userKey") Long userKey,
                                                @RequestParam("image") MultipartFile file) {
        try {
            memberService.saveMemberImage(userKey, file);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save-pet-image")
    public ResponseEntity<Void> savePetImage(@RequestParam("userKey") Long userKey,
                                             @RequestParam("image") MultipartFile file,
                                             @RequestParam("petName") String petName) {
        try {
            memberService.savePetImage(userKey, file, petName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/setMainBadge")
    public ResponseEntity<Member> setMainBadge(@RequestParam("userKey") Long userKey,
                                               @RequestParam("mainBadgeName") String mainBadgeName,
                                               @RequestParam("mainBadgeImage") String mainBadgeImage) {
        Member updatedMember = memberService.updateMainBadge(userKey, mainBadgeName, mainBadgeImage);

        if (updatedMember != null) {
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
