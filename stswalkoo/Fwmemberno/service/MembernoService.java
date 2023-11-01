package com.stswalkoo.Fwmemberno.service;

import com.stswalkoo.Fwmemberno.mapper.MembernoMapper;
import com.stswalkoo.Fwmemberno.model.MemberNo;
import com.stswalkoo.fwalarm.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MembernoService {

    private final MembernoMapper memberRepository;

    public MemberNo getMemberInfo(Long userKey) {
        Optional<MemberNo> savedMember = memberRepository.findById(userKey);
        return savedMember.orElse(null);
    }

    public List<MemberNo> getAllMembersInfo() {
        return memberRepository.findAll();
    }


    public MemberNo getMemberInfoByEmail(String userEmail) {
        return memberRepository.findByUserEmail(userEmail);
    }


    private final NotificationService notificationService;

    public MembernoService(MembernoMapper memberRepository, NotificationService notificationService) {
        this.memberRepository = memberRepository;
        this.notificationService = notificationService;
    }
}