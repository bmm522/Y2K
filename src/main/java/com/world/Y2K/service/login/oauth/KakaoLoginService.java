package com.world.Y2K.service.login.oauth;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.world.Y2K.dao.login.LoginDAO;
import com.world.Y2K.model.dto.Member;
import com.world.Y2K.service.login.auth.UserDetailsImpl;
import com.world.Y2K.service.login.oauth.token.KakaoOAuthToken;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KakaoLoginService {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private LoginDAO loginDAO;
	
	@SuppressWarnings("null")
	public HttpSession kakaoLogin(String code, HttpServletRequest request) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
		
		MultiValueMap<String, String> accessTokenBodyInfo = new LinkedMultiValueMap<String, String>();
		accessTokenBodyInfo.add("grant_type", "authorization_code");
		accessTokenBodyInfo.add("client_id", "505e36c739260bba34b117ded3d8b963");
		accessTokenBodyInfo.add("redirect_uri", "http://localhost:8080/kakao.lo");
		accessTokenBodyInfo.add("code", code);
		
		
		
		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = 
				new HttpEntity<MultiValueMap<String, String>>(accessTokenBodyInfo, headers);
		
		ResponseEntity<String> response = new RestTemplate().exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST, 
				kakaoTokenRequest,
				String.class
				);
		
		System.out.println(response.getBody());
		
		ObjectMapper objectMapper = new ObjectMapper();
		KakaoOAuthToken kakaoOAuthToken = null;
		try {
			kakaoOAuthToken = objectMapper.readValue(response.getBody(),KakaoOAuthToken.class);
		} catch (JsonMappingException e) {
			log.error(e.getMessage());
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+kakaoOAuthToken.getAccess_token());
		headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
		
		HttpEntity<String> kakaoUserInfoRequest = 
				new HttpEntity<String>(headers2);
		
		ResponseEntity<String> response2 = new RestTemplate().exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST, 
				kakaoUserInfoRequest,
				String.class
				);
		
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(response2.getBody());
		
		String provider = "KAKAO";
		String providerId = element.getAsJsonObject().get("id").getAsString();
		String username = provider+"_"+providerId;
		String password = UUID.randomUUID().toString();
		
		Member member = loginDAO.findUser(username);

		if(member==null) {

			member = Member.builder()
						.username(username)
						.password("{bcrypt}"+bCryptPasswordEncoder.encode(password))
						.nickName("null")
						.status("Y")
						.orange(0L)
						.role("ROLE_USER")
						.provider(provider)
						.providerId(providerId)
						.build();
			
			System.out.println(member);
			loginDAO.registerMember(member);
		}
		
		UserDetails userDetails = new UserDetailsImpl(member);
		HttpSession session = request.getSession();
		session.setAttribute("Authentication", new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
		return session;
	}
	
	


}
