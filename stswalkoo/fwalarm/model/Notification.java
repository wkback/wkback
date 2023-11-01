package com.stswalkoo.fwalarm.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = true)
    private String noMemberName;

    @Column(nullable = true)
    private String noMemberImage;

    @Column(nullable = true)
    private Long userkey;
}