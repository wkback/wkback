package com.stswalkoo.fwmember.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userkey;

    private String memberName;
    private String memberImage;
    private String petName;
    private String petImage;
    private String mainBadgeName;
    private String mainBadgeImage;
    private String userEmail;

}