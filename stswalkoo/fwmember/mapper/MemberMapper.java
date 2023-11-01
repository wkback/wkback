package com.stswalkoo.fwmember.mapper;

import com.stswalkoo.fwmember.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMapper extends JpaRepository<Member, Long> {
    Member findByUserEmail(String userEmail);
}