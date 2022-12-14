package com.world.Y2K.service.login.oauth;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.world.Y2K.dao.mypage.MypageDAO;
import com.world.Y2K.dao.skin.SkinDAO;
import com.world.Y2K.model.dto.Member;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KakaoLoginService extends SocialLoginServiceTemplate{
	
	@Autowired
	private MypageDAO mypageDAO;
	
	@Autowired
	private SkinDAO skinDAO;
	
	@Override
	protected String getAccessToken(String code) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
		
		MultiValueMap<String, String> accessTokenBodyInfo = new LinkedMultiValueMap<String, String>();
		accessTokenBodyInfo.add("grant_type", "authorization_code");
		accessTokenBodyInfo.add("client_id", "505e36c739260bba34b117ded3d8b963");
		accessTokenBodyInfo.add("redirect_uri", "http://localhost:8080/kakao.lo");
		accessTokenBodyInfo.add("code", code);
		
		
		ResponseEntity<String> response = new RestTemplate().exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST, 
				new HttpEntity<MultiValueMap<String, String>>(accessTokenBodyInfo, headers),
				String.class
				);
		
		return  asString(response.getBody(),"access_token");
	}
	
	@Override
	protected HashMap<String, String> getUserInfoFromKakaoResource(String accessToken) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer "+accessToken);
		headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
		
		ResponseEntity<String> response = new RestTemplate().exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST, 
				new HttpEntity<String>(headers),
				String.class
				);
			
		return setUserInfo(new JsonParser().parse(response.getBody()));
	}


	@Override
	protected HashMap<String, String> setUserInfo(JsonElement userInfoJson) {
		HashMap<String, String> userInfo = new HashMap<String, String>();
		userInfo.put("provider", "KAKAO");
		userInfo.put("providerId", userInfoJson.getAsJsonObject().get("id").getAsString());
		userInfo.put("username", "KAKAO"+"_"+userInfoJson.getAsJsonObject().get("id").getAsString());
		userInfo.put("password", "null");
		return userInfo;
	}
	
	@Override
	protected ModelAndView setLogin(HashMap<String, String> userInfo) {
		Member member = loginDAO.findUser(userInfo.get("username"));

		if(member==null) {
		
			member = Member.builder()
						.username(userInfo.get("username"))
						.password(bCryptPasswordEncoder.encode(userInfo.get("password")))
						.nickName("null")
						.email("null")
						.status("Y")
						.orange(0L)
						.role("ROLE_USER")
						.provider(userInfo.get("provider"))
						.providerId(userInfo.get("providerId"))
						.build();
		
			loginDAO.registerMember(member);

		}
		
		Member member2 = loginDAO.findUser(member.getUsername());
		
		if(mypageDAO.checkFirst(member2.getUserNo()) == 0 ){
			skinDAO.insertDefault(member2.getUserNo());
//			mypageDAO.insertDefault(member2.getUserNo());
		}
		
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("username", member2.getUsername());
		mv.addObject("password", member2.getPassword());
		mv.setViewName("/login/social");
		return mv;
	}

	
}

	
	



