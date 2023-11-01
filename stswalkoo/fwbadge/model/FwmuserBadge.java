package com.stswalkoo.fwbadge.model;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

public class FwmuserBadge {

	private Integer muserbadgekey;
	private Integer badgekey;
	private Integer walkkey;
	private Integer userkey;
	private Date credate;
	private String badgecode;
	private String badgename;
	
	private Date startdate;
	private Date enddate;
}

