package com.stswalkoo.fwbadge.controller;

import com.stswalkoo.fwbadge.model.FwBadge;
import com.stswalkoo.fwbadge.model.FwmuserBadge;

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

public class FwBadgeController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Autowired 
    private com.stswalkoo.fwbadge.service.FwBadgeService badgeService;

//    @RequestMapping(value = "/wk.saveBadge", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String,Object> saveBadge(Model model, @RequestBody String param, HttpServletRequest request) {
//    	Boolean result = false;
//    	JSONObject jsonParam = null;
//    	JSONObject jsonObj = null;
//    	Map<String,Object> returnMap = new HashMap<String,Object>();
//    	FwBadge Badge= null;
//		try{
//			jsonParam = JSONObject.fromObject(param);
//			if(jsonParam.has("badge") == true)	jsonObj = jsonParam.getJSONObject("badge");
//			if(jsonObj != null){
//				Badge = (FwBadge) JSONObject.toBean(jsonObj, FwBadge.class);
//				badgeService.saveBadge(Badge);
//				result = true;
//			}
//		}catch(Exception e){e.printStackTrace();}
//
//		returnMap.put("result", result);
//		return returnMap;
//	}
    
    
	
//	@RequestMapping(value = "/wk.deleteBadge", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String,Object> deleteBadge(Model model, @RequestBody String param, HttpServletRequest request) {
//		JSONObject jsonParam = null;
//		Boolean result = false;
//		
//		Integer badgeKey = null;
//    	Map<String,Object> returnMap = new HashMap<String,Object>();
//		
//		try{
//			jsonParam = JSONObject.fromObject(param);
//			if(jsonParam.has("badgeKey")) 	badgeKey = jsonParam.getInt("badgeKey");
//			badgeService.deleteBadge(badgeKey);
//			result = true;
//		}catch(Exception e){e.printStackTrace();}
//		
//		returnMap.put("result", result);
//		return returnMap;
//	}
//	
//	

    @RequestMapping(value = "/wk.getBadgeList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getBadgeList(Model model, @RequestBody String param, HttpServletRequest request) {
    	Boolean result = false;
    	JSONObject jsonParam = null;
    	JSONObject jsonObj = null;
    	List<FwBadge> badgeList = null;
    	Map<String,Object> returnMap = new HashMap<String,Object>();
		FwBadge Badge= null;
		try{
			jsonParam = JSONObject.fromObject(param);
			if(jsonParam.has("badge") == true)	jsonObj = jsonParam.getJSONObject("badge");
			if(jsonObj != null){
				Badge = (FwBadge) JSONObject.toBean(jsonObj, FwBadge.class);
				badgeList = badgeService.getBadgeList(Badge);
				result = true;
			}
		}catch(Exception e){e.printStackTrace();}

		returnMap.put("result", result);
		returnMap.put("badge", badgeList);
		return returnMap;
	}
    
    @RequestMapping(value = "/wk.getmuserBadgeList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getmuserBadgeList(Model model, @RequestBody String param, HttpServletRequest request) {
    	Boolean result = false;
    	JSONObject jsonParam = null;
    	JSONObject jsonObj = null;
    	List<FwmuserBadge> badgeList = null;
    	Map<String,Object> returnMap = new HashMap<String,Object>();
		FwmuserBadge Badge= null;
		try{
			jsonParam = JSONObject.fromObject(param);
			if(jsonParam.has("badge") == true)	jsonObj = jsonParam.getJSONObject("badge");
			if(jsonObj != null){
				Badge = (FwmuserBadge) JSONObject.toBean(jsonObj, FwmuserBadge.class);
				badgeList = badgeService.getmuserBadgeList(Badge);
				result = true;
			}
		}catch(Exception e){e.printStackTrace();}

		returnMap.put("result", result);
		returnMap.put("mBadge", badgeList);
		return returnMap;
	}
}