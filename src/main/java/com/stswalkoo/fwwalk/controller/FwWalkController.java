package com.stswalkoo.fwwalk.controller;



import com.stswalkoo.fwmember.model.Member;
import com.stswalkoo.fwwalk.mapper.FwWalkMapper;
import com.stswalkoo.fwwalk.service.FwWalkService;
import net.sf.json.JSONObject;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import com.stswalkoo.fwwalk.model.FwMuserWalk;
import com.stswalkoo.fwwalk.model.FwWalk;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
	private FwWalkMapper fwWalkMapper;

	@Autowired
	private com.stswalkoo.fwwalk.service.FwWalkService walkService;

	@RequestMapping(value = "/wk.getUserData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserData() {
		Map<String, Object> response = new HashMap<>();
		try {
			List<FwWalk> walkData = walkService.getWalkList(new FwWalk());
			List<FwMuserWalk> userWalkData = walkService.getMuserWalkList(new FwMuserWalk());

			response.put("walkData", walkData);
			response.put("userWalkData", userWalkData);
			response.put("status", "success");
		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An error occurred while fetching data.");
		}
		return response;
	}


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
	public Map<String, Object> endWalkUser(Model model, @RequestBody String param, HttpServletRequest request) {
		Boolean result = false;
		JSONObject jsonParam = null;
		JSONObject jsonObj = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		FwMuserWalk walkUser = null;
		try {
			jsonParam = JSONObject.fromObject(param);
			if (jsonParam != null) {
				walkUser = (FwMuserWalk) JSONObject.toBean(jsonParam, FwMuserWalk.class);

				// 여기에서 userwalkkey 필드 사용을 제거하고 원래 의도대로 walkkey를 사용하여 산책 종료 로직을 수행
				if (walkUser.getWalkkey() != null && walkUser.getGetpoint() != null) {
					walkService.endWalkUser(walkUser);
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		returnMap.put("result", result);
		return returnMap;
	}


	@RequestMapping(value = "/wk.deleteWalk", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteWalk(Model model, @RequestBody String param, HttpServletRequest request) {
		JSONObject jsonParam = null;
		Boolean result = false;

		Integer walkkey = null;
		Map<String,Object> returnMap = new HashMap<String,Object>();

		try{
			jsonParam = JSONObject.fromObject(param);
			if(jsonParam.has("walkkey")) 	walkkey = jsonParam.getInt("walkkey");
			walkService.deleteWalk(walkkey);
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

	@Transactional
	@RequestMapping(value = "/api/save-friend-image", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> saveFriendImage(@RequestBody Map<String, String> payload) {
		try {
			String friendImages = payload.get("friendImage");

			if (friendImages == null) {
				// friendImage가 null일 경우 처리
				throw new IllegalArgumentException("friendImage is null");
			}

			Integer walkkey = Integer.parseInt(payload.get("walkkey"));
			Integer userkey = Integer.parseInt(payload.get("userkey"));

			// 파일명을 콤마를 기준으로 분리
			String[] imageNames = friendImages.split(",");

			for (String friendImage : imageNames) {
				// 각 파일명을 사용하여 FwMuserWalk 객체 생성
				FwMuserWalk walkUser = new FwMuserWalk();
				walkUser.setWalkkey(walkkey);
				walkUser.setUserkey(userkey);
				walkUser.setFriendImage(friendImage.trim()); // 공백 제거

				// FwWalkMapper를 사용하여 데이터베이스에 저장
				fwWalkMapper.createMuserWalk(walkUser);
			}

			// 응답 생성 및 반환
			Map<String, Object> response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "Friend image saved successfully");
			return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			// walkkey 또는 userkey가 올바르게 변환되지 않는 경우 처리
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("status", "error");
			errorResponse.put("message", "Invalid walkkey or userkey");
			return ResponseEntity.badRequest().body(errorResponse);
		} catch (IllegalArgumentException e) {
			// friendImage가 null인 경우 처리
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("status", "error");
			errorResponse.put("message", "friendImage is null");
			return ResponseEntity.badRequest().body(errorResponse);
		} catch (Exception e) {
			// 그 외 예외 처리
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("status", "error");
			errorResponse.put("message", "An error occurred");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}
}