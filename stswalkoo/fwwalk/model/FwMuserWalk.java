package com.stswalkoo.fwwalk.model;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

@Data

public class FwMuserWalk {
	private Integer userwalkkey;
	private Integer walkkey;
	private Integer getpoint;
	private Integer userkey;
	private String friendImage;

	private String userText;
	private Date startdate;
	private Date enddate;

	private Boolean endYn;
}