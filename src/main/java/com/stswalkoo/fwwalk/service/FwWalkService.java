package com.stswalkoo.fwwalk.service;


import java.util.List;


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
    // ��å�� �����Ѵ� (= fwwalk)
    // -> ��� �����ڴ� ��å ������ (= fwmuserwalk)
    
    public void createWalk(FwWalk walk){	
    	// 1. ��å�� �����Ѵ�.
    	// walk.getWalkkey() == null
    	FwWalkMapper.createWalk(walk);
    	// walk.getWalkkey() != null
    	
    	// 2. ��å�� �⺻�����ڸ� �����Ѵ� (= ������)
    	FwMuserWalk mUserWalk = new FwMuserWalk();
    	mUserWalk.setWalkkey(walk.getWalkkey());
    	mUserWalk.setUserkey(walk.getCreuserkey());
    	this.createMuserWalk(mUserWalk);
    	
	}
    

//  ������ ��å�� �����û�� �Ѵ� endWalkUser   
    public void endWalkUser(FwMuserWalk walkUser){
//		산책종료시 실행

//      �ش��ϴ� ��å�� ���� ����� ��å������ getMuserwalkList
    	FwMuserWalk param = new FwMuserWalk();
    	param.setWalkkey(walkUser.getWalkkey());
    	param.setEndYn(false);
//		산책중인 사람들만 리스트로 가져오겠ㄷ라
    	// endDate�� null�� ���� ���� �߰�
//		산책 중인 사람들 리스트 몇명 있는지
    	List<FwMuserWalk> muserList = this.getMuserWalkList(param);
//      ������ ��å�� �����Ѵ�  updatemUserWalk
//		산책 종료 요청이 들어온 사람한테는 일단 산책 종ㅇ료
    	walkUser.setEndYn(true);
    	this.updateMuserWalk(walkUser);
//      �ش������� ������ �������  ��å�� ����ȴ� updateWalk
//		내가 산책의 마지막 멤버이면 산책 자체를 종료
    	if (muserList != null && muserList.size() == 1) {
    		FwWalk walk = new FwWalk();
    		walk.setWalkkey(walkUser.getWalkkey());
    		walk.setEndYn(true);
    		this.updateWalk(walk);
    	}
		//	    ���� ��å �ð� : 5�ð����� �ϳ�
		// a) �ش������� �� ��å�ð� ���ϱ� ==> ������ 5 = �� ��������
		// b) ���õ� ���� ���� ���ϱ�
		// a-b��ŭ ���� �����
		
		//	 ���� ��å Ƚ�� : 5������ �ϳ�
			// a) �ش������� �� ��åȽ�� ���ϱ� ==> ������ 5 = �� ��������
			// b) ���õ� ���� ���� ���ϱ�
			// a-b��ŭ ���� �����
		

		//ù��å
		// �ش������� �� ��åȽ�� ���ϱ� ==> ó���̸� �����
//		산책 종료할 때 유저에게 줄 수 있는 배지가 있는지 확인해서 있으면 지급
//		여기에 줄 뱃지들
    	param = new FwMuserWalk();
    	param.setUserkey(walkUser.getUserkey());
    	// enddate�� not null�� ��
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
		}
//		FwMuserWalk 리스트로 가져왔으니까
//		a : enddate-startdate 해서 5시간 기준 -> /5 해서 (이 사람이 지금까지 한 모든 산책에 대한 연산 진행)
//		b : 현재 해당 배지 몇개 가지고 있는지 가져옴
//		c: a에서 연산한 몫 - b => 새로 줘야할 배지 개수

		//	    ���丮10�� //point
		// a) ���� ����Ʈ �����ͼ� (��������) ==> ������ 10 = �� ��������
		// b) ���õ� ���� ���� ���ϱ�
		// a-b��ŭ ���� �����
	
	
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
	
}