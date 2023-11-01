package com.stswalkoo.fwwalk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stswalkoo.fwwalk.model.FwMuserWalk;
import com.stswalkoo.fwwalk.model.FwWalk;


@Mapper

public interface FwWalkMapper {
	void createWalk(FwWalk walk);
	void updateWalk(FwWalk walk);
	void deleteWalk(@Param("walkkey")Integer walkkey);
	List<FwWalk> getWalkList(FwWalk walk);

	void createMuserWalk(FwMuserWalk walk);
	void updateMuserWalk(FwMuserWalk walk);
	void deleteMuserWalk(@Param("userwalkkey")Integer userwalkkey);
	List<FwMuserWalk> getMuserWalkList(FwMuserWalk walk);
}