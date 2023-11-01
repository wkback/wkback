package com.stswalkoo.Fwmemberno.controller;

import com.stswalkoo.Fwmemberno.model.MemberNo;
import com.stswalkoo.Fwmemberno.service.MembernoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MembernoController {

    private final MembernoService memberService;
    @GetMapping("/memberno-info")
    public ResponseEntity<?> getMemberInfo(@RequestParam(value = "userEmail", required = false) String userEmail) {
        if (userEmail != null) {
            MemberNo member = memberService.getMemberInfoByEmail(userEmail);
            if (member != null) {
                return ResponseEntity.ok(member);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            List<MemberNo> members = memberService.getAllMembersInfo();
            return ResponseEntity.ok(members);
        }
    }
}
