package com.stswalkoo.fwwalk.controller;



import net.sf.json.JSONObject;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stswalkoo.fwwalk.model.FwMuserWalk;
import com.stswalkoo.fwwalk.model.FwWalk;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController

public class FwWalkController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Autowired 
    private com.stswalkoo.fwwalk.service.FwWalkService walkService;

    @RequestMapping(value = "/wk.saveWalk", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveWalk(Model model, @RequestBody String param, HttpServletRequest request) {
    	Boolean result = false;
    	JSONObject jsonParam = null;
    	JSONObject jsonObj = null;
    	Map<String,Object> returnMap = new HashMap<String,Object>();
    	FwWalk Walk= null;
		try{
			jsonParam = JSONObject.fromObject(param);
			if(jsonParam.has("walk") == true)	jsonObj = jsonParam.getJSONObject("walk");
			if(jsonObj != null){
				Walk = (FwWalk) JSONObject.toBean(jsonObj, FwWalk.class);
				walkService.saveWalk(Walk);
				result = true;
			}
		}catch(Exception e){e.printStackTrace();}

		returnMap.put("result", result);
		returnMap.put("walkkey", Walk.getWalkkey());
		return returnMap;
	}
    
    @RequestMapping(value = "/wk.endWalkUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> endWalkUser(Model model, @RequestBody String param, HttpServletRequest request) {
    	Boolean result = false;
    	JSONObject jsonParam = null;
    	JSONObject jsonObj = null;
    	Map<String,Object> returnMap = new HashMap<String,Object>();
    	FwMuserWalk walkUser= null;
		try{
			jsonParam = JSONObject.fromObject(param);
			if(jsonParam != null){
				walkUser = (FwMuserWalk) JSONObject.toBean(jsonParam, FwMuserWalk.class);
				if (walkUser.getWalkkey() != null) {
					walkService.endWalkUser(walkUser);
					result = true;
				}
			}
		}catch(Exception e){e.printStackTrace();}
		
		returnMap.put("result", result);
		return returnMap;
	}
	
	@RequestMapping(value = "/wk.deleteWalk", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteWalk(Model model, @RequestBody String param, HttpServletRequest request) {
		JSONObject jsonParam = null;
		Boolean result = false;
		
		Integer walkKey = null;
    	Map<String,Object> returnMap = new HashMap<String,Object>();
		
		try{
			jsonParam = JSONObject.fromObject(param);
			if(jsonParam.has("walkKey")) 	walkKey = jsonParam.getInt("walkKey");
			walkService.deleteWalk(walkKey);
			result = true;
		}catch(Exception e){e.printStackTrace();}
		
		returnMap.put("result", result);
		return returnMap;
	}
	
	

    @RequestMapping(value = "/wk.getWalkList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getWalkList(Model model, @RequestBody String param, HttpServletRequest request) {
    	Boolean result = false;
    	JSONObject jsonParam = null;
    	JSONObject jsonObj = null;
    	List<FwWalk> walkList = null;
    	Map<String,Object> returnMap = new HashMap<String,Object>();
    	FwWalk Walk= null;
		try{
			jsonParam = JSONObject.fromObject(param);
			if(jsonParam.has("walk") == true)	jsonObj = jsonParam.getJSONObject("walk");
			if(jsonObj != null){
				Walk = (FwWalk) JSONObject.toBean(jsonObj, FwWalk.class);
				walkList = walkService.getWalkList(Walk);
				result = true;
			}
		}catch(Exception e){e.printStackTrace();}

		returnMap.put("result", result);
		returnMap.put("walk", walkList);
		return returnMap;
	}
}