package com.stswalkoo.fwranking.controller;

import com.stswalkoo.fwranking.model.FwRanking;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController

public class FwRankingController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Autowired 
    private com.stswalkoo.fwranking.service.FwRankingService rankingService;

    @RequestMapping(value = "/wk.saveRanking", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveBadge(Model model, @RequestBody String param, HttpServletRequest request) {
    	Boolean result = false;
    	JSONObject jsonParam = null;
    	JSONObject jsonObj = null;
    	Map<String,Object> returnMap = new HashMap<String,Object>();
    	FwRanking Ranking= null;
		try{
			jsonParam = JSONObject.fromObject(param);
			if(jsonParam.has("ranking") == true)	jsonObj = jsonParam.getJSONObject("ranking");
			if(jsonObj != null){
				Ranking = (FwRanking) JSONObject.toBean(jsonObj, FwRanking.class);
				rankingService.saveRanking(Ranking);
				result = true;
			}
		}catch(Exception e){e.printStackTrace();}
		
		returnMap.put("result", result);
		return returnMap;
	}
    
    
	
	@RequestMapping(value = "/wk.deleteRanking", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteBadge(Model model, @RequestBody String param, HttpServletRequest request) {
		JSONObject jsonParam = null;
		Boolean result = false;
		
		Integer rankingKey = null;
    	Map<String,Object> returnMap = new HashMap<String,Object>();
		
		try{
			jsonParam = JSONObject.fromObject(param);
			if(jsonParam.has("rankingKey")) 	rankingKey = jsonParam.getInt("rankingKey");
			rankingService.deleteRanking(rankingKey);
			result = true;
		}catch(Exception e){e.printStackTrace();}
		
		returnMap.put("result", result);
		return returnMap;
	}
	
	

    @RequestMapping(value = "/wk.getRankingList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getRankingList(Model model, @RequestBody String param, HttpServletRequest request) {
    	Boolean result = false;
    	JSONObject jsonParam = null;
    	JSONObject jsonObj = null;
    	List<FwRanking> rankingList = null;
    	Map<String,Object> returnMap = new HashMap<String,Object>();
		FwRanking Ranking= null;
		try{
			jsonParam = JSONObject.fromObject(param);
			if(jsonParam.has("ranking") == true)	jsonObj = jsonParam.getJSONObject("ranking");
			if(jsonObj != null){
				Ranking = (FwRanking) JSONObject.toBean(jsonObj, FwRanking.class);
				rankingList = rankingService.getRankingList(Ranking);
				result = true;
			}
		}catch(Exception e){e.printStackTrace();}

		returnMap.put("result", result);
		returnMap.put("ranking", rankingList);
		return returnMap;
	}
}