package com.stswalkoo.fwwalk.model;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

public class FwWalk {

	private String walkname;
	private Date startdate;
	private Date enddate;
	private String distance;
	private Integer creuserkey;
	private Integer walkkey;
	private Integer userkey;
	private List<FwMuserWalk> mUserList;
	private Boolean endYn;
}