package com.stswalkoo.fwwalk.service;


import java.util.List;
import java.util.Date;


import com.stswalkoo.fwbadge.mapper.FwBadgeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stswalkoo.fwbadge.model.FwmuserBadge;
import com.stswalkoo.fwbadge.service.FwBadgeService;
import com.stswalkoo.fwwalk.mapper.FwWalkMapper;
import com.stswalkoo.fwwalk.model.FwMuserWalk;
import com.stswalkoo.fwwalk.model.FwWalk;



@Service
@Transactional(rollbackFor = Exception.class)

public class FwWalkService {
	private final FwWalkMapper walkMapper;
	@Autowired
	private FwBadgeMapper fwBadgeMapper;

	@Autowired
	public FwWalkService(FwWalkMapper walkMapper) {
		this.walkMapper = walkMapper;
	}

	@Autowired(required=false)
	private FwWalkMapper FwWalkMapper;


	@Autowired(required=false)
	private FwBadgeService badgeService;

	public List<FwWalk> getWalkList(FwWalk walk){
		List<FwWalk> walkList = FwWalkMapper.getWalkList(walk);
		for (FwWalk w : walkList) {
			FwMuserWalk userParam = new FwMuserWalk();
			userParam.setWalkkey(w.getWalkkey());
			w.setMUserList(this.getMuserWalkList(userParam));
		}
		return walkList;
	}


	public void saveWalk(FwWalk walk){
		if (walk.getWalkkey() != null) {
			this.updateWalk(walk);
		} else {
			this.createWalk(walk);
		}
	}


	public void createWalk(FwWalk walk){
		// walk.getWalkkey() == null
		FwWalkMapper.createWalk(walk);
		// walk.getWalkkey() != null

		FwMuserWalk mUserWalk = new FwMuserWalk();
		mUserWalk.setWalkkey(walk.getWalkkey());
		mUserWalk.setUserkey(walk.getCreuserkey());
		this.createMuserWalk(mUserWalk);

	}


	public void endWalkUser(FwMuserWalk walkUser){
//		산책종료시 실행

		FwMuserWalk param = new FwMuserWalk();
		param.setWalkkey(walkUser.getWalkkey());
		param.setEndYn(false);
//		산책중인 사람들만 리스트로 가져오겠ㄷ라
//		산책 중인 사람들 리스트 몇명 있는지
		List<FwMuserWalk> muserList = this.getMuserWalkList(param);
//		산책 종료 요청이 들어온 사람한테는 일단 산책 종ㅇ료
		walkUser.setEndYn(true);
		this.updateMuserWalk(walkUser);
//		내가 산책의 마지막 멤버이면 산책 자체를 종료
		if (muserList != null && muserList.size() == 1) {
			FwWalk walk = new FwWalk();
			walk.setWalkkey(walkUser.getWalkkey());
			walk.setEndYn(true);
			this.updateWalk(walk);
		}

//		산책 종료할 때 유저에게 줄 수 있는 배지가 있는지 확인해서 있으면 지급
//		여기에 줄 뱃지들
		param = new FwMuserWalk();
		param.setUserkey(walkUser.getUserkey());
//		setEndYn == 산책 종료
		param.setEndYn(true);
//		db에서 FwMuserWalk의 데이터 가져와서 찾아보기
		List<FwMuserWalk> mUserWalkList = this.getMuserWalkList(param);
		if (mUserWalkList != null && mUserWalkList.size() == 1) {
			FwmuserBadge muserbadge = new FwmuserBadge();
			muserbadge.setBadgecode("FW_FIRST");
			muserbadge.setWalkkey(walkUser.getWalkkey());
			muserbadge.setUserkey(walkUser.getUserkey());
			badgeService.createmuserBadge(muserbadge);
			fwBadgeMapper.updateBadgeCredateByBadgecode("FW_FIRST", new Date());
		}
		if (mUserWalkList != null && mUserWalkList.size() == 5) {
			FwmuserBadge muserbadge = new FwmuserBadge();
			muserbadge.setBadgecode("FW_TEST2");
			muserbadge.setWalkkey(walkUser.getWalkkey());
			muserbadge.setUserkey(walkUser.getUserkey());
			badgeService.createmuserBadge(muserbadge);
			fwBadgeMapper.updateBadgeCredateByBadgecode("FW_TEST2", new Date());
		}
		if (mUserWalkList != null && !mUserWalkList.isEmpty()) {
			// 누적 산책 시간 계산을 위한 변수
			double totalWalkTime = 0.0;

			// mUserWalkList에 있는 각 산책 기록을 순회하며 시간 계산
			for (FwMuserWalk userWalk : mUserWalkList) {
				if (userWalk.getStartdate() != null && userWalk.getEnddate() != null) {
					// 각 산책의 시간 차이를 계산하고 더합니다.
//					double walkDuration = (userWalk.getEnddate().getTime() - userWalk.getStartdate().getTime()) / 1000.0; // 밀리초를 초로 변환

					double walkDuration = (userWalk.getEnddate().getTime() - userWalk.getStartdate().getTime()) / 3600000.0;
					totalWalkTime += walkDuration;
				}
			}

			// 누적 산책 시간이 5시간 이상인 경우에만 배지를 지급
//			if (totalWalkTime >= 30.0) {

			if (totalWalkTime >= 0.01) {
				FwmuserBadge muserbadge = new FwmuserBadge();
				muserbadge.setBadgecode("FW_TEST1");
				muserbadge.setWalkkey(walkUser.getWalkkey());
				muserbadge.setUserkey(walkUser.getUserkey());
				badgeService.createmuserBadge(muserbadge);
				fwBadgeMapper.updateBadgeCredateByBadgecode("FW_TEST1", new Date());
			}
		}


//		FwMuserWalk 리스트로 가져왔으니까
//		a : enddate-startdate 해서 5시간 기준 -> /5 해서 (이 사람이 지금까지 한 모든 산책에 대한 연산 진행)
//		b : 현재 해당 배지 몇개 가지고 있는지 가져옴
//		c: a에서 연산한 몫 - b => 새로 줘야할 배지 개수

//		long totalTimeMillis = walkUser.getEnddate().getTime() - walkUser.getStartdate().getTime();
//		int totalHours = (int)(totalTimeMillis / (1000 * 60 * 60)); // 산첵한 전체 시간
//		int badgesToAward = totalHours / 5; // 5시간 당 1개의 배지
//
//		// 사용자가 이미 가지고 있는 배지 개수 가져오기
//		int currentBadges = badgeService.getBadgeCountForUser(walkUser.getUserkey());
//
//		// 새로운 배지 개수 계산
//		int newBadges = badgesToAward - currentBadges;
//
//		// c: a에서 연산한 몫 - b => 새로 줘야할 배지 개수
//		if (newBadges > 0) {
//			for (int i = 0; i < newBadges; i++) {
//				// "FW_TEST1" 배지를 지급
//				FwmuserBadge muserbadge = new FwmuserBadge();
//				muserbadge.setBadgecode("FW_TEST1");
//				muserbadge.setWalkkey(walkUser.getWalkkey());
//				muserbadge.setUserkey(walkUser.getUserkey());
//				badgeService.createmuserBadge(muserbadge);
//			}
//		}


	}
	//
	public void updateWalk(FwWalk walk){


		FwWalkMapper.updateWalk(walk);

	}

	public void deleteWalk(Integer walkKey){
		FwWalkMapper.deleteWalk(walkKey);
	}



	public List<FwMuserWalk> getMuserWalkList(FwMuserWalk walk){
		List<FwMuserWalk> userList = FwWalkMapper.getMuserWalkList(walk);

		return userList;
	}

	public void createMuserWalk(FwMuserWalk walk){
		FwWalkMapper.createMuserWalk(walk);
	}
	public void updateMuserWalk(FwMuserWalk walk){
		FwWalkMapper.updateMuserWalk(walk);
	}
	public void deleteMuserWalk(Integer userWalkKey){
		FwWalkMapper.deleteMuserWalk(userWalkKey);
	}
	public void saveFriendImage(String friendImage) {
		// Logic to save the friendImage to the database
		FwMuserWalk walkUser = new FwMuserWalk();
		walkUser.setFriendImage(friendImage);
		walkMapper.createMuserWalk(walkUser);
	}

}