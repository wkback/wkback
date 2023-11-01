package com.stswalkoo.fwrank.model;

import lombok.Data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "fwwalk")
public class FwRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int walkkey;

    private Long creuserkey;
    private String walkname;
    private String distance;


    @Column(name = "startdate")
    private LocalDateTime startdate;

    @Column(name = "enddate")
    private LocalDateTime enddate;

}