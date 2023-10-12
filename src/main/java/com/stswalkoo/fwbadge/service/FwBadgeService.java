package com.stswalkoo.fwbadge.service;


import com.stswalkoo.fwbadge.model.FwBadge;
import com.stswalkoo.fwbadge.model.FwmuserBadge;
import com.stswalkoo.fwbadge.mapper.FwBadgeMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class) 

public class FwBadgeService {

	@Autowired(required=false)
    private FwBadgeMapper FwBadgeMapper;

    public List<FwBadge> getBadgeList(FwBadge badge){
    	List<FwBadge> badgeList = FwBadgeMapper.getBadgeList(badge);
    	return badgeList;
	}
    
    public List<FwmuserBadge> getmuserBadgeList(FwmuserBadge badge){
    	List<FwmuserBadge> badgeList = FwBadgeMapper.getmuserBadgeList(badge);
    	
    	return badgeList;
	}
    
	public Boolean createmuserBadge(FwmuserBadge muserbadge){
		// muserbadge 
		// 어떤 뱃지(badgeKey)에 누구(userKey)를 추가할지 
		if (muserbadge.getBadgecode() == null || muserbadge.getUserkey() == null) {
			return false;
		}
		
		// 1. 얻으려는 뱃지가 반복뱃지인지 확인
		FwBadge param = new FwBadge();
		param.setBadgecode(muserbadge.getBadgecode());
		List<FwBadge> badgeList = this.getBadgeList(param);
		if (badgeList.size() == 0 || badgeList.size() > 1) {
			return false;
		}
		FwBadge targetBadge = badgeList.get(0);
		Boolean repeatYn = targetBadge.getRepeatyn();
		// 2. 반복이 아니라면 내가 이미 가지고있는지 확인
		if (repeatYn.equals(false)) {
			FwmuserBadge paramUser = new FwmuserBadge();
			paramUser.setBadgecode(muserbadge.getBadgecode());
			paramUser.setUserkey(muserbadge.getUserkey());
			List<FwmuserBadge> muserList = this.getmuserBadgeList(muserbadge);
			if (muserList.size() > 0) {
				return false;
			}
		}
		
		// 3. 뱃지추가
		FwBadgeMapper.createmuserBadge(muserbadge);
		return true;
	}
	public void updatemuserBadge(FwmuserBadge badge){
		FwBadgeMapper.updatemuserBadge(badge);
	}
	public void deletemuserBadge(Integer badgeUserKey){
		FwBadgeMapper.deletemuserBadge(badgeUserKey);
	}
	
	// 뱃지 얻는거 -> 이미 있는지 확인(반복확인)
	
	
	
	
//	  public void saveBadge(FwBadge badge){
//	    	if (badge.getBadgeKey() != null) {
//	    		this.updateBadge(badge);
//	    	} else {
//	    		this.createBadge(badge);
//	    	}
//		}
//	    
//	    public void createBadge(FwBadge badge){
//	    	// 1. 산책을 생성한다.
//	    	// walk.getWalkkey() == null
//	    	FwBadgeMapper.createBadge(badge);
//	    	// walk.getWalkkey() != null
//	    	
//	    	// 2. 산책에 기본참여자를 생성한다 (= 생성자)
//			FwmuserBadge userBadge = new FwmuserBadge();
//			userBadge.setBadgeKey(badge.getBadgeKey());
//	    	this.createuserBadge(userBadge);
//	    	
//		}
//		public void updateBadge(FwBadge badge){
//			FwBadgeMapper.updateBadge(badge);
//		}
//
	
//	public void deleteBadge(Integer badgeKey){
//		FwBadgeMapper.deleteBadge(badgeKey);
//	}
}