package com.stswalkoo.fwranking.model;

import lombok.Data;

import java.util.Date;
//import java.util.List;

@Data

public class FwRanking {

	private Integer rankingKey;
	private Integer userKey;
	private Integer followerUserKey;
	private Integer userwalkKey;
	private Integer followerwalkKey;
	private Integer LvPKey;
	private Date userwalkDate;
	private Date followerwalkDate;

//	private List<FwMuserWalk> mUserList;
}