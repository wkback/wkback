package com.stswalkoo.fwrank.service;

import com.stswalkoo.fwrank.mapper.FwRankMapper;
import com.stswalkoo.fwrank.model.FwRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FwRankService {
    private final FwRankMapper fwwalkBackRepository;

    @Autowired
    public FwRankService(FwRankMapper fwwalkBackRepository) {
        this.fwwalkBackRepository = fwwalkBackRepository;
    }

    public List<FwRank> getAllRanksInfo() {
        return fwwalkBackRepository.findAll();
    }
}