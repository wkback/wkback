package com.stswalkoo.Fwmemberno.model;

import com.stswalkoo.fwalarm.model.Notification;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberNo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userkey;

    private String noMemberName;
    private String noMemberImage;
    private String noPetName;
    private String noPetImage;
    private String userEmail;

}