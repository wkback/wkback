package com.stswalkoo.fwbadge.mapper;

import com.stswalkoo.fwbadge.model.FwBadge;
import com.stswalkoo.fwbadge.model.FwmuserBadge;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Date;


@Mapper

public interface FwBadgeMapper {
//	void createBadge(FwBadge badge);
//	void updateBadge(FwBadge badge);
//	void deleteBadge(@Param("badgekey")Integer badgeKey);
	List<FwBadge> getBadgeList(FwBadge badge);

	void createmuserBadge(FwmuserBadge badge);
	void updatemuserBadge(FwmuserBadge badge);
	void deletemuserBadge(@Param("badgeuserkey")Integer badgeUserKey);
	List<FwmuserBadge> getmuserBadgeList(FwmuserBadge badge);

	void updateBadgeCredateByBadgecode(@Param("badgecode") String badgecode, @Param("credate") Date credate);
}