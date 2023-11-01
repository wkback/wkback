package com.stswalkoo.Fwmemberno.mapper;

import com.stswalkoo.Fwmemberno.model.MemberNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MembernoMapper extends JpaRepository<MemberNo, Long> {
    MemberNo findByUserEmail(String userEmail);
    void deleteByUserEmailAndUserkey(String userEmail, Long userkey);
    MemberNo findByUserEmailAndUserkey(String userEmail, Long userkey);

}