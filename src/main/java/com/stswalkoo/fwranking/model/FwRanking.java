package com.stswalkoo.fwranking.model;

import com.stswalkoo.fwwalk.model.FwMuserWalk;
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
	// FwMuserWalk 모델과 관련된 필드 추가
	private Integer userkey; // FwMuserWalk의 userkey
	private Integer userwalkkey; // FwMuserWalk의 userwalkkey
	private Date startdate; // FwMuserWalk의 startdate

	// FwMuserWalk 모델에서 데이터를 설정하는 메서드
	public void setFwMuserWalkData(FwMuserWalk fwMuserWalk) {
		this.userkey = fwMuserWalk.getUserkey();
		this.userwalkkey = fwMuserWalk.getUserwalkkey();
		this.startdate = fwMuserWalk.getStartdate();
	}
}