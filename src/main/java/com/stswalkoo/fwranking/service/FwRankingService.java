package com.stswalkoo.fwranking.service;

import com.stswalkoo.fwranking.model.FwRanking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stswalkoo.fwranking.mapper.FwRankingMapper;
import com.stswalkoo.fwranking.model.FwRanking;

import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class) 

public class FwRankingService {

	@Autowired(required=false)
    private FwRankingMapper FwRankingMapper;

    public List<FwRanking> getRankingList(FwRanking ranking){
    	List<FwRanking> rankingList = FwRankingMapper.getRankingList(ranking);
    	return rankingList;
	}
    
    public void saveRanking(FwRanking ranking){
		this.createRanking(ranking);

	}

    
    public void createRanking(FwRanking ranking){

    	FwRankingMapper.createRanking(ranking);

//    	FwMuserWalk mUserWalk = new FwMuserWalk();
//    	mUserWalk.setWalkkey(ranking.getWalkkey());
//    	mUserWalk.setUserkey(ranking.getCreuserkey());
//    	this.createMuserWalk(mUserWalk);
    	
	}
	public void updateRanking(FwRanking ranking){
		FwRankingMapper.updateRanking(ranking);
	}
	
	public void deleteRanking(Integer rankingKey){
		FwRankingMapper.deleteRanking(rankingKey);
	}
	
	

//    public List<FwMuserWalk> getMuserWalkList(FwMuserWalk walk){
//    	List<FwMuserWalk> userList = FwWalkMapper.getMuserWalkList(walk);
//
//    	return userList;
//	}
//
//	public void createMuserWalk(FwMuserWalk walk){
//    	FwWalkMapper.createMuserWalk(walk);
//	}
//	public void updateMuserWalk(FwMuserWalk walk){
//		FwWalkMapper.updateMuserWalk(walk);
//	}
//	public void deleteMuserWalk(Integer userWalkKey){
//		FwWalkMapper.deleteMuserWalk(userWalkKey);
//	}
	
}