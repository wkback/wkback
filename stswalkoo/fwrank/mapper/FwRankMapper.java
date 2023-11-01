package com.stswalkoo.fwrank.mapper;

import com.stswalkoo.fwrank.model.FwRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FwRankMapper extends JpaRepository<FwRank, Long> {
    List<FwRank> findAll();
}