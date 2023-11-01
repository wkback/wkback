package com.stswalkoo.fwmember.service;

import com.stswalkoo.fwalarm.service.NotificationService;
import com.stswalkoo.fwmember.mapper.MemberMapper;
import com.stswalkoo.fwmember.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class MemberService {

    private final MemberMapper memberRepository;

    @Value("${file.upload.path}") // 프로퍼티 값 읽어오기
    private String fileUploadPath;

    public Member getMemberInfo(Long userKey) {
        Optional<Member> savedMember = memberRepository.findById(userKey);
        return savedMember.orElse(null);
    }

    public List<Member> getAllMembersInfo() {
        return memberRepository.findAll();
    }

    public void saveMemberImage(Long userKey, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new FileNotFoundException();
        }
        String savedName = saveImage(file);

        Member member;
        Optional<Member> savedMember = memberRepository.findById(userKey);
        member = savedMember.orElseGet(Member::new);
        member.setMemberImage(savedName);
        memberRepository.save(member);
    }

    public void savePetImage(Long userKey, MultipartFile image, String petName) throws IOException {
        if (image.isEmpty()) {
            throw new FileNotFoundException();
        }
        String savedName = saveImage(image);
        // 사용자 정보 업데이트
        Member member = memberRepository.findById(userKey).orElseThrow(() -> new FileNotFoundException("User not found"));
        member.setPetImage(savedName);
        member.setPetName(petName);
        memberRepository.save(member);
    }


    private String saveImage(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        if (!StringUtils.hasText(originalName)) {
            throw new FileNotFoundException();
        }
        String uuid = String.valueOf(UUID.randomUUID());
        String extension = originalName.substring(originalName.lastIndexOf("."));
        String savedName = uuid + extension;
        String savedPath = fileUploadPath + savedName;

        File savedDir = new File(fileUploadPath);
        if (!savedDir.exists()) {
            try {
                if (savedDir.mkdir()) {
                    log.info("디렉토리 생성 완료");
                }
            } catch (Exception e) {
                log.error("디렉토리 생성 실패 : {}", e.getMessage());
            }
        }
        file.transferTo(new File(savedPath));
        return savedName;
    }
    public Member updateMainBadge(Long userKey, String mainBadgeName, String mainBadgeImage) {
        Optional<Member> optionalMember = memberRepository.findById(userKey);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setMainBadgeName(mainBadgeName);
            member.setMainBadgeImage(mainBadgeImage);

            memberRepository.save(member);
            return member;
        } else {
            return null; // 사용자를 찾을 수 없을 경우 null을 반환
        }
    }

    public Member getMemberInfoByEmail(String userEmail) {
        return memberRepository.findByUserEmail(userEmail);
    }


    private final NotificationService notificationService;

    public MemberService(MemberMapper memberRepository, NotificationService notificationService) {
        this.memberRepository = memberRepository;
        this.notificationService = notificationService;
    }



}