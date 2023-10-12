package com.stswalkoo.fwranking.mapper;

import com.stswalkoo.fwranking.model.FwRanking;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper

public interface FwRankingMapper {
	void createRanking(FwRanking ranking);
	void updateRanking(FwRanking ranking);
	void deleteRanking(@Param("rankingKey")Integer rankingKey);
	List<FwRanking> getRankingList(FwRanking ranking);

//	void createMuserWalk(FwMuserWalk walk);
//	void updateMuserWalk(FwMuserWalk walk);
//	void deleteMuserWalk(@Param("userwalkkey")Integer userwalkkey);
//	List<FwMuserWalk> getMuserWalkList(FwMuserWalk walk);
}