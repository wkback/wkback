package com.stswalkoo.fwbadge.model;

import com.stswalkoo.fwwalk.model.FwMuserWalk;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data

public class FwBadge {

	private Integer badgekey;
	private String badgename;
	private String badgecode;
	private Boolean repeatyn;
	private Date credate;
	private Integer creuserkey;
	private String badgeImage;


	private List<FwmuserBadge> userbadgeList;


}

