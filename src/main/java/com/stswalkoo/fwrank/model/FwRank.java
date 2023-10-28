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
@Table(name = "fwwalk_back")
public class FwRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
		// @Column(name = "walkkey")
    private int walkkey; //얘를 기준으로 나옴
		
		private Long userkey;
		private int min;
		private int seconds;
		private float distance;
		private int getpoint;

		@Column(name = "walkdate")
		private LocalDateTime walkDate;
}