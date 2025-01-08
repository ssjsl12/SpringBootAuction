package com.example.auctionshop.config;

import com.example.auctionshop.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@Log4j2
public class CustomOAuth2UserService extends DefaultOAuth2UserService {


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)  throws OAuth2AuthenticationException {
        // 카카오 사용자 정보 요청
        OAuth2User oauth2User = loadUser(userRequest);

        String accessToken = userRequest.getAccessToken().getTokenValue();

        // 사용자 정보 (예: 이메일) 가져오기
        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("kakao_account.email");

        log.info("-----------------------------------------------");

        // 사용자 정보를 기반으로 추가적인 처리를 할 수 있음 (예: DB 저장, 역할 부여 등)

        // Authentication 객체 생성 (여기서는 예시로 User 객체를 사용)
        return null;
    }


}
