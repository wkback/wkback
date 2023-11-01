package com.stswalkoo.fwrank.controller;

import com.stswalkoo.fwrank.model.FwRank;
import com.stswalkoo.fwrank.service.FwRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FwRankController {
    private final FwRankService fwRankService;

    @Autowired
    public FwRankController(FwRankService fwRankService) {
        this.fwRankService = fwRankService;
    }

    @GetMapping("/fwrank-info")
    public ResponseEntity<List<FwRank>> getFwRankInfo() {
        List<FwRank> ranks = fwRankService.getAllRanksInfo();
        return ResponseEntity.ok(ranks);
    }
}